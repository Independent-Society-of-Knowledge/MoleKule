package org.isk.molekule.core.geomertry

import org.isk.molekule.core.geomertry.point.Point
import org.isk.molekule.core.geomertry.point.times

data class Line(
    val point: Point,
    val vector: Point
) : Distanceable {
    constructor(points: Pair<Point, Point>) : this(
        points.first,
        with(points) { Point(second.x - first.x, second.y - first.y, second.z - first.z) }
    )

    override fun distance(point: Point): Double {
        // l = point + t*vector where t is a variable
        // min( |l - b| ) is what we look for
        // min( |p + t*v - b |)
        // min( |c + t*v |) where c = p - b
        // d/dt ( c2 +t2v2 +2tv.c ) = 2tv2 + 2v.c = 0 -> t* = v.c/v2
        // minVec = p - c.v/v2 * v - b

        val p = this.point
        val b = point
        val v = vector

        val c = p - b
        val tStar = - (v dot c) / v.norm2

        val minVector = p + tStar * v - b

        return minVector.norm
    }
}
