package com.gmail.borlandlp.minigamesdtools.arena.localevent

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class ArenaEventHandler(
    val priority: ArenaEventPriority = ArenaEventPriority.LOWEST,
    val ignoreCancelled: Boolean = false
)