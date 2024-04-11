package org.isk.molekule.gen

import org.isk.molekule.gen.compounds.atomOf
import org.isk.molekule.gen.geomertry.Box
import org.isk.molekule.gen.geomertry.Point
import org.isk.molekule.gen.geomertry.Sphere
import org.isk.molekule.gen.geomertry.isInside
import org.isk.molekule.gen.lattice.Grid3D
import org.isk.molekule.gen.lattice.spanInAllDirections


fun main() {
    val env = Environment()

    val shape = Sphere(Point.origin, 20)

    Grid3D(100, 100, 100)
        .points
        .spanInAllDirections()
        .map { it * 3 }
        .filter { it isInside shape }
        .map {
            atomOf(AtomicMass.C, it)
        }
        .forEach {
            env.add(it)
        }

    env.createVMD()
        .vdm(1.0)
        .run()
        .waitUntilExit()
}