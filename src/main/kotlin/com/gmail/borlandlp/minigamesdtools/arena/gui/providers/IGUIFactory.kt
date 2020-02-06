package com.gmail.borlandlp.minigamesdtools.arena.gui.providers

interface IGUIFactory {
    fun create(ID: String): GUIProvider
}