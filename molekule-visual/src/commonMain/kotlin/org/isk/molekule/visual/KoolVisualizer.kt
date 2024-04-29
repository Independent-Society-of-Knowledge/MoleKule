package org.isk.molekule.visual

import de.fabmax.kool.KoolConfig
import de.fabmax.kool.KoolContext
import de.fabmax.kool.createContext
import de.fabmax.kool.defaultKoolConfig
import de.fabmax.kool.math.Vec3f
import de.fabmax.kool.scene.OrbitInputTransform
import de.fabmax.kool.scene.defaultOrbitCamera
import de.fabmax.kool.scene.orbitCamera
import de.fabmax.kool.scene.scene
import de.fabmax.kool.util.Color
import org.isk.molekule.core.Environment
import org.isk.molekule.visual.plugins.EnvironmentMainPlugin
import org.isk.molekule.visual.plugins.KoolVizualizerPlugin

class KoolVisualizer(cfg: KoolConfig = defaultKoolConfig()) {

    lateinit var orbitInputTransform: OrbitInputTransform
    val ctx: KoolContext = createContext(cfg)
    val scene = scene {
        orbitInputTransform = defaultOrbitCamera()

        lighting.addDirectionalLight {
            setup(Vec3f(-1f, -1f, -1f))
            setColor(Color.WHITE, 5f)
        }
    }
    val plugins = mutableListOf<KoolVizualizerPlugin>()


    fun withDefaultConfig(): KoolVisualizer {
        plugins.add(EnvironmentMainPlugin())
        return this
    }

    fun init(): KoolVisualizer {
        ctx.scenes += scene
        plugins.forEach { it.initialize(this) }
        return this
    }

    fun runAndWaitUntilExit() {
        ctx.run()
    }

    fun addEnvironment(environment: Environment): KoolVisualizer {
        plugins.forEach { it.addEnvironment(this, environment) }
        return this
    }

    fun addPlugin(plugin: KoolVizualizerPlugin): KoolVisualizer {
        plugins.add(plugin)
        return this
    }


}