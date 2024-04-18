package org.isk.molekule.gen.geomertry.point

import kotlin.math.abs

val Pair<Number, Number>.length: Double
    get() = abs(second.toDouble() - first.toDouble())
