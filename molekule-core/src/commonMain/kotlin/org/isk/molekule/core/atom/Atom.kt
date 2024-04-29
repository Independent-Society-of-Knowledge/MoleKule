package org.isk.molekule.core.atom

import org.isk.molekule.core.geomertry.point.Point

open class Atom(val position: Point, val mass: Double, override val type: Int) : Trackable, EntityGenerator {
    override fun generate(): Array<Trackable> = arrayOf(this)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Atom) return false

        if (position != other.position) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = position.hashCode()
        result = 31 * result + type
        return result
    }

    override fun toString(): String {
        return "Atom(position=$position, mass=$mass, type=$type)"
    }

}
