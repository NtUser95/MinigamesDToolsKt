package com.gmail.borlandlp.minigamesdtools.gui.other.radar2d

import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.block.Block

class BlockMarker(private val block: Block, private val chatColor: ChatColor,
                  override val color: ChatColor,
                  override val location: Location
) :
    Marker {
    override fun isOwner(`object`: Any): Boolean {
        return `object` is Block && `object`.location.toString() == this.location.toString()
    }
}