package com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.inventory_gui

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.Creator
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.util.*

@CreatorInfo(creatorId = "inventory_page_open_requester")
class InventoryPageOpenRequesterCreator : Creator() {
    @Throws(Exception::class)
    override fun create(id: String, dataProvider: AbstractDataProvider): InventoryPageOpenRequester {
        val requester = InventoryPageOpenRequester()
        val configEntity =
            MinigamesDTools.instance!!.configProvider!!.getEntity(ConfigPath.HOTBAR_SLOTS, id)
        val configurationSection = configEntity!!.data
        if (dataProvider.contains("page_id")) {
            requester.pageId = dataProvider["page_id"] as String
        } else {
            requester.pageId = configEntity.data["page_id"].toString()
        }
        val activeIcon =
            ItemStack(Material.getMaterial(configurationSection["active_icon"].toString()))
        requester.activeIcon = activeIcon
        val unactiveIcon =
            ItemStack(Material.getMaterial(configurationSection["unactive_icon"].toString()))
        requester.unactiveIcon = unactiveIcon
        requester.setCooldownTime(configurationSection["cooldown"].toString().toInt().toLong())
        requester.isInfiniteSlot = configurationSection["infinite"].toString().toBoolean()
        requester.amount = configurationSection["amount"].toString().toInt()
        return requester
    }
}