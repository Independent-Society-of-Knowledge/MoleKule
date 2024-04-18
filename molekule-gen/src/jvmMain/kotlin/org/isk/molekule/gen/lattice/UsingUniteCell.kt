package org.isk.molekule.gen.lattice

import org.isk.molekule.gen.geomertry.point.Point
import org.isk.molekule.gen.geomertry.point.times
import org.isk.molekule.gen.lattice.unit.UnitCell


fun Sequence<Point>.usingUnitCell(unitCell: UnitCell): Sequence<Point> =
    map {
        with(it) {
            with(unitCell) {
                (x * a1) + (y * a2) + (z * a3)
            }
        }
    }
