package org.isk.molekule.core.utils

import org.isk.molekule.core.geomertry.Box
import org.isk.molekule.core.geomertry.isInside
import org.isk.molekule.core.geomertry.isOnSurface
import org.isk.molekule.core.geomertry.point.Point

infix fun Point.isInPeriodicRegion(box: Box): Boolean =
    this isInside box || (this isOnSurface box && this.x < box.xHigh && this.y < box.yHigh && this.z < box.zHigh)