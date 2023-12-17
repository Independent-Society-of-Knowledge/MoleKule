package org.isk.molekule.gen.utils

import kotlin.math.PI

private const val radConverter = PI / 180.0
fun Number.toRad(): Double =
    this.toDouble() * radConverter