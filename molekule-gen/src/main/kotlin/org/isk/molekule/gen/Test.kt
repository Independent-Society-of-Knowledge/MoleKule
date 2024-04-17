package org.isk.molekule.gen

import org.isk.molekule.gen.compounds.atomOf
import org.isk.molekule.gen.geomertry.Box
import org.isk.molekule.gen.geomertry.point.Point
import org.isk.molekule.gen.geomertry.Sphere
import org.isk.molekule.gen.geomertry.isInside
import org.isk.molekule.gen.lattice.Grid3D
import org.isk.molekule.gen.lattice.spanInAllDirections
import org.isk.molekule.gen.lattice.unit.bravis.Bravais
import org.isk.molekule.gen.lattice.usingUnitCell
import kotlin.math.PI


fun main() {
    val env = Environment()

    val shape = Box(-20 to 20, -20 to 20, -20 to 30)


    Grid3D(100, 100, 100)
        .points
        .spanInAllDirections()
        .usingUnitCell(Bravais.dim3.cubic(3.0))
        .filter { it isInside shape }
        .map {
            atomOf(AtomicMass.C, it)
        }
        .forEach {
            env.add(it)
        }

    env.createVMD()
        .vdm(0.5)
        .run()
        .waitUntilExit()
}
