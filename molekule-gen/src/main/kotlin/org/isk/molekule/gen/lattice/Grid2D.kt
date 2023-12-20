package org.isk.molekule.gen.lattice

import org.isk.molekule.gen.geomertry.Point

class Grid2D(val width: Long, val height: Long) : Lattice {
    private val thinGrid = Grid3D(width, height, 1)
    override val points: Sequence<Point>
        get() = thinGrid.points

}