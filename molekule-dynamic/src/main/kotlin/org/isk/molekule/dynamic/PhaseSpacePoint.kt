package org.isk.molekule.dynamic

import org.isk.molekule.gen.geomertry.Point

class PhaseSpacePoint(
    var position: Point,
    var velocity: Point
) {
    operator fun plus(other: PhaseSpacePoint): PhaseSpacePoint =
        PhaseSpacePoint(position + other.position, velocity + other.velocity)

    operator fun times(other: Number): PhaseSpacePoint =
        PhaseSpacePoint(position * other, velocity * other)

    override fun toString(): String {
        return "PhaseSpacePoint(position=$position, velocity=$velocity)"
    }

}


operator fun Number.times(point: PhaseSpacePoint): PhaseSpacePoint =
    point.times(this)