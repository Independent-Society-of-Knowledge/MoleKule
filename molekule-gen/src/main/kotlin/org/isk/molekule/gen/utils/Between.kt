package org.isk.molekule.gen.utils

infix fun Double.between(range: Pair<Double, Double>): Boolean =
    range.first < this && this < range.second