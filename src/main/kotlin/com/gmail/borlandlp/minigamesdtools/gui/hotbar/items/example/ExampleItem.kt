package com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.example

import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.SlotItem
import org.bukkit.entity.Player

class ExampleItem : SlotItem() {
    override fun use(player: Player): Boolean {
        Debug.print(
            Debug.LEVEL.NOTICE,
            "use item"
        )
        return true
    }
}