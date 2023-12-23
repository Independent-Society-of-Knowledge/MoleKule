package org.isk.molekule.dynamic.integrator

import org.isk.molekule.dynamic.PhaseSpacePoint

class VerletIntegrator : Integrator {
    override fun evolve(dt: Double, qp: PhaseSpacePoint, time: Double, a: AccelerationEvaluator): PhaseSpacePoint {
        val acc = a(qp, time)
        val newPos = qp.position + (qp.velocity * dt) + acc * (dt * dt * 0.5)
        val newAcc = a(PhaseSpacePoint(newPos, qp.velocity), time)
        val newVel = qp.velocity + (acc + newAcc) * (dt * 0.5)
        return PhaseSpacePoint(newPos, newVel)
    }

}