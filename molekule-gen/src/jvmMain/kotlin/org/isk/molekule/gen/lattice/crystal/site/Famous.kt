package org.isk.molekule.gen.lattice.crystal.site

import org.isk.molekule.gen.geomertry.point.Point
import org.isk.molekule.gen.lattice.unit.VectorBasedUnitCell
import org.isk.molekule.gen.lattice.unit.bravis.Bravais3D
import org.isk.molekule.gen.utils.toRad
import kotlin.math.sqrt

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
