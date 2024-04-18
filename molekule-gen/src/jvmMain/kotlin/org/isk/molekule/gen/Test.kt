package org.isk.molekule.gen

import org.isk.molekule.gen.compounds.atomOf
import org.isk.molekule.gen.geomertry.Box
import org.isk.molekule.gen.geomertry.Tube
import org.isk.molekule.gen.geomertry.isInside
import org.isk.molekule.gen.geomertry.point.Point
import org.isk.molekule.gen.lattice.Grid3D
import org.isk.molekule.gen.lattice.crystal.Crystal
import org.isk.molekule.gen.lattice.crystal.usingCrystal
import org.isk.molekule.gen.lattice.spanInAllDirections


fun main() {

    val env = Environment()

    val boxLen = -100 to 100
    val shape = Box(boxLen, boxLen, boxLen) - Tube(Point(-101,0,0), Point(101,0,0), 30)

    Grid3D(30, 30, 30)
        .points
        .spanInAllDirections()
        .usingCrystal(Crystal.famous.diamond(40.0))
        .filter { it isInside shape }
        .map {
            atomOf(AtomicMass.C, it)
        }
        .forEach {
            env.add(it)
        }

    env.createVMD()
        .dynamicBonds(cutOff = 40.0, size = 1.0)
        .run()
        .waitUntilExit()
}
