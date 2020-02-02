package com.gmail.borlandlp.minigamesdtools.gui.inventory.api

import com.gmail.borlandlp.minigamesdtools.APIComponent
import com.gmail.borlandlp.minigamesdtools.gui.inventory.DrawableInventory
import org.bukkit.entity.Player
import java.util.*

class ViewManager : InventoryAPI, APIComponent {
    private val viewMap: MutableMap<String, DrawableInventory> =
        Hashtable()

    override fun show(drawableInventory: DrawableInventory, player: Player) {
        player.openInventory?.close()
        viewMap[player.name] = drawableInventory
        player.openInventory(drawableInventory.toBukkitInventory())
    }

    override fun getInventory(player: Player): DrawableInventory {
        return viewMap[player.name]!!
    }

    override fun isView(player: Player): Boolean {
        return viewMap.containsKey(player.name)
    }

    override fun close(player: Player) {
        unregister(player)
    }

    override fun unregister(player: Player) {
        viewMap.remove(player.name)
    }

    override fun onLoad() {}
    override fun onUnload() {}
}