package org.isk.molekule.dynamic.force

import org.isk.molekule.dynamic.tracker.PhaseAtom
import org.isk.molekule.core.geomertry.point.Point
import kotlin.math.pow

class LennardJonesCutOffForce(
    val interaction: Pair<Int, Int>,
    val epsilon: Double,
    val sigma: Double,
    val farCutOff: Double = 2.5 * sigma,
    val nearCutOff: Double = 1.0 * sigma
) :
    ForceComputer {
    override fun force(phaseAtom: PhaseAtom, time: Double): Point {
        // if this atom doesn't interact - return 0.0
        if (phaseAtom.atom.type != interaction.first || phaseAtom.atom.type != interaction.second)
            return Point.origin
        val p1 = phaseAtom.phaseSpacePoint.position

        return phaseAtom.neighboringPhaseAtoms
            // filter non-interacting
            .filter {
                it.atom.type == interaction.first || it.atom.type == interaction.second
            }
            // select position
            .map {
                it.phaseSpacePoint.position
            }
            // select line and norm
            .map { (p1 - it) to it.distance(p1) }
            // select near
            .filter { it.second in nearCutOff..farCutOff }
            // normalize line and norm to sigma
            .map { it.first.normalize() to (it.second / sigma) }
            .map {
                // vector part of lj
                it.first * (2 * it.second.pow(-13) - it.second.pow(-7))
            }
            .fold(Point.origin) { acc, p -> acc + p } * (24 * epsilon / sigma)

    }

    override fun potential(phaseAtom: PhaseAtom, time: Double): Double {
        // if this atom doesn't interact - return 0.0
        if (phaseAtom.atom.type != interaction.first || phaseAtom.atom.type != interaction.second)
            return 0.0
        val p1 = phaseAtom.phaseSpacePoint.position

        return phaseAtom.neighboringPhaseAtoms
            // filter non-interacting
            .filter {
                it.atom.type == interaction.first || it.atom.type == interaction.second
            }
            // select position
            .map {
                it.phaseSpacePoint.position.distance(p1)
            }
            // select line and norm
            .filter { it in nearCutOff..farCutOff }
            .map { (it / sigma).pow(-6) }
            .map { it * (it - 1) }
            .sum() * 4 * epsilon

    }
}
