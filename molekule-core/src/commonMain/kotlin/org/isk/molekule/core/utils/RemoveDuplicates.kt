package org.isk.molekule.core.utils

import org.isk.molekule.core.geomertry.point.Point

fun Sequence<Point>.removeDuplicates(distanceThreshold: Double = 10e-2): Sequence<Point> =
    this.distinctBy {
        Triple(
            (it.x / distanceThreshold).toLong(),
            (it.y/distanceThreshold).toLong(),
            (it.z/distanceThreshold).toLong()
        )
    }
