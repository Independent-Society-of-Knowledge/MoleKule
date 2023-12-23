package org.isk.molekule.dynamic.integrator

import org.isk.molekule.dynamic.PhaseSpacePoint
import org.isk.molekule.dynamic.times


class RK4Integrator : Integrator {
    override fun evolve(dt: Double, qp: PhaseSpacePoint, time: Double, a: AccelerationEvaluator): PhaseSpacePoint {

        fun f(point: PhaseSpacePoint, t: Double): PhaseSpacePoint =
            PhaseSpacePoint(point.velocity, a(point, time))

        val halfStep = dt / 2.0

        val k1 = f(qp, time)
        val k2 = f(qp + halfStep * k1, time + halfStep)
        val k3 = f(qp + halfStep * k2, time + halfStep)
        val k4 = f(qp + dt * k3, time + dt)

        return qp + (dt / 6.0) * (k1 + 2 * k2 + 2 * k3 + k4)
    }

}