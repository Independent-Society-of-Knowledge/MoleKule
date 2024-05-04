package org.isk.molekule.core.lattice.unit

import org.isk.molekule.core.geomertry.point.Point
import space.kscience.kmath.linear.*
import space.kscience.kmath.linear.DoubleLinearSpace.dot

typealias Basis = Array<Point>

interface UnitCell {

    val a1: Point
    val a2: Point
    val a3: Point

    val volume: Double
        get() = (a1 cross a2) dot a3

    fun direct(point: Point): Point =
        with(point) {
            (a1 * x) + (a2 * y) + (a3 * z)
        }

    fun decompose(point: Point): Point {
        // Mx = b
        // x = M-1 b
        val matrix = DoubleLinearSpace.buildMatrix(3, 3) { i, j ->
            val vector = if (i == 0) a1 else if (i == 1) a2 else a3
            val number = if (j == 0) vector.x else if (j == 1) vector.y else vector.z
            number
        }
        val inverse = DoubleLinearSpace.lupSolver().inverse(matrix)
        val vec = inverse.dot(point.km_vector)
        return Point(vec[0], vec[1], vec[2])
    }

    val basis: Basis
        get() = arrayOf(a1, a2, a3)
}
