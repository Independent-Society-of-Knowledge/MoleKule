package org.isk.molekule.core.utils

infix fun Double.outside(range: Pair<Number, Number>) =
    this < range.first.toDouble() || this > range.second.toDouble()