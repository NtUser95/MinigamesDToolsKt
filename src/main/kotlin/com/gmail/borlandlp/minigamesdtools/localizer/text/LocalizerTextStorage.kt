package com.gmail.borlandlp.minigamesdtools.localizer.text

interface LocalizerTextStorage {
    /**
     * Проверяет, содержит ли текст для textId
     * @param textId идентификатор текста
     * @return true/false
     */
    fun containsText(textId: String): Boolean

    /**
     * Возвращает текст для textId
     * @param textId
     * @return
     */
    fun getText(textId: String): String?

    val langCode: String?
}