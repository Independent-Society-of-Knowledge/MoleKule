package org.isk.molekule.core.geomertry.point

import org.isk.molekule.core.geomertry.Distanceable
import kotlin.math.*
import kotlin.random.Random

data class Point(
    var x: Double, var y: Double, var z: Double
) : Distanceable {

    constructor(x: Number, y: Number, z: Number) : this(x.toDouble(), y.toDouble(), z.toDouble())

    override fun distance(point: Point): Double {
        return sqrt(
            (x - point.x).pow(2) + (y - point.y).pow(2) + (z - point.z).pow(2)
        )
    }

    operator fun plus(other: Point) = Point(x + other.x, y + other.y, z + other.z)
    operator fun times(number: Number) = with(number.toDouble()) { Point(this * x, this * y, this * z) }
    operator fun div(number: Number) = with(number.toDouble()) { Point(x / this, y / this, z / this) }

    operator fun minus(other: Point) = Point(x - other.x, y - other.y, z - other.z)

    infix fun dot(other: Point): Double = x * other.x + y * other.y + z * other.z
    infix fun cross(other: Point): Point =
        Point(
            y * other.z - z * other.y,
            x * other.z - z * other.x,
            x * other.y - y * other.x
        )

    /**
     * radians
     */
    infix fun angle(other: Point): Double = acos((this dot other) / (norm * other.norm))

    val norm2
        get() = this dot this
    val norm
        get() = sqrt(norm2)


    fun rotateZ(rad: Double): Point {
        val sinRad = sin(rad)
        val cosRad = cos(rad)
        return Point(
            x * cosRad - y * sinRad, x * sinRad + y * cosRad, z
        )
    }


    fun rotateY(rad: Double): Point {
        val sinRad = sin(rad)
        val cosRad = cos(rad)
        return Point(
            x * cosRad + z * sinRad, y, -x * sinRad + z * cosRad
        )
    }


    fun rotateX(rad: Double): Point {
        val sinRad = sin(rad)
        val cosRad = cos(rad)
        return Point(
            x, y * cosRad + z * sinRad, y * sinRad + z * cosRad
        )
    }

    fun normalize() = this / norm

    companion object {
        val origin: Point = Point(0, 0, 0)

        val xHat = Point(1.0, 0.0, 0.0)
        val yHat = Point(0.0, 1.0, 0.0)
        val zHat = Point(0.0, 0.0, 1.0)

        val randomOrientation: Point
            get() = Point(
                Random.nextDouble(-1.0, 1.0),
                Random.nextDouble(-1.0, 1.0),
                Random.nextDouble(-1.0, 1.0),
            ).normalize()
    }
}

operator fun Number.times(point: Point) = point.times(this)
