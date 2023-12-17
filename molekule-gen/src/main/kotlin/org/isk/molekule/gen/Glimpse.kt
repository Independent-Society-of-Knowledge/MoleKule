package org.isk.molekule.gen

import org.isk.molekule.gen.atom.*
import org.isk.molekule.gen.geomertry.Point
import org.isk.molekule.gen.utils.toRad


// represent your configuration using classes as base schema
class Hydrogen(position: Point, charge: Double) : ChargedAtom(position, AtomicMass.H.mass, charge, 1)
class Oxygen(position: Point, charge: Double) : ChargedAtom(position, AtomicMass.O.mass, charge, 2)

class OxygenHydrogenBond(first: Oxygen, second: Hydrogen) : Bond(first, second, 1)
class HydrogenOxygenHydrogen(h1: Hydrogen, o: Oxygen, h2: Hydrogen) : Angle(h1, o, h2, 1)


// complex molecules can further confine complexity using encapsulation
// this class represents a water molecule at given point
class WaterMolecule(center: Point) : Molecule(type = 1) {
    init {

        // hydrogen atoms in water molecule are 0.1 nm apart from oxygen
        // with 106 degree in between them
        val length1 = Point(0.1, 0.0, 0.0)
        val length2 = Point(0.1, 0.0, 0.0).rotateZ(106.toRad())

        val h1 = Hydrogen(center + length1, 0.1)
        val h2 = Hydrogen(center + length2, 0.1)
        val o = Oxygen(center, -0.2)

        val bond1 = OxygenHydrogenBond(o, h1)
        val bond2 = OxygenHydrogenBond(o, h2)

        val angle = HydrogenOxygenHydrogen(h1, o, h2)

        // add everything to the bag of molecule
        this.atoms.addAll(arrayOf(h1, o, h2))
        this.bonds.addAll(arrayOf(bond1, bond2))

        this.angles.add(angle)

    }
}

fun main() {

    val env = Environment()

    // cartesian lattice of 10x10x10 cube
    for (i in (0..<10))
        for (j in (0..<10))
            for (k in (0..<10))
                env.add(WaterMolecule(Point(i, j, k)))

    // dump everything to an input file
    // atom full style is for electric charge consideration
    env.toLammpsInputFile("cube-water.data", AtomStyle.FULL)
}