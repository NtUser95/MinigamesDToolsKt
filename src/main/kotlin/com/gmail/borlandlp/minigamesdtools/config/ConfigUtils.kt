package com.gmail.borlandlp.minigamesdtools.config

import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.InputStreamReader
import java.io.Reader
import java.nio.charset.StandardCharsets

object ConfigUtils {
    fun checkAndRestoreDefaultConfigs() {
        for (config in ConfigPath.values()) {
            if (!config.path.exists()) {
                if (config.isDir) {
                    config.path.mkdirs()
                } else {
                    restoreResource2Config(config.path.absoluteFile.name)
                }
            }
        }
    }

    fun restoreResource2Config(configName: String) {
        try {
            val defConfigStream: Reader = InputStreamReader(
                instance!!.getResource(configName),
                StandardCharsets.UTF_8
            )
            val defConfig =
                YamlConfiguration.loadConfiguration(defConfigStream)
            defConfig.save(
                File(
                    instance!!.dataFolder.absoluteFile,
                    configName
                )
            )
        } catch (ex: Exception) {
            Debug.print(
                Debug.LEVEL.WARNING,
                "An error occurred while restoring file:'$configName'"
            )
            ex.printStackTrace()
        }
    }
}