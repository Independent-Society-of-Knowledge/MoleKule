package org.isk.molekule.core.atom.coefficient

class DihedralCoefficient(vararg coefficients: Double, type: Int) : Coefficient(*coefficients, type = type)