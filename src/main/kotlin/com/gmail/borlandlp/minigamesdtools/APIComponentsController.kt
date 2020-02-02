package com.gmail.borlandlp.minigamesdtools

import java.util.*

class APIComponentsController {
    private val components: MutableList<APIComponent> = ArrayList()
    fun register(c: APIComponent) {
        components.add(c)
    }

    fun unregister(c: APIComponent) {
        components.remove(c)
    }

    private fun announceComponent(
        component: APIComponent,
        phase: ComponentEvent
    ) {
        try {
            when (phase) {
                ComponentEvent.PLUGIN_LOAD -> component.onLoad()
                ComponentEvent.PLUGIN_UNLOAD -> component.onUnload()
                else -> throw Exception("Invalid phase name:" + phase.name)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun announceEvent(componentEvent: ComponentEvent) {
        for (component in getComponents()) {
            announceComponent(component, componentEvent)
        }
    }

    fun getComponents(): List<APIComponent> {
        return ArrayList(components)
    }

    enum class ComponentEvent {
        PLUGIN_LOAD, PLUGIN_UNLOAD
    }
}