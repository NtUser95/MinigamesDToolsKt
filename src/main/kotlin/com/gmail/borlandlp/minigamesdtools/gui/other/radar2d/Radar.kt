package com.gmail.borlandlp.minigamesdtools.gui.other.radar2d

import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

interface Radar {
    fun draw()
    var drawDistance: Int
    var viewer: Player?
    fun addMarker(marker: Marker)
    fun removeMarker(marker: Marker)
    fun getCurrentMarker(block: Block): Marker?
    fun getCurrentMarker(entity: Entity): Marker?
    fun onUnload()
    fun onLoad()
}