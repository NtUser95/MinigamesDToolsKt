package com.gmail.borlandlp.minigamesdtools.arena

import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaEvent
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaEventPriority
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class RegisteredArenaListener // There is gold in the streets just waiting for someone to come and scoop it up.
@JvmOverloads constructor(
    val handler: ArenaEventListener,
    private val method: Method,
    val isIgnoreCanceled: Boolean = false,
    val priority: ArenaEventPriority = ArenaEventPriority.LOWEST
) {

    fun execute(event: ArenaEvent?) {
        try {
            method.invoke(handler, event)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
    }

}