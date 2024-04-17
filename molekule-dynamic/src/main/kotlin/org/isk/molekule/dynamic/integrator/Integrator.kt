package org.isk.molekule.dynamic.integrator

import org.isk.molekule.dynamic.PhaseSpacePoint
import org.isk.molekule.gen.geomertry.point.Point


typealias AccelerationEvaluator = (qp: PhaseSpacePoint, time: Double) -> Point

/**
 * evaluates evolution of given phase-point qp until next infinitesimal time dt under acceleration a
 */
interface Integrator {
    fun evolve(dt: Double, qp: PhaseSpacePoint, time: Double, a: AccelerationEvaluator): PhaseSpacePoint
}
