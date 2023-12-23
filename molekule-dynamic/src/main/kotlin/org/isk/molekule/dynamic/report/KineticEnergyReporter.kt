package org.isk.molekule.dynamic.report

import org.isk.molekule.dynamic.Simulation

class KineticEnergyReporter(
    private val type: Int? = null
) : Reporter<Double> {
    override fun evaluate(simulation: Simulation): Double =
        simulation.tracker.atoms
            .filter { type == null || it.atom.type == type }
            .map { 0.5 * it.atom.mass * it.phaseSpacePoint.velocity.norm2 }
            .sum()
}