package org.isk.molekule.core.compounds

import org.isk.molekule.core.AtomicMass
import org.isk.molekule.core.atom.Atom
import org.isk.molekule.core.geomertry.point.Point

fun atomOf(atomicMass: AtomicMass, position: Point, type: Int = atomicMass.atomType): Atom =
    Atom(position, atomicMass.mass, type)
