package org.isk.molekule.gen.lattice.crystal.site

import org.isk.molekule.gen.geomertry.point.Point
import org.isk.molekule.gen.lattice.unit.VectorBasedUnitCell
import org.isk.molekule.gen.utils.toRad
import kotlin.math.sqrt


object Graphene {
    fun zigzag(latticeConstant: Double): SiteBasedCrystal {
        val cell = VectorBasedUnitCell(
            Point(latticeConstant, 0, 0) + Point(latticeConstant, 0, 0).rotateZ(60.toRad()),
            Point(latticeConstant, 0, 0) + Point(latticeConstant, 0, 0).rotateZ((-60).toRad()),
            Point.origin
        )
        return object : SiteBasedCrystal(cell) {
            override fun generate(basePoint: Point): List<Point> =
                listOf(basePoint, basePoint + Point(latticeConstant, 0, 0))
        }
    }

    fun armchair(latticeConstant: Double): SiteBasedCrystal {
        val cell = VectorBasedUnitCell(
            Point(latticeConstant * sqrt(3.0), 0, 0),
            Point(latticeConstant * sqrt(3.0), 0, 0).rotateZ(120.toRad()),
            Point.origin
        )
        return object : SiteBasedCrystal(cell) {
            override fun generate(basePoint: Point): List<Point> {
                return listOf(basePoint, basePoint + Point(0, latticeConstant, 0))
            }

        }
    }
}
