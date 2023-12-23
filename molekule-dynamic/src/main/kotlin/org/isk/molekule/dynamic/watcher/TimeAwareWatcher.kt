package org.isk.molekule.dynamic.watcher

import org.isk.molekule.dynamic.Simulation

abstract class TimeAwareWatcher(
    var afterEach: Double
) : Watcher {
    private var lastInvoke = 0L
    abstract fun applyPeriodically(simulation: Simulation)
    final override fun trigger(simulation: Simulation) {
        if (lastInvoke * afterEach < simulation.elapsedTime) {
            applyPeriodically(simulation)
            lastInvoke++
        }
    }
}