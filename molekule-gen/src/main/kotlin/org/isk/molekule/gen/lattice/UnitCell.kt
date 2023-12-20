package org.isk.molekule.gen.lattice

import org.isk.molekule.gen.geomertry.Point
import org.isk.molekule.gen.geomertry.times
import org.isk.molekule.gen.utils.toRad
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

interface UnitCell {

    val a1: Point
    val a2: Point
    val a3: Point

    fun generateLatticePoints(point: Point): Sequence<Point> = sequenceOf(point)

    companion object {
        fun fcc(latticeConstant: Double) = object : UnitCell {
            private val c = latticeConstant / 2
            override val a1: Point =
                Point(0, 1, 1) * c
            override val a2: Point =
                Point(1, 0, 1) * c
            override val a3: Point =
                Point(1, 1, 0) * c

        }

        fun grapheneZigZag(latticeConstant: Double) = object : UnitCell {

            val acc: Double = latticeConstant * sqrt(3.0)

            override val a1: Point = Point(acc, 0, 0).rotateZ(30.toRad())
            override val a2: Point = Point(0, acc, 0)
            override val a3: Point = Point.origin

            override fun generateLatticePoints(point: Point): Sequence<Point> =
                with(Point(latticeConstant, 0, 0)) {
                    sequenceOf(
                        point + this,
                        point + this.rotateZ(60.toRad()),
                        point + this.rotateZ((-60).toRad()),
                        point + this.rotateZ(120.toRad()),
                        point + this.rotateZ((-120).toRad()),
                        point + this.rotateZ(180.toRad()),
                        point - this
                    )
                }
        }

    }
}