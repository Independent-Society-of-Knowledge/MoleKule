package org.isk.molekule.core.lattice

import org.isk.molekule.core.geomertry.point.Point
import org.isk.molekule.core.geomertry.point.times
import org.isk.molekule.core.lattice.unit.UnitCell


fun Sequence<Point>.usingUnitCell(unitCell: UnitCell): Sequence<Point> =
    map {
        with(it) {
            with(unitCell) {
                (x * a1) + (y * a2) + (z * a3)
            }
        }
    }
