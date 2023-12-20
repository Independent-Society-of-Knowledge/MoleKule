package org.isk.molekule.gen.lattice

import org.isk.molekule.gen.geomertry.Point

open class Grid3D(
    val width: Long,
    val height: Long,
    val depth: Long,
) : Lattice {
    override val points: Sequence<Point>
        get() = sequence {
            for (x in 0 until width)
                for (y in 0 until height)
                    for (z in 0 until depth)
                        yield(Point(x, y, z))
        }
}