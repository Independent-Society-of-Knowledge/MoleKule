package org.isk.molekule.gen.lattice.crystal

import org.isk.molekule.gen.geomertry.point.Point
import org.isk.molekule.gen.lattice.usingUnitCell

fun <T> Sequence<Point>.usingCrystal(crystal: Crystal<T>): Sequence<T> =
    usingUnitCell(crystal.unitCell)
        .flatMap { crystal.generate(it) }
