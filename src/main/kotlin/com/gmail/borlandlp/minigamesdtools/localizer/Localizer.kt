package com.gmail.borlandlp.minigamesdtools.localizer

import com.gmail.borlandlp.minigamesdtools.APIComponent
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools
import com.gmail.borlandlp.minigamesdtools.localizer.config.LocalizerDbStorage
import com.gmail.borlandlp.minigamesdtools.localizer.config.YAMLDbStorage
import java.io.File
import java.util.*

// TODO: Недоделанный кусок кода. i18n?
class Localizer : APIComponent, LocalizerAPI {
    private val defaultLang = "en"
    private var dbStorage: LocalizerDbStorage? = null

    override fun onLoad() {
        val locPath =
            File(MinigamesDTools.getInstance().dataFolder.absoluteFile, "localizer")
        dbStorage = YAMLDbStorage(File(locPath, "db.yml"))
        val locConfig = File(locPath, "config.yml")
    }

    override fun onUnload() { // unload files from disk
    }

    override fun setLanguage(uuid: UUID, langCode: String) {
        dbStorage!!.setLang(uuid, langCode)
    }

    override fun getLanguage(uuid: UUID): String {
        return dbStorage!!.getLang(uuid) ?: defaultLang
    }

    override fun getLocalizedText(uuid: UUID, textId: String): String? {
        val langCode = dbStorage!!.getLang(uuid)
        return if (langCode != null) this.getLocalizedText(
            langCode,
            textId
        ) else this.getLocalizedText(defaultLang, textId)
    }

    override fun getLocalizedText(langCode: String, textId: String): String? {
        return null
    }
}