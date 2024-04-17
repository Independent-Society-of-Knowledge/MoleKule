package org.isk.molekule.gen.geomertry

import org.isk.molekule.gen.geomertry.point.Point

data class Sphere(
    val middle: Point,
    val radius: Number
) : ClosedSurface {

    override fun contains(point: Point): Boolean =
        point.distance(middle) < radius.toDouble()

    override fun excludes(point: Point): Boolean =
        point.distance(middle) > radius.toDouble()

}
