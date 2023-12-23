package org.isk.molekule.dynamic.watcher

import org.isk.molekule.dynamic.Simulation
import org.isk.molekule.gen.utils.outside
import kotlin.math.absoluteValue

enum class BoundaryType {
    FIXED,
    PERIODIC
}

class BoundaryCondition(
    val x: BoundaryType,
    val y: BoundaryType,
    val z: BoundaryType
) : Watcher {
    override fun trigger(simulation: Simulation) {
        simulation.tracker.atoms.forEach { phaseAtom ->
            if (phaseAtom.phaseSpacePoint.position.x outside simulation.enclosingBox.xBoundary) {
                if (x == BoundaryType.FIXED) {
                    phaseAtom.phaseSpacePoint.velocity.x *= -1
                } else {
                    phaseAtom.phaseSpacePoint.position.x =
                        if (phaseAtom.phaseSpacePoint.position.x < simulation.enclosingBox.xLow)
                            simulation.enclosingBox.xHigh - (simulation.enclosingBox.xLow - phaseAtom.phaseSpacePoint.position.x).absoluteValue
                        else
                            simulation.enclosingBox.xLow + (simulation.enclosingBox.xHigh - phaseAtom.phaseSpacePoint.position.x).absoluteValue
                }
            }
            if (phaseAtom.phaseSpacePoint.position.y outside simulation.enclosingBox.yBoundary) {
                if (y == BoundaryType.FIXED) {
                    phaseAtom.phaseSpacePoint.velocity.y *= -1
                } else {
                    phaseAtom.phaseSpacePoint.position.y =
                        if (phaseAtom.phaseSpacePoint.position.y < simulation.enclosingBox.yLow)
                            simulation.enclosingBox.yHigh - (simulation.enclosingBox.yLow - phaseAtom.phaseSpacePoint.position.y).absoluteValue
                        else
                            simulation.enclosingBox.yLow + (simulation.enclosingBox.yHigh - phaseAtom.phaseSpacePoint.position.y).absoluteValue
                }
            }
            if (phaseAtom.phaseSpacePoint.position.z outside simulation.enclosingBox.zBoundary) {
                if (x == BoundaryType.FIXED) {
                    phaseAtom.phaseSpacePoint.velocity.z *= -1
                } else {
                    phaseAtom.phaseSpacePoint.position.z =
                        if (phaseAtom.phaseSpacePoint.position.z < simulation.enclosingBox.zLow)
                            simulation.enclosingBox.zHigh - (simulation.enclosingBox.zLow - phaseAtom.phaseSpacePoint.position.z).absoluteValue
                        else
                            simulation.enclosingBox.zLow + (simulation.enclosingBox.zHigh - phaseAtom.phaseSpacePoint.position.z).absoluteValue
                }
            }
        }
    }
}