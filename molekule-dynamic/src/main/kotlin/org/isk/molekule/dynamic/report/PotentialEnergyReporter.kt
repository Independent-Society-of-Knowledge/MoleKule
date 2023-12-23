package org.isk.molekule.dynamic.report

import org.isk.molekule.dynamic.Simulation

class PotentialEnergyReporter(val type: Int? = null) : Reporter<Double> {
    override fun evaluate(simulation: Simulation): Double =
        simulation.tracker.atoms
            .filter { type == null || it.atom.type == type }
            .map { atom ->
                atom.attributes.computeIfAbsent("potential_energy_${type ?: "all"}") {
                    simulation.forceComputers.sumOf { it.potential(atom, simulation.elapsedTime) }
                } as Double
            }
            .sum()
}