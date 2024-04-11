package org.isk.molekule.gen.lammps

import org.isk.molekule.gen.Environment
import org.isk.molekule.gen.atom.ChargedAtom
import org.isk.molekule.gen.plus
import java.io.File
import java.io.FileOutputStream

enum class AtomStyle {
    ATOMIC,
    MOLECULE,
    FULL
}

fun Environment.toLammpsInputFile(
    filePath: String,
    atomStyle: AtomStyle = AtomStyle.ATOMIC,
    enclosingBoxOffset: Double = 0.0,
    rescale: Double = 1.0
) {

    val labeledAtoms = this.atoms.toList().mapIndexed { index, atom -> atom to index + 1 }.toMap()
    val labeledBonds = this.bond.toList().mapIndexed { index, atom -> atom to index + 1 }.toMap()
    val labeledAngles = this.angles.toList().mapIndexed { index, atom -> atom to index + 1 }.toMap()
    val labeledDihedral = this.dihedral.toList().mapIndexed { index, atom -> atom to index + 1 }.toMap()
    val labeledMolecule = this.molecules.toList().mapIndexed { index, atom -> atom to index + 1 }.toMap()
    val moleculeLessAtoms = labeledAtoms.filterNot { indexedAtom ->
        labeledMolecule.keys.any { molecule ->
            molecule.atoms.contains(indexedAtom.key)
        }
    }.toMap()

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

        if (this.bondCoefficients.isNotEmpty()) {
            oups + ""
            oups + "Bond Coeffs"
            oups + ""
            this.bondCoefficients
                .distinctBy { it.type }
                .forEach {
                    oups + "${it.type} ${it.coefficients.joinToString("\t")}"
                }
        }

        if (this.angleCoefficients.isNotEmpty()) {
            oups + ""
            oups + "Angle Coeffs"
            oups + ""
            this.angleCoefficients
                .distinctBy { it.type }
                .forEach {
                    oups + "${it.type} ${it.coefficients.joinToString("\t")}"
                }
        }


        if (this.dihedralCoefficients.isNotEmpty()) {
            oups + ""
            oups + "Dihedral Coeffs"
            oups + ""
            this.dihedralCoefficients
                .distinctBy { it.type }
                .forEach {
                    oups + "${it.type} ${it.coefficients.joinToString("\t")}"
                }
        }


        oups + ""
        oups + "Atoms"
        oups + ""

        when (atomStyle) {
            AtomStyle.ATOMIC -> {
                labeledAtoms
                    .entries.sortedBy { it.value }
                    .forEach { (atom, index) ->
                        oups + "$index ${atom.type} ${atom.position.x * rescale} ${atom.position.y * rescale} ${atom.position.z * rescale}"
                    }
            }

            AtomStyle.MOLECULE -> {
                moleculeLessAtoms.forEach { (atom, index) ->
                    oups + "$index 0 ${atom.type} ${atom.position.x * rescale} ${atom.position.y * rescale} ${atom.position.z * rescale}"
                }
                labeledMolecule
                    .forEach { (molecule, moleculeIndex) ->
                        molecule.atoms.map { it to labeledAtoms[it] }
                            .sortedBy { it.second }
                            .forEach { (atom, atomIndex) ->
                                oups + "$atomIndex $moleculeIndex ${atom.type} ${atom.position.x * rescale} ${atom.position.y * rescale} ${atom.position.z * rescale}"
                            }
                    }
            }

            AtomStyle.FULL -> {
                moleculeLessAtoms.forEach { (atom, index) ->
                    oups + "$index 0 ${atom.type} ${if (atom is ChargedAtom) atom.charge else 0.0} ${atom.position.x * rescale} ${atom.position.y * rescale} ${atom.position.z * rescale}"
                }
                labeledMolecule
                    .forEach { (molecule, moleculeIndex) ->
                        molecule.atoms
                            .map { it to labeledAtoms[it] }
                            .sortedBy { it.second }
                            .forEach { (chargedAtom, atomIndex) ->
                                oups + "$atomIndex $moleculeIndex ${chargedAtom.type} ${if (chargedAtom is ChargedAtom) chargedAtom.charge else 0.0} ${chargedAtom.position.x * rescale} ${chargedAtom.position.y * rescale} ${chargedAtom.position.z * rescale}"
                            }
                    }
            }
        }

        if (labeledBonds.isNotEmpty()) {
            oups + ""
            oups + "Bonds"
            oups + ""
            labeledBonds.entries.sortedBy { it.value }.forEach { (bond, index) ->
                oups + "$index ${bond.type} ${labeledAtoms[bond.first]!!} ${labeledAtoms[bond.second]!!}"
            }
        }

        if (labeledAngles.isNotEmpty()) {
            oups + ""
            oups + "Angles"
            oups + ""
            labeledAngles.entries.sortedBy { it.value }.forEach { (angle, index) ->
                oups + "$index ${angle.type} ${labeledAtoms[angle.first]!!} ${labeledAtoms[angle.second]!!} ${labeledAtoms[angle.third]!!}"
            }

        }

        if (labeledDihedral.isNotEmpty()) {
            oups + ""
            oups + "Dihedrals"
            oups + ""
            labeledDihedral.entries.sortedBy { it.value }.forEach { (dihedral, index) ->
                oups + "$index ${dihedral.type} ${dihedral.subAtoms.map { labeledAtoms[it]!! }.joinToString(" ")}"
            }
        }

    }
}


fun Environment.toLammpsDumpFile(
    filePath: String,
    timeStamps: Long = 0,
    bounds: String = "pp pp pp",
    enclosingBoxOffset: Double = 0.0,
    rescale: Double = 1.0
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
        ${box.xLow * rescale} ${box.xHigh * rescale}
        ${box.yLow * rescale} ${box.yHigh * rescale}
        ${box.zLow * rescale} ${box.zHigh * rescale}
        ITEM: ATOMS id type xs ys zs""".trimIndent()
        labeledAtoms.forEach { (atom, index) ->
            oups + "$index ${atom.type} ${atom.position.x * rescale} ${atom.position.y * rescale} ${atom.position.z * rescale}"
        }
    }
}
