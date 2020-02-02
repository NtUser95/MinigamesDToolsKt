package com.gmail.borlandlp.minigamesdtools.gui.hotbar.type

import com.gmail.borlandlp.minigamesdtools.gui.hotbar.Hotbar
import org.bukkit.entity.Player

class ItemInterractHotbar(player: Player) : Hotbar(player) {
    override fun update() {
        super.update()
        updateXpReloadStatus()
    }

    private fun updateXpReloadStatus() { /*long cdRemain = Instant.now().getEpochSecond() - this.getLastClickTime();
        if(cdRemain > this.getCooldownTime()) {

        SlotItem slotItem = this.slots[this.player.getInventory().getHeldItemSlot()];
        boolean nullFlag = slotItem == null && this.player.getExp() > 0.0F;
        boolean cdCompleted = slotItem != null && this.player.getExp() > 0.0F && slotItem.coo
        if(nullFlag || ) {
            this.player.setExp(0F);
        }*/
    }
}