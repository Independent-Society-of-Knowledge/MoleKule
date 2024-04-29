package org.isk.molekule.core.atom

open class Molecule(
    val atoms: MutableList<Atom> = mutableListOf(),
    val bonds: MutableList<Bond> = mutableListOf(),
    val angles: MutableList<Angle> = mutableListOf(),
    val dihedral: MutableList<Dihedral> = mutableListOf(),
    override val type: Int
) : MultiAtomEntity(
    * (bonds + angles + dihedral)
        .flatMap { it.subAtoms.asSequence() }
        .plus(atoms).toTypedArray()
), EntityGenerator {
    override fun generate(): Array<Trackable> =
        (atoms + bonds + angles + dihedral)
            .flatMap { it.generate().asSequence() }
            .plus(this)
            .toTypedArray()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Molecule) return false
        if (!super.equals(other)) return false

        if (atoms != other.atoms) return false
        if (bonds != other.bonds) return false
        if (angles != other.angles) return false
        if (dihedral != other.dihedral) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + atoms.hashCode()
        result = 31 * result + bonds.hashCode()
        result = 31 * result + angles.hashCode()
        result = 31 * result + dihedral.hashCode()
        result = 31 * result + type
        return result
    }


}