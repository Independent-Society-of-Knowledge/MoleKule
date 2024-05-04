package org.isk.molekule.visual.plugins

import de.fabmax.kool.math.Vec3f
import de.fabmax.kool.math.deg
import de.fabmax.kool.scene.addColorMesh
import de.fabmax.kool.util.Color
import org.isk.molekule.core.Environment
import org.isk.molekule.core.geomertry.point.length
import org.isk.molekule.visual.KoolVisualizer

class AxisPlugin(
    private val radius: Float = 0.5f,
    val relativeSizeMultiplier: Float = 1.0f,
    val sizeOverride: Float? = null
) : KoolVizualizerPlugin {
    override fun initialize(koolVisualizer: KoolVisualizer) {

    }

    override fun addEnvironment(koolVisualizer: KoolVisualizer, environment: Environment) {
        val height_ = sizeOverride ?: run {
            with(environment.enclosingBox) { listOf(xBoundary.length, yBoundary.length, zBoundary.length) }
                .min().toFloat() * relativeSizeMultiplier / 2.0f
        }
        koolVisualizer.scene.addColorMesh {
            generate {
                cylinder {
                    height = height_
                    topRadius = this@AxisPlugin.radius
                    bottomRadius = this@AxisPlugin.radius
                    topFill = true
                    bottomFill = true
                    color = Color.GREEN
                }
                shader = defaultShader
            }
            transform.translate(0f, height_ / 2.0f, 0f)
        }
        koolVisualizer.scene.addColorMesh {
            generate {
                cylinder {
                    height = height_
                    topRadius = this@AxisPlugin.radius
                    bottomRadius = this@AxisPlugin.radius
                    topFill = true
                    bottomFill = true
                    color = Color.BLUE
                }
                shader = defaultShader
            }
            transform.rotate(90f.deg, Vec3f.X_AXIS)
            transform.translate(0f, 0f, height_ / 2.0f)
        }
        koolVisualizer.scene.addColorMesh {
            generate {
                cylinder {
                    height = height_
                    topRadius = this@AxisPlugin.radius
                    bottomRadius = this@AxisPlugin.radius
                    topFill = true
                    bottomFill = true
                    color = Color.RED
                }
                shader = defaultShader
            }
            transform.rotate(90f.deg, Vec3f.Z_AXIS).translate(height_ / 2.0f, 0f, 0f)
        }
    }
}