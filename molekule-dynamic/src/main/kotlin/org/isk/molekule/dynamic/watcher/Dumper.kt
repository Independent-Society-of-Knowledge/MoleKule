package org.isk.molekule.dynamic.watcher

import org.isk.molekule.dynamic.Simulation
import org.isk.molekule.core.plus
import java.io.File
import java.io.FileOutputStream

class Dumper(afterEach: Double, val path: String) : TimeAwareWatcher(afterEach) {
    override fun onRegister(simulation: Simulation) {
        simulation.dumpLammps(path, (simulation.elapsedTime / simulation.timeStep).toLong())
    }

    override fun applyPeriodically(simulation: Simulation) {
        simulation.dumpLammps(path, (simulation.elapsedTime / simulation.timeStep).toLong())
    }
}

fun Simulation.dumpLammps(
    filePath: String,
    timeStamps: Long = 0,
    bounds: String = "pp pp pp",
) {
    val labeledAtoms = tracker.atoms.mapIndexed { index, atom -> atom to index + 1 }.toMap()
    val box = enclosingBox
    FileOutputStream(File(filePath), true).use { oups ->
        oups +
                ("""
        ITEM: TIMESTEP
        $timeStamps
        ITEM: NUMBER OF ATOMS
        ${labeledAtoms.size}
        ITEM: BOX BOUNDS $bounds
        ${box.xLow} ${box.xHigh}
        ${box.yLow} ${box.yHigh}
        ${box.zLow} ${box.zHigh}
        ITEM: ATOMS id type xs ys zs
        
        """.trimIndent() + labeledAtoms.map { (atom, index) ->
                    "$index ${atom.atom.type} ${atom.phaseSpacePoint.position.x} ${atom.phaseSpacePoint.position.y} ${atom.phaseSpacePoint.position.z}"
                }.joinToString("\n"))
    }
}