package org.isk.molekule.gen.geomertry

data class Sphere(
    val middle: Point,
    val radius: Double
) : ClosedSurface {

    override fun contains(point: Point): Boolean =
        point.distance(middle) < radius

    override fun excludes(point: Point): Boolean =
        point.distance(middle) > radius

}