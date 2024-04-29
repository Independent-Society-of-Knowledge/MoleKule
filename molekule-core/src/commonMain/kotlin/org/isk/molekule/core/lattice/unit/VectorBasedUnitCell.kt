package org.isk.molekule.core.lattice.unit

import org.isk.molekule.core.geomertry.point.Point

class VectorBasedUnitCell(
    override val a1: Point,
    override val a2: Point,
    override val a3: Point
) : UnitCell
