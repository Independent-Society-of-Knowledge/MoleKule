package org.isk.molekule.core.lattice.crystal.site

import org.isk.molekule.core.geomertry.point.Point
import org.isk.molekule.core.lattice.unit.bravis.Bravais3D

object Famous {
    fun diamond(latticeConstant: Double): SiteBasedCrystal {
        val cell = Bravais3D.cubicFaceCentered(latticeConstant)
        return object : SiteBasedCrystal(cell) {
            override fun generate(basePoint: Point): List<Point> {
                return listOf(
                    basePoint,
                    basePoint + cell.direct(0.25, 0.25, 0.25)
                )
            }

        }
    }
}
