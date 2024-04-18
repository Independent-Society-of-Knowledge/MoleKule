package org.isk.molekule.gen.lattice.crystal.site

import org.isk.molekule.gen.geomertry.point.Point
import org.isk.molekule.gen.lattice.crystal.Crystal
import org.isk.molekule.gen.lattice.unit.UnitCell

abstract class SiteBasedCrystal(unitCell: UnitCell) : Crystal<Point>(unitCell)
