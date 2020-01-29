package com.gmail.borlandlp.minigamesdtools.localizer.config

import java.util.*

interface LocalizerDbStorage {
    fun getLang(uuid: UUID): String?
    fun setLang(uuid: UUID, langCode: String)
}