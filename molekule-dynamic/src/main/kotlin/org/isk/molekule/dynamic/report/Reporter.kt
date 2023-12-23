package org.isk.molekule.dynamic.report

import org.isk.molekule.dynamic.Simulation

fun interface Reporter<T> {
    fun evaluate(simulation: Simulation): T
}