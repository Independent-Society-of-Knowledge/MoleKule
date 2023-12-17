package org.isk.molekule.gen.geomertry

import kotlin.math.PI

class Tube(
    val beginMiddle: Point,
    val endMiddle: Point,
    val radius: Double
) : ClosedSurface {
    val middleLine = Line(beginMiddle to endMiddle)

    private val beginToEnd = endMiddle - beginMiddle
    private val endToBegin = -1 * beginToEnd
    override fun isInside(point: Point): Boolean =
        middleLine.distance(point) < radius &&
                (beginToEnd.angle(point) < PI / 2.0) &&
                (endToBegin.angle(point) < PI / 2.0)

    override fun isOutSide(point: Point): Boolean =
        middleLine.distance(point) > radius ||
                (beginToEnd.angle(point) > PI / 2.0) ||
                (endToBegin.angle(point) > PI / 2.0)
}