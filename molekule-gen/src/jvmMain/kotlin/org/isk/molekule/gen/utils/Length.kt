package org.isk.molekule.gen.utils

import kotlin.math.absoluteValue

fun Pair<Number, Number>.length(): Double =
    (first.toDouble() - second.toDouble()).absoluteValue