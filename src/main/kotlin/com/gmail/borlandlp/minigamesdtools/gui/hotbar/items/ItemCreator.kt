package com.gmail.borlandlp.minigamesdtools.gui.hotbar.items

interface ItemCreator {
    fun create(itemID: String): SlotItem
}