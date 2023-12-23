package org.isk.molekule.dynamic.tracker

import org.isk.molekule.dynamic.PhaseSpacePoint
import org.isk.molekule.gen.atom.*
import org.isk.molekule.gen.atom.Trackable
import org.isk.molekule.gen.geomertry.Point

abstract class PhaseAtom(
    val atom: Atom,
    var phaseSpacePoint: PhaseSpacePoint,
    val tracker: Tracker,
    val attributes: MutableMap<Any, Any> = mutableMapOf()
) {
    abstract val relations: Sequence<Trackable>
    val angles: Sequence<Angle>
        get() = relations.filterIsInstance(Angle::class.java)
    val bonds: Sequence<Bond>
        get() = relations.filterIsInstance(Bond::class.java)

    val dihedral: Sequence<Dihedral>
        get() = relations.filterIsInstance(Dihedral::class.java)

    val molecules: Sequence<Molecule>
        get() = relations.filterIsInstance(Molecule::class.java)

    val neighboringAtoms: Sequence<Atom>
        get() = relations.filterIsInstance(Atom::class.java)


    val neighboringPhaseAtoms: Sequence<PhaseAtom>
        get() = neighboringAtoms.map { tracker.phaseAtomByTrackable(it) }
}