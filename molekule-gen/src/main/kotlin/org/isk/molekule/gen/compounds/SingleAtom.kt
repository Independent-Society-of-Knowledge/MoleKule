package org.isk.molekule.gen.compounds

import org.isk.molekule.gen.AtomicMass
import org.isk.molekule.gen.atom.Atom
import org.isk.molekule.gen.geomertry.Point

fun atomOf(atomicMass: AtomicMass, position: Point, type: Int = atomicMass.atomType): Atom =
    Atom(position, atomicMass.mass, type)