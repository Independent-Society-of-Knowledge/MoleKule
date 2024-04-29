package org.isk.molekule.core.lattice.crystal

import org.isk.molekule.core.geomertry.point.Point
import org.isk.molekule.core.lattice.usingUnitCell

fun <T> Sequence<Point>.usingCrystal(crystal: Crystal<T>): Sequence<T> =
    usingUnitCell(crystal.unitCell)
        .flatMap { crystal.generate(it) }
