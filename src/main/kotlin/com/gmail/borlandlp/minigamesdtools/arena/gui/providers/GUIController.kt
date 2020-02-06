package com.gmail.borlandlp.minigamesdtools.arena.gui.providers

import com.gmail.borlandlp.minigamesdtools.arena.ArenaComponent
import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent
import java.util.*

class GUIController : ArenaComponent(), ArenaPhaseComponent {
    private val providers: MutableList<GUIProvider> = ArrayList()
    private var guiListener: GUIListener? = null

    fun getProviders(): List<GUIProvider> {
        return providers
    }

    fun addProvider(provider: GUIProvider) {
        providers.add(provider)
    }

    fun removeProvider(provider: GUIProvider?) {
        providers.remove(provider)
    }

    override fun update() {}
    override fun beforeRoundStarting() {}
    override fun onRoundEnd() {}
    override fun onInit() {
        guiListener = GUIListener(this)
        try {
            arena!!.eventAnnouncer.register(guiListener!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        for (provider in getProviders()) {
            try {
                arena!!.phaseComponentController.register(provider)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    override fun beforeGameStarting() {}
    override fun gameEnded() {
        arena!!.eventAnnouncer.unregister(guiListener!!)
    }
}