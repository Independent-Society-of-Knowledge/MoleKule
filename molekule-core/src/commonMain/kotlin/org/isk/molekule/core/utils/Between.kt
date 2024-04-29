package org.isk.molekule.core.utils

infix fun Double.between(range: Pair<Number, Number>): Boolean =
    range.first.toDouble() < this && this < range.second.toDouble()