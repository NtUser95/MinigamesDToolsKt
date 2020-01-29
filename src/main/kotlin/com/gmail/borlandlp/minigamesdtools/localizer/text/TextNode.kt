package com.gmail.borlandlp.minigamesdtools.localizer.text

/**
 * Содержит в себе представление какой-либо строки на различные языки.
 */
interface TextNode {
    /**
     * @return Список доступных языков для этой ноды
     */
    fun containsLanguages(): Set<String>

    /**
     * @param langCode - двухзначный код языка, для которого будет запрошена строка ноды.
     * @return String неформатированное представление ноды для langCode
     */
    fun getForLanguage(langCode: String): String
}