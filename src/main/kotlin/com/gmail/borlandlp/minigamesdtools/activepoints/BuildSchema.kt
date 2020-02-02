package com.gmail.borlandlp.minigamesdtools.activepoints

import org.bukkit.Location
import org.bukkit.Material
import java.util.*

class BuildSchema(map: Map<Location, Material>) {
    var schema: Map<Location, Material> = mutableMapOf()

    init {
        schema = map
    }
}