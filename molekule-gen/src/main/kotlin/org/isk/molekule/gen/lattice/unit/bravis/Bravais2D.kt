package org.isk.molekule.gen.lattice.unit.bravis

import org.isk.molekule.gen.geomertry.point.Point
import org.isk.molekule.gen.lattice.unit.UnitCell
import org.isk.molekule.gen.lattice.unit.VectorBasedUnitCell
import org.isk.molekule.gen.utils.toRad
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

object Bravais2D {


    /**
     * theta in rads
     * @see <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/c2/2d_mp.svg/90px-2d_mp.svg.png"/>
     */
    fun monoclinic(a: Double, b: Double, theta: Double): UnitCell {
        return VectorBasedUnitCell(
            Point(a, 0, 0),
            Point(b * cos(theta), b * sin(theta), 0),
            Point(0, 0, 0),
        )
    }

    /**
     * @see <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/e/ee/2d_op_rectangular.svg/110px-2d_op_rectangular.svg.png"/>
     */
    fun orthorhombic(a: Double, b: Double): UnitCell =
        monoclinic(a, b, PI / 2.0)


    /**
     * @see <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/f/f7/2d_tp.svg/90px-2d_tp.svg.png"/>
     */
    fun tetragonal(a: Double): UnitCell =
        orthorhombic(a, a)


    /**
     * @see <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/0/0a/2d_hp.svg/100px-2d_hp.svg.png"/>
     */
    fun hexagonal(a: Double): UnitCell =
        monoclinic(a, a, 120.toRad())
}
