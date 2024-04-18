package org.isk.molekule.gen.atom

open class Bond(
    val first: Atom,
    val second: Atom,
    override val type: Int
) : MultiAtomEntity(first, second) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Bond) return false

        if (type != other.type) return false
        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + type
        return result
    }
}