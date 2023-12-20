package org.isk.molekule.gen.utils

infix fun Double.outside(range: Pair<Number, Number>) =
    this < range.first.toDouble() || this > range.second.toDouble()