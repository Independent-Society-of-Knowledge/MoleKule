package org.isk.molekule.core.geomertry

import org.isk.molekule.core.geomertry.point.Point

interface Distanceable {
    fun distance(point: Point): Double
}
