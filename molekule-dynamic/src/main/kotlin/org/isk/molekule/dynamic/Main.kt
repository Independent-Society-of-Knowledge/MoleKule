package org.isk.molekule.dynamic

import org.isk.molekule.dynamic.force.LennardJonesCutOffForce
import org.isk.molekule.dynamic.integrator.RK4Integrator
import org.isk.molekule.dynamic.report.KineticEnergyReporter
import org.isk.molekule.dynamic.report.PotentialEnergyReporter
import org.isk.molekule.dynamic.report.ReportGatherer
import org.isk.molekule.dynamic.report.Reporter
import org.isk.molekule.dynamic.tracker.NaiveListTracker
import org.isk.molekule.dynamic.tracker.NeighborListTracker
import org.isk.molekule.dynamic.watcher.BoundaryCondition
import org.isk.molekule.dynamic.watcher.BoundaryType.*
import org.isk.molekule.dynamic.watcher.Dumper
import org.isk.molekule.dynamic.watcher.TimeAwareWatcher
import org.isk.molekule.dynamic.watcher.Watcher
import org.isk.molekule.gen.AtomicMass
import org.isk.molekule.gen.Environment
import org.isk.molekule.gen.atom.Atom
import org.isk.molekule.gen.geomertry.Point
import java.util.concurrent.Executors

fun main() {
    val env = Environment()
    for (i in 0..<5)
        for (j in 0..<5)
            for (k in 0..<5)
                env.add(Atom(Point(i * 0.8, j * 0.8, k * 0.8), AtomicMass.H.mass, 1))

//    env.createVMD()
//        .run()
//        .waitUntilExit()

    val simulation = Simulation.fromEnvironment(env, BoundaryCondition(PERIODIC, PERIODIC, PERIODIC), 3.0)
//    simulation.tracker = NaiveListTracker()
//    simulation.tracker.trackAll(env.entities)
//    simulation.addWatcher(Dumper(simulation.timeStep * 200, "dump.lammpstrj"))

    simulation.forceComputers.add(LennardJonesCutOffForce(1 to 1, 1.0, 1.0))
    val reportGatherer = ReportGatherer(200 * simulation.timeStep).apply {
        reporters["ke"] = KineticEnergyReporter()
        reporters["pe"] = PotentialEnergyReporter()
        reporters["te"] = Reporter { KineticEnergyReporter().evaluate(it) + PotentialEnergyReporter().evaluate(it) }
    }
    simulation.addWatcher(reportGatherer)

    simulation.addWatcher(Dumper(simulation.timeStep, "dump.lammpstrj"))

    simulation.addWatcher(object : TimeAwareWatcher(simulation.timeStep) {
        override fun applyPeriodically(simulation: Simulation) {
            simulation.tracker
                .atoms
                .shuffled()
                .take((simulation.tracker.atoms.size * 0.1).toInt())
                .forEach {
                    it.phaseSpacePoint.velocity *= 0.9
                }
        }

    })


    simulation.tracker.atoms.forEach {
        it.phaseSpacePoint.velocity = Point.randomOrientation * 100
    }
    simulation.run(20.0)
    simulation.finish()
}