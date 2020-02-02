package com.gmail.borlandlp.minigamesdtools.gui.hotbar

import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.SlotItem
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.utils.Leveling.calculateWithPercentage
import net.minecraft.server.v1_12_R1.PacketPlayOutExperience
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

abstract class Hotbar {
    protected var slots = arrayOfNulls<SlotItem>(9)
    protected var itemsInQueue: Deque<SlotItem> = ArrayDeque()
    protected var player: Player

    constructor(player: Player) {
        this.player = player
    }

    constructor(player: Player, items: Array<SlotItem?>) {
        this.player = player
        slots = items
    }

    fun addSlot(slotItem: SlotItem) {
        itemsInQueue.add(slotItem)
    }

    @Throws(Exception::class)
    fun setSlot(index: Int, slot: SlotItem) {
        if (index < slots.size && index >= 0) {
            slots[index] = slot
        } else {
            throw Exception("HotbarSlot with ID: '" + slot.name + "' has an incorrect index[" + index + "] or is out of bounds [correct -> 0-8]")
        }
    }

    open fun update() {
        updateSlotsQueue()
    }

    fun performAction(slotID: Int) {
        Debug.print(
            Debug.LEVEL.NOTICE,
            "[SkyBattle] performAction slotID:" + slotID + " for player:" + player.displayName
        )
        if (slotID < slots.size && slotID >= 0 && slots[slotID] != null) {
            slots[slotID]!!.performClick(player)
            if (slots[slotID]!!.amount < 1) {
                slots[slotID] = null
            }
        }
    }

    val drawData: Array<ItemStack?>
        get() {
            val data = arrayOfNulls<ItemStack>(9)
            for (i in slots.indices) {
                if (slots[i] != null) {
                    data[i] = slots[i]!!.drawData
                }
            }
            return data
        }

    fun draw() {
        val drawData = drawData
        val inventory = player.inventory.contents
        val heldSlot = player.inventory.heldItemSlot
        if (slots[heldSlot] != null && slots[heldSlot]!!.inCooldown()) {
            val percent =
                (slots[heldSlot]!!.getCooldownTime() - slots[heldSlot]!!.cooldownRemain).toFloat() / slots[heldSlot]!!.getCooldownTime()
            val secRemain = (slots[heldSlot]!!.cooldownRemain / 1000).toInt()
            val value =
                calculateWithPercentage((slots[heldSlot]!!.cooldownRemain / 1000).toInt(), percent)
            val packet = PacketPlayOutExperience(percent, value.total_exp.toInt(), secRemain)
            (player as CraftPlayer).handle.playerConnection.sendPacket(packet)
        }
        if (!isIdentDrawData(drawData, inventory)) {
            for (i in 0..8) {
                inventory[i] = drawData[i]
            }
            player.inventory.contents = inventory
        }
    }

    private fun isIdentDrawData(
        drawData: Array<ItemStack?>,
        inventory: Array<ItemStack?>
    ): Boolean {
        for (i in 0..8) {
            if (inventory[i] == null && drawData[i] != null || inventory[i] != null && drawData[i] == null) {
                return false
            } else if (inventory[i] != null && drawData[i] != null) {
                val materialChanged = inventory[i]!!.type != drawData[i]!!.type
                val amountChanged = inventory[i]!!.amount != drawData[i]!!.amount
                if (materialChanged || amountChanged) {
                    return false
                }
            }
        }
        return true
    }

    private fun updateSlotsQueue() {
        for (i in 0 until slots.size - 1) { // use 8 slots. 9 slot - reserved for correct work itemHeldEvent
            if (slots[i] == null && itemsInQueue.size > 0) {
                slots[i] = itemsInQueue.poll()
            }
        }
    }
}