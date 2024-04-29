package org.isk.molekule.core.atom

abstract class MultiAtomEntity(
    vararg val subAtoms: Atom
) : Trackable, EntityGenerator {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MultiAtomEntity) return false

        if (!subAtoms.contentEquals(other.subAtoms)) return false

        return true
    }

    override fun hashCode(): Int {
        return subAtoms.contentHashCode()
    }

    override fun generate(): Array<Trackable> = arrayOf(this, *subAtoms)
    override fun toString(): String {
        return "MultiAtomEntity(subAtoms=${subAtoms.contentToString()})"
    }

}