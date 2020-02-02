package com.gmail.borlandlp.minigamesdtools.activepoints.type.block

import org.bukkit.Material

abstract class PrimitiveBlockPoint : StaticBlockPoint() {
    var isHollow = false
    var borderMaterial: Material = Material.AIR
    var fillerMaterial: Material = Material.AIR
}