package org.isk.molekule.gen.lattice

import org.isk.molekule.gen.geomertry.point.Point

interface Lattice {
    val points: Sequence<Point>
}

fun Sequence<Point>.spanInAllDirections(): Sequence<Point> =
    flatMap {
        with(it) {
            val reflected = mutableListOf(this)

            if (x != 0.0)
                reflected.add(copy(x = -x))

            if (y != 0.0)
                reflected.add(copy(y = -y))

            if (z != 0.0)
                reflected.add(copy(z = -z))


            if (x != 0.0 && y != 0.0)
                reflected.add(copy(x = -x, y = -y))

            if (x != 0.0 && z != 0.0)
                reflected.add(copy(x = -x, z = -z))

            if (z != 0.0 && y != 0.0)
                reflected.add(copy(z = -z, y = -y))

            if (z != 0.0 && y != 0.0 && x != 0.0)
                reflected.add(copy(z = -z, y = -y, x = -x))

            reflected
        }
    }
