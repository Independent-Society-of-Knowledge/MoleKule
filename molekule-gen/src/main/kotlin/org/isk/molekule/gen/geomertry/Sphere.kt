package org.isk.molekule.gen.geomertry

data class Sphere(
    val middle: Point,
    val radius: Double
) : ClosedSurface {

    override fun isInside(point: Point): Boolean =
        point.distance(middle) < radius

    override fun isOutSide(point: Point): Boolean =
        point.distance(middle) > radius

}