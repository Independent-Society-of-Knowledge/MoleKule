package org.isk.molekule.dynamic.tracker

import org.isk.molekule.dynamic.PhaseSpacePoint
import org.isk.molekule.gen.atom.Atom
import org.isk.molekule.gen.atom.Trackable
import org.isk.molekule.gen.geomertry.Point


class NaivePhaseAtom(
    atom: Atom,
    phaseSpacePoint: PhaseSpacePoint,
    tracker: Tracker,
) : PhaseAtom(atom, phaseSpacePoint, tracker) {
    lateinit var relationFinder: () -> Sequence<Trackable>
    override val relations: Sequence<Trackable>
        get() = relationFinder()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NaivePhaseAtom) return false

        return atom === other.atom
    }


    override fun hashCode(): Int = atom.type

}

class NaiveListTracker : Tracker {
    private val itemsList: MutableMap<PhaseAtom, MutableSet<Trackable>> = mutableMapOf()
    private val reverseItemsList: MutableMap<Trackable, PhaseAtom> = mutableMapOf()

    override val trackables: Collection<Trackable>
        get() = reverseItemsList.keys
    override val atoms: Collection<PhaseAtom>
        get() = itemsList.keys

    override fun phaseAtomByTrackable(trackable: Trackable): PhaseAtom = reverseItemsList[trackable]
        ?: throw IllegalArgumentException("tracker doesn't track given trackable: $trackable")

    override fun trackAll(trackables: Collection<Trackable>) {
        for (trackable in trackables) {
            val phaseAtom = if (trackable is Atom) {
                NaivePhaseAtom(
                    trackable,
                    PhaseSpacePoint(trackable.position, Point.origin),
                    this
                ).apply {
                    relationFinder = {
                        itemsList[this]!!.asSequence() + reverseItemsList.keys.asSequence().filter { it != trackable }
                    }
                }
            } else {
                reverseItemsList[trackable] ?: throw IllegalStateException("first add all atoms!")
            }
            val list = itemsList.computeIfAbsent(phaseAtom) { mutableSetOf() }
            if (trackable is Atom && trackable !== phaseAtom.atom)
                list.add(trackable)
            reverseItemsList[trackable] = phaseAtom
        }

    }
}