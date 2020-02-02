package com.gmail.borlandlp.minigamesdtools.gui.hotbar.utils

import kotlin.math.pow

object Leveling {
    @JvmStatic
    fun calculateWithPercentage(level: Int, percent: Float): ExperienceContainer {
        val maxExp: Double = when {
            level <= 0 -> {
                0.0
            }
            level <= 16 -> {
                level.toDouble().pow(2.0) + 6 * level
            }
            level <= 31 -> {
                2.5 * level.toDouble().pow(2.0) - 40.5 * level + 360
            }
            else -> { // 32+
                4.5 * level.toDouble().pow(2.0) - 162.5 * level + 2220
            }
        }
        val percentExp = maxExp * (percent / 100.0)
        return ExperienceContainer(maxExp, percentExp, level)
    }

    class ExperienceContainer(var total_exp: Double, var percentExp: Double, var level: Int)
}