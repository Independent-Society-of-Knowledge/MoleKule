package org.isk.molekule.core.lattice.crystal.site

import org.isk.molekule.core.geomertry.point.Point
import org.isk.molekule.core.lattice.crystal.Crystal
import org.isk.molekule.core.lattice.unit.UnitCell

abstract class SiteBasedCrystal(unitCell: UnitCell) : Crystal<Point>(unitCell)
