package org.isk.molekule.gen.atom

open class Dihedral(vararg atoms: Atom, override val type: Int) : MultiAtomEntity(*atoms) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Dihedral) return false
        if (type != other.type) return false
        if (!super.equals(other)) return false

        return true
    }


    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + type
        return result
    }
}
