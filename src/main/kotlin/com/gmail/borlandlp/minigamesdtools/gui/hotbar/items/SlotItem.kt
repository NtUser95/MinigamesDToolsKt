package com.gmail.borlandlp.minigamesdtools.gui.hotbar.items

import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class SlotItem {
    protected var cooldownTime: Long? = null
    var activeIcon: ItemStack? = null
    var unactiveIcon: ItemStack? = null
    var amount = 0
    var iD: String? = null
    var lastClickTime: Long = 0
    var name: String? = null
    var isInfiniteSlot = false
    var useSound: Sound? = null

    fun getCooldownTime(): Long {
        return cooldownTime!!
    }

    fun setCooldownTime(cooldownTime: Long) {
        this.cooldownTime = cooldownTime
    }

    val cooldownRemain: Long
        get() = if (lastClickTime == 0L) 0L else getCooldownTime() - (System.nanoTime() - lastClickTime) / 1000000

    fun inCooldown(): Boolean {
        return lastClickTime != 0L && cooldownRemain > 0L
    }

    fun performClick(player: Player) {
        if (!inCooldown()) {
            lastClickTime = System.nanoTime()
            val result = this.use(player)
            if (result) {
                if (!isInfiniteSlot) {
                    amount = amount - 1
                }
                if (useSound != null) {
                    player.world.playSound(player.location, useSound, 1f, 1f)
                }
            }
        } else {
            player.sendMessage("DBG:CD_REMAIN->" + cooldownRemain) //DEBUG
        }
    }

    //item in cooldown
    val drawData: ItemStack
        get() {
            val icon: ItemStack
            val msDiff = (System.nanoTime() - lastClickTime) / 1000000
            if (msDiff > getCooldownTime()) {
                icon = activeIcon!!.clone()
                icon.amount = amount
            } else { //item in cooldown
                icon = unactiveIcon!!.clone()
            }
            return icon
        }

    abstract fun use(player: Player): Boolean

    override fun toString(): String {
        val str = StringBuilder()
            .append("{")
            .append("activeIcon=").append(activeIcon!!.type.name)
            .append(", unactiveIcon=").append(unactiveIcon!!.type.name)
            .append(", amount=").append(amount)
            .append(", infiniteSlot=").append(isInfiniteSlot)
            .append(", name=").append(name)
            .append("}")
        return str.toString()
    }
}