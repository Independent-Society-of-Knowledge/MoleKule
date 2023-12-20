package org.isk.molekule.gen.lattice

import org.isk.molekule.gen.geomertry.Point
import org.isk.molekule.gen.geomertry.times


fun Sequence<Point>.usingUnitCell(unitCell: UnitCell): Sequence<Point> =
    map {
        with(it) {
            with(unitCell) {
                x * a1 + y * a2 + z * a3
            }
        }
    }.flatMap { unitCell.generateLatticePoints(it) }