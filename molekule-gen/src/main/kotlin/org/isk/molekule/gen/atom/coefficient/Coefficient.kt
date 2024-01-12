package org.isk.molekule.gen.atom.coefficient

import org.isk.molekule.gen.atom.EntityGenerator
import org.isk.molekule.gen.atom.Trackable

open class Coefficient(vararg val coefficients: Double, override val type: Int) : Trackable, EntityGenerator {
    override fun generate(): Array<Trackable> = arrayOf(this)

}