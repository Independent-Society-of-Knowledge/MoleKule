package org.isk.molekule.gen.geomertry

import kotlin.math.PI
import kotlin.math.abs

class Tube(
    val beginMiddle: Point,
    val endMiddle: Point,
    val radius: Double
) : ClosedSurface {
    val middleLine = Line(beginMiddle to endMiddle)

    val length = (endMiddle - beginMiddle).norm
    val beginToEndDirection = (endMiddle - beginMiddle).normalize()
    val endToBeginDirection = (endMiddle - beginMiddle).normalize()
    override fun contains(point: Point): Boolean {
        val projectionAlongMiddleLine = beginToEndDirection dot (point - beginMiddle)
        return middleLine.distance(point) < radius &&
                projectionAlongMiddleLine < length &&
                projectionAlongMiddleLine > 0
    }


    override fun excludes(point: Point): Boolean {
        val projectionAlongMiddleLine = beginToEndDirection dot (point - beginMiddle)
        return middleLine.distance(point) > radius ||
                projectionAlongMiddleLine > length ||
                projectionAlongMiddleLine < 0
    }
}