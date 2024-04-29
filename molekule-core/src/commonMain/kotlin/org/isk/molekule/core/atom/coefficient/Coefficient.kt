package org.isk.molekule.core.atom.coefficient

import org.isk.molekule.core.atom.EntityGenerator
import org.isk.molekule.core.atom.Trackable

open class Coefficient(vararg val coefficients: Double, override val type: Int) : Trackable, EntityGenerator {
    override fun generate(): Array<Trackable> = arrayOf(this)

}