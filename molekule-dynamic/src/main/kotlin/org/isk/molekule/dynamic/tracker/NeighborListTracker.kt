package org.isk.molekule.dynamic.tracker

import org.isk.molekule.dynamic.PhaseSpacePoint
import org.isk.molekule.dynamic.Simulation
import org.isk.molekule.dynamic.watcher.Watcher
import org.isk.molekule.core.atom.Atom
import org.isk.molekule.core.atom.Trackable
import org.isk.molekule.core.geomertry.point.Point

class NeighborListTracker(
    val systemLength: Double,
    val simulation: Simulation
) : Tracker {

    private val boxes: MutableMap<Triple<Int, Int, Int>, MutableSet<PhaseAtom>> = mutableMapOf()
    private val reversePhaseAtomBox: MutableMap<PhaseAtom, Triple<Int, Int, Int>> = mutableMapOf()


    private fun rebuild() {
        boxes.clear()
        reversePhaseAtomBox.clear()

        for (i in 0..maxI)
            for (j in 0..maxJ)
                for (k in 0..maxK)
                    boxes[Triple(i, j, k)] = mutableSetOf()

        atoms.forEach {
            val id = finBoxOf(it.phaseSpacePoint.position)
            boxes[id]!!.add(it)
            reversePhaseAtomBox[it] = id
        }
    }


    private val maxI = -((simulation.enclosingBox.xLow - simulation.enclosingBox.xHigh) / systemLength).toInt()
    private val maxJ = -((simulation.enclosingBox.yLow - simulation.enclosingBox.yHigh) / systemLength).toInt()
    private val maxK = -((simulation.enclosingBox.zLow - simulation.enclosingBox.zHigh) / systemLength).toInt()

    init {
        for (i in 0..maxI)
            for (j in 0..maxJ)
                for (k in 0..maxK)
                    boxes[Triple(i, j, k)] = mutableSetOf()

        simulation.addWatcher(object : Watcher {
            override fun trigger(simulation: Simulation) {
                val isOld = reversePhaseAtomBox
                    .asSequence()
                    .shuffled()
                    .take((reversePhaseAtomBox.size * 0.05).toInt())
                    .any {
                        finBoxOf(it.key.phaseSpacePoint.position) != it.value
                    }
                if (isOld)
                    rebuild()
            }
        })
    }

    private val itemsList: MutableMap<PhaseAtom, MutableSet<Trackable>> = mutableMapOf()
    private val reverseItemsList: MutableMap<Trackable, PhaseAtom> = mutableMapOf()

    override val trackables: Collection<Trackable>
        get() = reverseItemsList.keys
    override val atoms: Collection<PhaseAtom>
        get() = itemsList.keys

    override fun phaseAtomByTrackable(trackable: Trackable): PhaseAtom = reverseItemsList[trackable]
        ?: throw IllegalArgumentException("tracker doesn't track given trackable: $trackable")

    private fun findBoxesRelatedTo(point: Point): Sequence<Trackable> =
        with(simulation.enclosingBox) {
            val pi = ((xHigh - xLow - point.x) / systemLength).toInt()
            val pj = ((yHigh - yLow - point.y) / systemLength).toInt()
            val pk = ((zHigh - zLow - point.z) / systemLength).toInt()

            sequence {
                for (i in pi - 1..pi + 1)
                    for (j in pj - 1..pj + 1)
                        for (k in pk - 1..pk + 1)
                            yieldAll(
                                boxes[
                                    Triple(

                                        if (i < 0) maxI else if (i >= maxI) 0 else i,
                                        if (j < 0) maxJ else if (j >= maxJ) 0 else j,
                                        if (k < 0) maxK else if (k >= maxK) 0 else k
                                    )
                                ]!!.map { it.atom }

                            )
            }
        }

    override fun trackAll(trackables: Collection<Trackable>) {
        for (trackable in trackables) {
            val phaseAtom = if (trackable is Atom) {
                NaivePhaseAtom(
                    trackable,
                    PhaseSpacePoint(trackable.position, Point.origin),
                    this
                ).apply {
                    relationFinder = {
                        itemsList[this]!!.asSequence() + findBoxesRelatedTo(phaseSpacePoint.position).filter { it != trackable }
                    }
                    boxes[finBoxOf(trackable.position)]!!.add(this)
                }

            } else {
                reverseItemsList[trackable] ?: throw IllegalStateException("first add all atoms!")
            }
            val list = itemsList.computeIfAbsent(phaseAtom) { mutableSetOf() }
            if (trackable is Atom && trackable !== phaseAtom.atom)
                list.add(trackable)
            reverseItemsList[trackable] = phaseAtom
        }
        rebuild()
    }

    private fun finBoxOf(point: Point): Triple<Int, Int, Int> =
        with(simulation.enclosingBox) {
            val pi = ((xHigh - xLow - point.x) / systemLength).toInt()
            val pj = ((yHigh - yLow - point.y) / systemLength).toInt()
            val pk = ((zHigh - zLow - point.z) / systemLength).toInt()
            Triple(pi, pj, pk)
        }
}
