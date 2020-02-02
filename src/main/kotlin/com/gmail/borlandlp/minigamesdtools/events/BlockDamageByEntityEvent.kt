package com.gmail.borlandlp.minigamesdtools.events

import org.bukkit.block.Block
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.inventory.ItemStack

/*
* TODO: Перевести на английский
* Разработчики Spigot, почему-то, не разрешили ломать блоки всем, кроме Player.
* Эта дискриминация мешает как Entity, так и мне. Исправим это.
* */
class BlockDamageByEntityEvent(
    val entity: LivingEntity,
    val block: Block,
    val itemStack: ItemStack?,
    val isInstabreak: Boolean
) : Event(), Cancellable {
    private var canceled = false

    override fun isCancelled(): Boolean {
        return canceled
    }

    override fun setCancelled(b: Boolean) {
        canceled = b
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}