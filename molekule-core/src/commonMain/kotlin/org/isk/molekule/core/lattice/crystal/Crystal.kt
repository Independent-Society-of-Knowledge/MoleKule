package org.isk.molekule.core.lattice.crystal

import org.isk.molekule.core.geomertry.point.Point
import org.isk.molekule.core.lattice.crystal.site.Famous
import org.isk.molekule.core.lattice.crystal.site.Graphene
import org.isk.molekule.core.lattice.unit.UnitCell

/**
 * @param T: anything that is generated in this crystal
 */
abstract class Crystal<T>(val unitCell: UnitCell) {
    /**
     * @param basePoint: given this point generate list of items to be used in next stage
     */
    abstract fun generate(basePoint: Point): List<T>

    companion object {
        val graphene = Graphene
        val famous = Famous
    }
}
