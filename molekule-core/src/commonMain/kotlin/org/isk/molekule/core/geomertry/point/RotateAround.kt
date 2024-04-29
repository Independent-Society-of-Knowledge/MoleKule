package org.isk.molekule.core.geomertry.point

import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin


fun Point.rotateV(axis: Point, rad: Double): Point {

    val cos = cos(rad)
    val sin = sin(rad)
    val w = axis.normalize()
    return Point(
        (cos + w.x.pow(2) * (1 - cos)) * this.x + (w.x * w.y * (1 - cos) - w.z * sin) * this.y + (w.y * sin + w.x * w.z * (1 - cos)) * this.z,
        (w.z * sin + w.x * w.y * (1 - cos)) * this.x + (cos + w.y.pow(2) * (1 - cos)) * this.y + (-w.x * sin + w.y * w.z * (1 - cos)) * this.z,
        (-w.y * sin + w.x * w.z * (1 - cos)) * this.x + (w.x * sin + w.y * w.z * (1 - cos)) * this.y + (cos + w.z.pow(2) * (1 - cos)) * this.z
    )
}
