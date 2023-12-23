package org.isk.molekule.dynamic

import org.isk.molekule.dynamic.force.ForceComputer
import org.isk.molekule.dynamic.integrator.Integrator
import org.isk.molekule.dynamic.integrator.VerletIntegrator
import org.isk.molekule.dynamic.tracker.NeighborListTracker
import org.isk.molekule.dynamic.tracker.Tracker
import org.isk.molekule.dynamic.watcher.BoundaryCondition
import org.isk.molekule.dynamic.watcher.BoundaryType
import org.isk.molekule.dynamic.watcher.Watcher
import org.isk.molekule.gen.Environment
import org.isk.molekule.gen.geomertry.Box
import org.isk.molekule.gen.geomertry.Point
import org.isk.molekule.gen.utils.length
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import kotlin.math.max

class Simulation(
    var enclosingBox: Box,
    var boundaryCondition: BoundaryCondition = BoundaryCondition(
        BoundaryType.PERIODIC, BoundaryType.PERIODIC, BoundaryType.PERIODIC
    )
) {

    lateinit var tracker: Tracker


    var integrator: Integrator = VerletIntegrator()

    private val watchers: MutableList<Watcher> = mutableListOf()

    val forceComputers = mutableSetOf<ForceComputer>()
    var elapsedTime: Double = 0.0
        private set


    var timeStep: Double = 0.005

    fun addWatcher(watcher: Watcher) {
        watcher.onRegister(this)
        watchers.add(watcher)
    }


    fun removeWatcher(watcher: Watcher) {
        if (watchers.remove(watcher)) watcher.onRemove(this)
    }

    private val availableCores = max(Runtime.getRuntime().availableProcessors(), 1)
    var threadPool = Executors.newFixedThreadPool(availableCores)

    fun run(time: Double): Simulation {
        var acc = 0.0
        while (acc < time) {
            tracker.atoms.chunked(tracker.atoms.size / availableCores)
                .map {
                    threadPool.submit {
                        it.map { atom ->
                            atom to integrator.evolve(timeStep, atom.phaseSpacePoint, elapsedTime) { it, t ->
                                forceComputers.map { it.force(atom, t) }.filterNot { it === Point.origin }
                                    .fold(Point.origin) { acc, point -> acc + point }.also {
                                        atom.attributes["force"] = it
                                    }
                            }
                        }.forEach { it.first.phaseSpacePoint = it.second }
                    }
                }.forEach { it.get() }
            watchers.plus(boundaryCondition).forEach { it.trigger(this) }
            elapsedTime += timeStep
            acc += timeStep
            tracker.atoms.forEach { it.attributes.clear() }
        }
        return this
    }

    fun finish() {
        threadPool.shutdown()
    }

    companion object {
        fun fromEnvironment(
            environment: Environment,
            boundaryCondition: BoundaryCondition = BoundaryCondition(
                BoundaryType.PERIODIC, BoundaryType.PERIODIC, BoundaryType.PERIODIC
            ),
            systemLength: Double

        ): Simulation = Simulation(environment.enclosingBox, boundaryCondition).apply {
            this.tracker = NeighborListTracker(
                systemLength,
                this
            )
            tracker.trackAll(environment.atoms)
            tracker.trackAll(environment.entities)
        }
    }
}