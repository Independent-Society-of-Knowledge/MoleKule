package org.isk.molekule.gen

import io.kotest.matchers.shouldBe
import org.isk.molekule.gen.geomertry.point.Point
import org.isk.molekule.gen.geomertry.Sphere
import org.isk.molekule.gen.geomertry.isInside
import org.isk.molekule.gen.geomertry.isOutside
import org.junit.jupiter.api.Test
import kotlin.random.Random


class SphereTest {

    @Test
    fun `sphere test`() {
        val sphere = Sphere(Point.origin, 1.0)

        (0..100)
            .map { Point.randomOrientation * Random.nextDouble(0.999) }
            .forEach {
                it isInside sphere shouldBe true
            }


        (0..100)
            .map { Point.randomOrientation * (1.0 + Random.nextDouble(0.999)) }
            .forEach {
                it isOutside  sphere shouldBe true
            }

    }

}
