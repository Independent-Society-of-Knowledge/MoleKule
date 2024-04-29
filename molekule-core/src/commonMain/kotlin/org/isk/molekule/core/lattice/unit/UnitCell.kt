package org.isk.molekule.core.lattice.unit

import org.isk.molekule.core.geomertry.point.Point

interface UnitCell {

    val a1: Point
    val a2: Point
    val a3: Point

    val volume: Double
        get() = (a1 cross a2) dot a3

    fun direct(x: Double, y: Double, z: Double): Point =
        (a1 * x) + (a2 * y) + (a3 * z)
}
