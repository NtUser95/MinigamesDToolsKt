package com.gmail.borlandlp.minigamesdtools.localizer.text

import com.gmail.borlandlp.minigamesdtools.Debug
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class YAMLTextStorage(private val file: File) : LocalizerTextStorage {
    private val storage: YamlConfiguration = YamlConfiguration.loadConfiguration(file)
    override val langCode: String? = null

    override fun containsText(textId: String): Boolean {
        if (textId.contains(".")) {
            val msg =
                "textId[ID:$textId] contains a directory delimiter for YAML. This may cause a malfunction of the plugin."
            Debug.print(
                Debug.LEVEL.WARNING,
                msg
            )
        }
        return storage.contains(textId)
    }

    override fun getText(textId: String): String? {
        if (textId.contains(".")) {
            val msg =
                "textId[ID:$textId] contains a directory delimiter for YAML. This may cause a malfunction of the plugin."
            Debug.print(
                Debug.LEVEL.WARNING,
                msg
            )
        }
        return storage.getString(textId)
    }

}