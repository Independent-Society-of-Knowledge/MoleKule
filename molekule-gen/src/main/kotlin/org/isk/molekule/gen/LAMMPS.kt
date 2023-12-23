package org.isk.molekule.gen

import org.isk.molekule.gen.atom.ChargedAtom
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

private fun OutputStream.writeLine(string: String): OutputStream =
    this.also {
        it.write("$string\n".encodeToByteArray())
    }

operator fun OutputStream.plus(string: String) = writeLine(string)


enum class AtomStyle {
    ATOMIC,
    MOLECULE,
    FULL
}

fun Environment.toLammpsInputFile(
    filePath: String,
    atomStyle: AtomStyle = AtomStyle.ATOMIC,
    enclosingBoxOffset: Double = 0.0
) {

    val labeledAtoms = this.atoms.toList().mapIndexed { index, atom -> atom to index + 1 }.toMap()
    val labeledBonds = this.bond.toList().mapIndexed { index, atom -> atom to index + 1 }.toMap()
    val labeledAngles = this.angles.toList().mapIndexed { index, atom -> atom to index + 1 }.toMap()
    val labeledDihedral = this.dihedral.toList().mapIndexed { index, atom -> atom to index + 1 }.toMap()
    val labeledMolecule = this.molecules.toList().mapIndexed { index, atom -> atom to index + 1 }.toMap()

    FileOutputStream(File(filePath)).use { oups ->

        oups + "# generated with molekule-gen"
        oups + ""
        oups + "${this.atoms.size}\tatoms"
        oups + "${this.bond.size}\tbonds"
        oups + "${this.angles.size}\tangles"
        oups + "${this.dihedral.size}\tdihedrals"
        oups + ""
        oups + "${this.atoms.distinctBy { it.type }.size}\t\tatom types"
        oups + "${this.bond.distinctBy { it.type }.size}\t\tbond types"
        oups + "${this.angles.distinctBy { it.type }.size}\t\tangle types"
        oups + "${this.dihedral.distinctBy { it.type }.size}\t\tdihedral types"
        oups + ""

        val box = this.enclosingBox(enclosingBoxOffset)
        oups + "${box.xLow} ${box.xHigh} xlo xhi"
        oups + "${box.yLow} ${box.yHigh} ylo yhi"
        oups + "${box.zLow} ${box.zHigh} zlo zhi"
        oups + ""
        oups + "Masses"
        oups + ""
        this.atoms.distinctBy { it.type }
            .forEach {
                oups + "${it.type} ${it.mass}"
            }
        oups + ""
        oups + "Atoms"
        oups + ""

        when (atomStyle) {
            AtomStyle.ATOMIC -> {
                labeledAtoms
                    .entries.sortedBy { it.value }
                    .forEach { (atom, index) ->
                        oups + "$index ${atom.type} ${atom.position.x} ${atom.position.y} ${atom.position.z}"
                    }
            }

            AtomStyle.MOLECULE -> {
                labeledMolecule
                    .forEach { (molecule, moleculeIndex) ->
                        molecule.atoms.map { it to labeledAtoms[it] }
                            .sortedBy { it.second }
                            .forEach { (atom, atomIndex) ->
                                oups + "$atomIndex $moleculeIndex ${atom.type} ${atom.position.x} ${atom.position.y} ${atom.position.z}"
                            }
                    }
            }

            AtomStyle.FULL -> {
                labeledMolecule
                    .forEach { (molecule, moleculeIndex) ->
                        molecule.atoms
                            .map { if (it is ChargedAtom) it else ChargedAtom(it.position, it.mass, 0.0, it.type) }
                            .map { it to labeledAtoms[it] }
                            .sortedBy { it.second }
                            .forEach { (chargedAtom, atomIndex) ->
                                oups + "$atomIndex $moleculeIndex ${chargedAtom.type} ${chargedAtom.charge} ${chargedAtom.position.x} ${chargedAtom.position.y} ${chargedAtom.position.z}"
                            }
                    }
            }
        }

        oups + ""
        oups + "Bonds"
        oups + ""
        labeledBonds.entries.sortedBy { it.value }.forEach { (bond, index) ->
            oups + "$index ${bond.type} ${labeledAtoms[bond.first]!!} ${labeledAtoms[bond.second]!!}"
        }

        oups + ""
        oups + "Angles"
        oups + ""
        labeledAngles.entries.sortedBy { it.value }.forEach { (angle, index) ->
            oups + "$index ${angle.type} ${labeledAtoms[angle.first]!!} ${labeledAtoms[angle.second]!!} ${labeledAtoms[angle.third]!!}"
        }


        oups + ""
        oups + "Dihedrals"
        oups + ""
        labeledDihedral.entries.sortedBy { it.value }.forEach { (dihedral, index) ->
            oups + "$index ${dihedral.type} ${dihedral.subAtoms.map { labeledAtoms[it]!! }.joinToString(" ")}"
        }

    }
}


fun Environment.toLammpsDumpFile(
    filePath: String,
    timeStamps: Long = 0,
    bounds: String = "pp pp pp",
    enclosingBoxOffset: Double = 0.0
) {
    val labeledAtoms = this.atoms.mapIndexed { index, atom -> atom to index + 1 }.toMap()
    val box = enclosingBox(enclosingBoxOffset)
    FileOutputStream(File(filePath)).use { oups ->

        oups + """
        ITEM: TIMESTEP
        $timeStamps
        ITEM: NUMBER OF ATOMS
        ${labeledAtoms.size}
        ITEM: BOX BOUNDS $bounds
        ${box.xLow} ${box.xHigh}
        ${box.yLow} ${box.yHigh}
        ${box.zLow} ${box.zHigh}
        ITEM: ATOMS id type xs ys zs""".trimIndent()
        labeledAtoms.forEach { (atom, index) ->
            oups + "$index ${atom.type} ${atom.position.x} ${atom.position.y} ${atom.position.z}"
        }
    }
}