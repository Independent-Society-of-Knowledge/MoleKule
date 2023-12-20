package org.isk.molekule.gen.utils

import org.isk.molekule.gen.geomertry.Point

fun Sequence<Point>.removeDuplicates(distanceThreshold: Double = 10e-5): Sequence<Point> =
    this.distinctBy {
        Triple(
            (it.x / distanceThreshold).toLong(),
            (it.y/distanceThreshold).toLong(),
            (it.z/distanceThreshold).toLong()
        )
    }