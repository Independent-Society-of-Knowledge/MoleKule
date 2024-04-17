package org.isk.molekule.gen.geomertry

import org.isk.molekule.gen.geomertry.point.Point

interface Distanceable {
    fun distance(point: Point): Double
}
