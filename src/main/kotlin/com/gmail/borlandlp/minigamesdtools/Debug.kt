package com.gmail.borlandlp.minigamesdtools

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath
import org.fusesource.jansi.Ansi
import java.util.logging.Level

object Debug {
    private var enabled = true
    private val curDebugLevel = INT_LEVEL.ALL

    fun init() {
        try {
            val conf =
                instance!!.configProvider!!.getEntity(ConfigPath.MAIN, "minigamesdtools")
            enabled =
                java.lang.Boolean.parseBoolean(conf!!.data["debug"].toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun print(reqDebugLevel: LEVEL, msg: String?) {
        if (enabled && isValidLevel(
                reqDebugLevel
            )
        ) {
            val str = StringBuilder()
            str.append(Ansi.ansi().fg(Ansi.Color.BLUE).boldOff().toString())
            str.append("DEBUG[Level->")
            if (reqDebugLevel == LEVEL.WARNING) {
                str.append(Ansi.ansi().fg(Ansi.Color.RED).boldOff().toString())
            } else {
                str.append(Ansi.ansi().fg(Ansi.Color.GREEN).boldOff().toString())
            }
            str.append(reqDebugLevel.toString())
            str.append(Ansi.ansi().fg(Ansi.Color.BLUE).boldOff().toString())
            str.append("]")
            str.append(Ansi.ansi().fg(Ansi.Color.WHITE).boldOff().toString())
            str.append(":")
            str.append(msg)
            str.append(Ansi.ansi().fg(Ansi.Color.WHITE).boldOff().toString())
            instance!!.logger.log(Level.INFO, str.toString())
        }
    }

    private fun isValidLevel(reqDebugLevel: LEVEL): Boolean {
        return if (curDebugLevel == INT_LEVEL.ALL) {
            true
        } else if (curDebugLevel == INT_LEVEL.WARNING && reqDebugLevel == LEVEL.WARNING) {
            true
        } else curDebugLevel == INT_LEVEL.NOTICE && reqDebugLevel == LEVEL.NOTICE
    }

    private enum class INT_LEVEL {
        ALL, WARNING, NOTICE, NONE
    }

    enum class LEVEL {
        WARNING, NOTICE
    }
}