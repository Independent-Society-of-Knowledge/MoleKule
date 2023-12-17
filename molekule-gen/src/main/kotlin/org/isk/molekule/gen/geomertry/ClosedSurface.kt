package org.isk.molekule.gen.geomertry

interface ClosedSurface {
    fun isInside(point: Point): Boolean

    fun isOutSide(point: Point): Boolean
    fun isOnSurface(point: Point): Boolean =
        !isInside(point) && !isOutSide(point)


    operator fun plus(other: ClosedSurface) =
        object : ClosedSurface {
            override fun isInside(point: Point): Boolean =
                this@ClosedSurface.isInside(point) || other.isInside(point)

            override fun isOutSide(point: Point): Boolean =
                this@ClosedSurface.isOutSide(point) && other.isOutSide(point)
        }

    operator fun minus(other: ClosedSurface) =
        object : ClosedSurface {
            override fun isInside(point: Point): Boolean =
                this@ClosedSurface.isInside(point) && other.isOutSide(point)

            override fun isOutSide(point: Point): Boolean =
                this@ClosedSurface.isOutSide(point) || other.isInside(point)
        }


}