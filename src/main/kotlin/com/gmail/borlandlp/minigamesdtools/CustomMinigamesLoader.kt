package com.gmail.borlandlp.minigamesdtools

import com.gmail.borlandlp.minigamesdtools.Debug.print
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath
import org.bukkit.Bukkit
import org.bukkit.plugin.InvalidDescriptionException
import org.bukkit.plugin.InvalidPluginException
import org.bukkit.plugin.Plugin
import java.io.File
import java.util.*

class CustomMinigamesLoader {
    private val loadedPlugins: MutableList<Plugin> =
        ArrayList()
    var currentLoadingPhase: LoadingPhase? = null
        private set

    private val addonsList: List<String>
        private get() {
            val addons: MutableList<String> = ArrayList()
            val source = ConfigPath.ADDONS.path.list()
            if (source != null) {
                for (addonName in source) {
                    if (addonName.contains(".jar")) {
                        addons.add(addonName)
                    } else {
                        print(
                            Debug.LEVEL.WARNING,
                            "Ignore path '$addonName'!"
                        )
                    }
                }
            } else {
                print(
                    Debug.LEVEL.WARNING,
                    "Addons path does not contains files."
                )
            }
            return addons
        }

    @Throws(InvalidPluginException::class, InvalidDescriptionException::class)
    private fun loadAddon(jarName: String) {
        val addonFile = File(ConfigPath.ADDONS.path, jarName)
        print(
            Debug.LEVEL.NOTICE,
            "Load addon '$jarName'"
        )
        val plugin = Bukkit.getPluginManager().loadPlugin(addonFile)
        if (plugin != null) {
            print(
                Debug.LEVEL.NOTICE,
                "Trying to enable plugin '" + plugin.name + "'."
            )
            Bukkit.getPluginManager().enablePlugin(plugin)
            loadedPlugins.add(plugin)
        } else {
            print(
                Debug.LEVEL.WARNING,
                "Error while load '$jarName'."
            )
        }
    }

    fun loadAddons() {
        currentLoadingPhase = LoadingPhase.IN_PROGRESS
        for (jarName in addonsList) {
            try {
                loadAddon(jarName)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        currentLoadingPhase = LoadingPhase.DONE
    }

    fun unloadAddons() {
        for (plugin in loadedPlugins) {
            try {
                Bukkit.getPluginManager().disablePlugin(plugin)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    enum class LoadingPhase {
        IN_PROGRESS, DONE
    }
}