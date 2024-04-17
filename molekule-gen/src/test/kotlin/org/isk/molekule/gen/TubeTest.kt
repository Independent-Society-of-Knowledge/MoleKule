package org.isk.molekule.gen

import io.kotest.matchers.shouldBe
import org.isk.molekule.gen.geomertry.point.Point
import org.isk.molekule.gen.geomertry.Tube
import org.junit.jupiter.api.Test

class TubeTest {
    @Test
    fun `tube test`() {
        val tube = Tube(Point(0, 0, 0), Point(1, 0, 0), 1.0)
        tube.contains(Point(0.5, 0, 0)) shouldBe true
        tube.contains(Point(0.5, 0.9, 0)) shouldBe true
        tube.contains(Point(0.5, 1.1, 0)) shouldBe false
        tube.contains(Point(0.5, -1.1, 0)) shouldBe false
        tube.contains(Point(0.5, 0, 0.9)) shouldBe true
        tube.contains(Point(0.5, 0, 1.1)) shouldBe false
        tube.contains(Point(0.5, 0, -1.1)) shouldBe false
    }
}
