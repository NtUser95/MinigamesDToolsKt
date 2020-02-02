package com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.gun

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.Creator
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.lang.Boolean
import java.util.*

@CreatorInfo(creatorId = "example_gun")
class ExampleGunCreator : Creator() {
    @Throws(Exception::class)
    override fun create(id: String, dataProvider: AbstractDataProvider): ExampleGun {
        val slotItem = ExampleGun()
        val configurationSection =
            MinigamesDTools.instance!!.configProvider!!.getEntity(ConfigPath.HOTBAR_SLOTS, id)!!.data
        val activeIcon =
            ItemStack(Material.getMaterial(configurationSection["active_icon"].toString()))
        val unactiveIcon =
            ItemStack(Material.getMaterial(configurationSection["unactive_icon"].toString()))
        val cooldown = configurationSection["cooldown"].toString().toLong()
        val infinite = Boolean.parseBoolean(configurationSection["infinite"].toString())
        val amount = configurationSection["amount"].toString().toInt()
        slotItem.activeIcon = activeIcon
        slotItem.unactiveIcon = unactiveIcon
        slotItem.setCooldownTime(cooldown)
        slotItem.isInfiniteSlot = infinite
        slotItem.amount = amount
        return slotItem
    }
}