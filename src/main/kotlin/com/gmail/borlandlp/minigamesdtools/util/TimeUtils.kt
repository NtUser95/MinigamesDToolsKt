package com.gmail.borlandlp.minigamesdtools.util

object TimeUtils {
    fun seconds2TimeF(secs: Int): String {
        val minutes = secs / 60
        val seconds = (secs - minutes) * 60

        return if (minutes > 0) "$minutes мин., $seconds сек." else "$seconds секунд(ы)"
    }
}