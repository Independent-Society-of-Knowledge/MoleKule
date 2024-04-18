package org.isk.molekule.gen.geomertry

import org.isk.molekule.gen.geomertry.point.Point
import org.isk.molekule.gen.utils.between
import org.isk.molekule.gen.utils.outside

data class Box(
    val xBoundary: Pair<Number, Number>,
    val yBoundary: Pair<Number, Number>,
    val zBoundary: Pair<Number, Number>,
) : ClosedSurface {

    constructor(
        xLow: Number, xHigh: Number,
        yLow: Number, yHigh: Number,
        zLow: Number, zHigh: Number,
    ) : this(
        xLow to xHigh,
        yLow to yHigh,
        zLow to zHigh
    )


    val xLow
        get() = xBoundary.first.toDouble()
    val xHigh
        get() = xBoundary.second.toDouble()


    val yLow
        get() = yBoundary.first.toDouble()
    val yHigh
        get() = yBoundary.second.toDouble()


    val zLow
        get() = zBoundary.first.toDouble()
    val zHigh
        get() = zBoundary.second.toDouble()


    init {
        if (xLow > xHigh)
            throw IllegalArgumentException("x boundary is not valid: $xBoundary should from low to high and not equal")

        if (yLow > yHigh)
            throw IllegalArgumentException("y boundary is not valid: $yBoundary should from low to high and not equal")

        if (zLow > zHigh)
            throw IllegalArgumentException("z boundary is not valid: $zBoundary should from low to high and not equal")
    }

    override fun contains(point: Point): Boolean =
        with(point) {
            x between xBoundary && y between yBoundary && z between zBoundary
        }

    override fun excludes(point: Point): Boolean =
        with(point) {
            x outside xBoundary || y outside yBoundary || z outside zBoundary
        }
}
