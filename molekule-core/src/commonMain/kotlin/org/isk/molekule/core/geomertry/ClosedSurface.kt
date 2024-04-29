package org.isk.molekule.core.geomertry

import org.isk.molekule.core.geomertry.point.Point

interface ClosedSurface {
    fun contains(point: Point): Boolean
    fun excludes(point: Point): Boolean
    fun touches(point: Point): Boolean =
        !contains(point) && !excludes(point)


    infix operator fun plus(other: ClosedSurface) =
        object : ClosedSurface {
            override fun contains(point: Point): Boolean =
                this@ClosedSurface.contains(point) || other.contains(point)

            override fun excludes(point: Point): Boolean =
                this@ClosedSurface.excludes(point) && other.excludes(point)
        }

    infix operator fun minus(other: ClosedSurface) =
        object : ClosedSurface {
            override fun contains(point: Point): Boolean =
                this@ClosedSurface.contains(point) && other.excludes(point)

            override fun excludes(point: Point): Boolean =
                this@ClosedSurface.excludes(point) || other.contains(point)
        }

    infix fun intersect(other: ClosedSurface): ClosedSurface =
        object : ClosedSurface {
            override fun contains(point: Point): Boolean =
                this@ClosedSurface.contains(point) && other.contains(point)

            override fun excludes(point: Point): Boolean =
                this@ClosedSurface.excludes(point) || other.excludes(point)

        }


    companion object {
        val EMPTY = object : ClosedSurface {
            override fun contains(point: Point): Boolean = false

            override fun excludes(point: Point): Boolean = true
        }
    }

}

infix fun Point.isInside(c: ClosedSurface): Boolean = c.contains(this)
infix fun Point.isOutside(c: ClosedSurface): Boolean = c.excludes(this)
infix fun Point.isOnSurface(c: ClosedSurface): Boolean = c.touches(this)
