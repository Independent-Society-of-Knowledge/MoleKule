package org.isk.molekule.dynamic.watcher

import org.isk.molekule.dynamic.Simulation

interface Watcher {
    fun trigger(simulation: Simulation)
    fun onRegister(simulation: Simulation) {}
    fun onRemove(simulation: Simulation) {}
}