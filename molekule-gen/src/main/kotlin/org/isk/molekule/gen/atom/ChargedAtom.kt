package org.isk.molekule.gen.atom

import org.isk.molekule.gen.geomertry.Point

open class ChargedAtom(position: Point, mass: Double, val charge: Double, type: Int) : Atom(position, mass, type) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ChargedAtom) return false

        if (type != other.type) return false
        if (charge != other.charge) return false
        if (position != other.position) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + charge.hashCode()
        return result
    }

    override fun toString(): String {
        return "ChargedAtom(charge=$charge, atom=${super.toString()})"
    }


}