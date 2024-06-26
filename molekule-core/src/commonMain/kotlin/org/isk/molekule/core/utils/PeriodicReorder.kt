package org.isk.molekule.core.utils

import org.isk.molekule.core.geomertry.Box
import org.isk.molekule.core.geomertry.point.Point
import org.isk.molekule.core.geomertry.point.length
import kotlin.math.abs

fun Sequence<Point>.periodicReorder(box: Box, removeEscapedPoints: Boolean = false): Sequence<Point> {

    val edgeVector = box.edge

    val lx = box.xBoundary.length
    val ly = box.yBoundary.length
    val lz = box.zBoundary.length

    return mapNotNull {
        if (it isInPeriodicRegion box)
            return@mapNotNull it

        // point in box coordinate system
        val point = it - edgeVector

        val escape = (point.x <= -lx || point.x >= 2 * lx) ||
                (point.y <= -ly || point.y >= 2 * ly) ||
                (point.z <= -lz || point.z >= 2 * lz)

        if (escape) {
            if (removeEscapedPoints)
                return@mapNotNull null
            else
                throw IllegalArgumentException("Point Escaped 2 times the length of box, it's at infinite now")
        }



        return@mapNotNull Point(point.x.mod(lx), point.y.mod(ly), point.z.mod(lz)) + edgeVector
    }
}