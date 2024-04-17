package org.isk.molekule.gen

import io.kotest.matchers.equals.shouldBeEqual
import org.isk.molekule.gen.geomertry.Box
import org.isk.molekule.gen.geomertry.point.Point
import org.junit.jupiter.api.Test

class BoxTest {
    @Test
    fun `boundary tests`() {
        val box = Box(0.0 to 1.0, 0.0 to 1.0, 0.0 to 1.0)

        val p1 = Point(0.5, 0.5, 0.5)
        val p2 = Point(1.5, 0.5, 0.5)
        val p3 = Point(1.0, 0.5, 0.5)

        box.contains(p1) shouldBeEqual true
        box.contains(p2) shouldBeEqual false
        box.contains(p3) shouldBeEqual false

        box.excludes(p1) shouldBeEqual false
        box.excludes(p2) shouldBeEqual true
        box.excludes(p3) shouldBeEqual false

        box.touches(p1) shouldBeEqual false
        box.touches(p2) shouldBeEqual false
        box.touches(p3) shouldBeEqual true

    }
}
