package org.isk.molekule.gen.atom

open class Angle(
    val first: Atom,
    val second: Atom,
    val third: Atom,
    override val type: Int
) : MultiAtomEntity(first, second, third) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Angle) return false

        if (type != other.type) return false
        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + type
        return result
    }


}