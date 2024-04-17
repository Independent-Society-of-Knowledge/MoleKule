package org.isk.molekule.gen.lattice.unit.bravis

import org.isk.molekule.gen.geomertry.point.Point
import org.isk.molekule.gen.geomertry.point.rotateV
import org.isk.molekule.gen.lattice.unit.UnitCell
import org.isk.molekule.gen.lattice.unit.VectorBasedUnitCell
import org.isk.molekule.gen.utils.toRad
import kotlin.math.*

object Bravais3D {

    /**
     * all angels in rads
     * @see <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/1/17/Triclinic.svg/80px-Triclinic.svg.png"/>
     */
    fun triclinic(
        a: Double,
        b: Double,
        c: Double,
        alpha: Double,
        beta: Double,
        gamma: Double
    ): UnitCell {
        val a1 = Point(a, 0, 0)
        val a2 = Point(b * cos(gamma), b * sin(gamma), 0)


        // equation taken from here
        // https://www.aflowlib.org/prototype-encyclopedia/triclinic_lattice.html
        val a3x = c * cos(beta)
        val a3y = c * (cos(alpha) - cos(beta) * cos(gamma)) / sin(gamma)
        val a3z = sqrt(c.pow(2) - a3x.pow(2) - a3y.pow(2))

        val a3 = Point(a3x, a3y, a3z)

        return VectorBasedUnitCell(a1, a2, a3)
    }

    /**
     * beta in rads
     * @see <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/f/f4/Monoclinic.svg/80px-Monoclinic.svg.png"/>
     */
    fun monoclinic(
        a: Double,
        b: Double,
        c: Double,
        beta: Double,
    ): UnitCell = triclinic(
        a, b, c,
        PI / 2.0,
        beta,
        PI / 2.0
    )


    /**
     * @see <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/d/dd/Orthorhombic.svg/80px-Orthorhombic.svg.png"/>
     */
    fun orthorhombic(
        a: Double,
        b: Double,
        c: Double,
    ): UnitCell = monoclinic(a, b, c, PI / 2.0)

    /**
     * @see <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/0/0a/Tetragonal.svg/80px-Tetragonal.svg.png"/>
     */
    fun tetragonal(
        a: Double,
        c: Double
    ): UnitCell = orthorhombic(a, a, c)

    /**
     * @see <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Cubic.svg/80px-Cubic.svg.png"/>
     */
    fun cubic(a: Double) = tetragonal(a, a)


    /**
     * @see <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/Hexagonal_latticeFRONT.svg/80px-Hexagonal_latticeFRONT.svg.png"/>
     */
    fun hexagonal(a: Double, c: Double) =
        triclinic(a, a, c, 120.toRad(), PI / 2, 120.toRad())


    /**
     * @see <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/0/03/Rhombohedral.svg/80px-Rhombohedral.svg.png"/>
     */
    fun rhombohedral(a: Double, alpha: Double) =
        triclinic(a, a, a, alpha, alpha, alpha)
}
