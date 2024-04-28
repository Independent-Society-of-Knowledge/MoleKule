package org.isk.molekule.gen.utils

import org.isk.molekule.gen.geomertry.Box
import org.isk.molekule.gen.geomertry.isInside
import org.isk.molekule.gen.geomertry.isOnSurface
import org.isk.molekule.gen.geomertry.point.Point

infix fun Point.isInPeriodicRegion(box: Box): Boolean =
    this isInside box || (this isOnSurface box && this.x < box.xHigh && this.y < box.yHigh && this.z < box.zHigh)