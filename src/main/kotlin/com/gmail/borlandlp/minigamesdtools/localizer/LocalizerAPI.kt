package com.gmail.borlandlp.minigamesdtools.localizer

import java.util.*

/**
 * Компонент, хранящий языковые настройки игрока, а также отвечающий за хранение локализованного текста.
 */
interface LocalizerAPI {
    /**
     * Устанавливает лвухзначное представление языка.
     * @param uuid
     */
    fun setLanguage(uuid: UUID, langCode: String)

    /**
     * @param uuid
     * @return Двузначное представление языка. en, ru, de
     */
    fun getLanguage(uuid: UUID): String

    fun getLocalizedText(uuid: UUID, textId: String): String?
    fun getLocalizedText(langCode: String, textId: String): String?
}