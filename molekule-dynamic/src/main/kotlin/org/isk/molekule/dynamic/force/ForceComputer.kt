package org.isk.molekule.dynamic.force

import org.isk.molekule.dynamic.tracker.PhaseAtom
import org.isk.molekule.gen.geomertry.point.Point

interface ForceComputer {
    fun force(phaseAtom: PhaseAtom, time: Double): Point
    fun potential(phaseAtom: PhaseAtom, time: Double): Double
}
