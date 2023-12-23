package org.isk.molekule.dynamic.report

import org.isk.molekule.dynamic.Simulation
import org.isk.molekule.dynamic.watcher.TimeAwareWatcher

class ReportGatherer(
    afterEach: Double
) : TimeAwareWatcher(afterEach) {
    val reporters: MutableMap<String, Reporter<*>> = mutableMapOf()
    override fun onRegister(simulation: Simulation) {
        println(reporters.keys.joinToString("\t"))
    }

    override fun applyPeriodically(simulation: Simulation) {
        println(reporters.values.map { it.evaluate(simulation) }.joinToString("\t"))
    }
}