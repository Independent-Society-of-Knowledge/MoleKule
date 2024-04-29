package org.isk.molekule.core.geomertry

import org.isk.molekule.core.geomertry.point.Point

class Tube(
    val beginMiddle: Point, val endMiddle: Point, val radius: Number
) : ClosedSurface {
    val middleLine = Line(beginMiddle to endMiddle)

    val length = (endMiddle - beginMiddle).norm
    val beginToEndDirection = (endMiddle - beginMiddle).normalize()
    val endToBeginDirection = (endMiddle - beginMiddle).normalize()
    override fun contains(point: Point): Boolean {
        val projectionAlongMiddleLine = beginToEndDirection dot (point - beginMiddle)
        return middleLine.distance(point) < radius.toDouble() && projectionAlongMiddleLine < length && projectionAlongMiddleLine > 0
    }


    override fun excludes(point: Point): Boolean {
        val projectionAlongMiddleLine = beginToEndDirection dot (point - beginMiddle)
        return middleLine.distance(point) > radius.toDouble() || projectionAlongMiddleLine > length || projectionAlongMiddleLine < 0
    }
}
