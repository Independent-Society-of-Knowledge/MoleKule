package org.isk.molekule.gen.utils

infix fun Double.outside(range: Pair<Double, Double>) =
    this < range.first || this > range.second