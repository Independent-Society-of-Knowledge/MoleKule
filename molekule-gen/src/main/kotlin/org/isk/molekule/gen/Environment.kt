package org.isk.molekule.gen

import org.isk.molekule.gen.atom.*
import org.isk.molekule.gen.atom.coefficient.AngleCoefficient
import org.isk.molekule.gen.atom.coefficient.BondCoefficient
import org.isk.molekule.gen.atom.coefficient.Coefficient
import org.isk.molekule.gen.atom.coefficient.DihedralCoefficient
import org.isk.molekule.gen.geomertry.Box
import java.util.Collections
import java.util.LinkedHashSet
import java.util.concurrent.ConcurrentSkipListSet

class Environment {


    val entities: MutableSet<Trackable> = mutableSetOf()

    val atoms: List<Atom>
        get() = entities.filterIsInstance<Atom>()

    val angles: List<Angle>
        get() = entities.filterIsInstance<Angle>()

    val bond: List<Bond>
        get() = entities.filterIsInstance<Bond>()

    val dihedral: List<Dihedral>
        get() = entities.filterIsInstance<Dihedral>()

    val molecules: List<Molecule>
        get() = entities.filterIsInstance<Molecule>()

    val coefficients: List<Coefficient>
        get() = entities.filterIsInstance<Coefficient>()


    val bondCoefficients: List<BondCoefficient>
        get() = entities.filterIsInstance<BondCoefficient>()
    val angleCoefficients: List<AngleCoefficient>
        get() = entities.filterIsInstance<AngleCoefficient>()
    val dihedralCoefficients: List<DihedralCoefficient>
        get() = entities.filterIsInstance<DihedralCoefficient>()

    var boundingBox: Box? = null
    val enclosingBox: Box
        get() {
            return boundingBox ?: run {

                val atoms = this.atoms
                if (atoms.isEmpty())
                    throw IllegalStateException("no atoms are inserted in the environment - can't enclose a box on an empty environment")


                var xLow: Double = atoms.first().position.x
                var xHigh: Double = xLow

                var yLow: Double = atoms.first().position.y
                var yHigh: Double = yLow

                var zLow: Double = atoms.first().position.z
                var zHigh: Double = zLow

                atoms.forEach {
                    with(it.position) {
                        if (x > xHigh)
                            xHigh = x
                        if (x < xLow)
                            xLow = x

                        if (y > yHigh)
                            yHigh = y
                        if (y < yLow)
                            yLow = y

                        if (z > zHigh)
                            zHigh = z
                        if (z < zLow)
                            zLow = z
                    }
                }

                return Box(xLow, xHigh, yLow, yHigh, zLow, zHigh)
            }
        }

    fun add(vararg entityGenerator: EntityGenerator) {
        entities.addAll(entityGenerator.flatMap { it.generate().asSequence() })
    }

    fun add( entityGenerator: List<EntityGenerator>) {
        entities.addAll(entityGenerator.flatMap { it.generate().asSequence() })
    }

    fun enclosingBox(offset: Double): Box = with(enclosingBox) {
        Box(
            xLow - offset, xHigh + offset,
            yLow - offset, yHigh + offset,
            zLow - offset, zHigh + offset
        )
    }

}
