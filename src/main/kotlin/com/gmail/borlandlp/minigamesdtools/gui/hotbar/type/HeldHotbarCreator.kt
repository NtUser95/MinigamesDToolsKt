package com.gmail.borlandlp.minigamesdtools.gui.hotbar.type

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.Creator
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.Hotbar
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.SlotItem
import org.bukkit.entity.Player
import java.util.*

@CreatorInfo(creatorId = "held_hotbar")
class HeldHotbarCreator : Creator {
    override fun getDataProviderRequiredFields(): List<String> {
        return listOf("player")
    }

    @Throws(Exception::class)
    override fun create(hotbarID: String, dataProvider: AbstractDataProvider): Hotbar {
        val hotbarCfg =
            MinigamesDTools.instance!!.configProvider!!.getEntity(ConfigPath.HOTBAR, hotbarID).data
                ?: throw Exception("cant find config file for hotbar[ID:$hotbarID]")
        val hotbar = HeldHotbar(dataProvider["player"] as Player)
        for (key in hotbarCfg.getConfigurationSection("slots").getKeys(false)) {
            val slotIndex = key.toInt()
            val slotID = hotbarCfg["slots.$key"].toString()
            var slotItem: SlotItem? = null
            if (MinigamesDTools.instance!!.hotbarItemCreatorHub!!.containsRouteId2Creator(slotID)) {
                try {
                    slotItem =
                        MinigamesDTools.instance!!.hotbarItemCreatorHub!!.createHotbarItem(slotID, DataProvider())
                    hotbar.setSlot(slotIndex, slotItem)
                } catch (e: Exception) {
                    throw Exception("Detected a factory problem for HotbarSlotConfig[ID:$slotID] for HotbarConfig[ID:$hotbarID]")
                }
            } else {
                throw Exception("Detected problem for HotbarSlotConfig[ID:$slotID] for HotbarConfig[ID:$hotbarID]")
            }
        }

        return hotbar
    }
}