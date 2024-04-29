package org.isk.molekule.visual.plugins

import org.isk.molekule.core.Environment
import org.isk.molekule.visual.KoolVisualizer

interface KoolVizualizerPlugin {
    fun initialize(koolVisualizer: KoolVisualizer)
    fun addEnvironment(koolVisualizer: KoolVisualizer, environment: Environment)
}