package org.isk.molekule.gen.atom.coefficient

class BondCoefficient(vararg coefficients: Double, type: Int) : Coefficient(*coefficients, type = type)