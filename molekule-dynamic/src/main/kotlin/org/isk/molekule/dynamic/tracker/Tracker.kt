package org.isk.molekule.dynamic.tracker

import org.isk.molekule.core.atom.*



interface Tracker {
    val trackables: Collection<Trackable>

    val atoms: Collection<PhaseAtom>

    fun phaseAtomByTrackable(trackable: Trackable): PhaseAtom

    fun trackAll(trackables: Collection<Trackable>)
    fun track(trackable: Trackable) = trackAll(listOf(trackable))
}