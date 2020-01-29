package com.gmail.borlandlp.minigamesdtools.localizer.config

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException
import java.util.*

class YAMLDbStorage(private val file: File) : LocalizerDbStorage {
    private val storage: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    override fun getLang(uuid: UUID): String? {
        return if(storage[uuid.toString()] != null) {
            storage[uuid.toString()].toString()
        } else null
    }

    override fun setLang(uuid: UUID, langCode: String) {
        storage[uuid.toString()] = langCode
        try {
            storage.save(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}