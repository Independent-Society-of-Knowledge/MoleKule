package org.isk.molekule.gen.geomertry

import org.isk.molekule.gen.utils.between
import org.isk.molekule.gen.utils.outside

data class Box(
    val xBoundary: Pair<Double, Double>,
    val yBoundary: Pair<Double, Double>,
    val zBoundary: Pair<Double, Double>,
) : ClosedSurface {

    constructor(
        xLow: Double, xHigh: Double,
        yLow: Double, yHigh: Double,
        zLow: Double, zHigh: Double,
    ) : this(
        xLow to xHigh,
        yLow to yHigh,
        zLow to zHigh
    )

    init {
        if (xBoundary.first > xBoundary.second)
            throw IllegalArgumentException("x boundary is not valid: $xBoundary should from low to high and not equal")

        if (yBoundary.first > yBoundary.second)
            throw IllegalArgumentException("y boundary is not valid: $yBoundary should from low to high and not equal")

        if (zBoundary.first > zBoundary.second)
            throw IllegalArgumentException("z boundary is not valid: $zBoundary should from low to high and not equal")
    }

    val xLow
        get() = xBoundary.first
    val xHigh
        get() = xBoundary.second


    val yLow
        get() = yBoundary.first
    val yHigh
        get() = yBoundary.second


    val zLow
        get() = zBoundary.first
    val zHigh
        get() = zBoundary.second

    override fun contains(point: Point): Boolean =
        with(point) {
            x between xBoundary && y between yBoundary && z between zBoundary
        }

    override fun excludes(point: Point): Boolean =
        with(point) {
            x outside xBoundary || y outside yBoundary || z outside zBoundary
        }
}