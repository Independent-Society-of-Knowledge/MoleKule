package org.isk.molekule.gen

import io.kotest.matchers.equals.shouldBeEqual
import org.isk.molekule.gen.geomertry.Box
import org.isk.molekule.gen.geomertry.Point
import org.junit.jupiter.api.Test

class BoxTest {
    @Test
    fun `boundary tests`() {
        val box = Box(0.0 to 1.0, 0.0 to 1.0, 0.0 to 1.0)

        val p1 = Point(0.5, 0.5, 0.5)
        val p2 = Point(1.5, 0.5, 0.5)
        val p3 = Point(1.0, 0.5, 0.5)

        box.isInside(p1) shouldBeEqual true
        box.isInside(p2) shouldBeEqual false
        box.isInside(p3) shouldBeEqual false

        box.isOutSide(p1) shouldBeEqual false
        box.isOutSide(p2) shouldBeEqual true
        box.isOutSide(p3) shouldBeEqual false

        box.isOnSurface(p1) shouldBeEqual false
        box.isOnSurface(p2) shouldBeEqual false
        box.isOnSurface(p3) shouldBeEqual true

    }
}