package com.gmail.borlandlp.minigamesdtools.util.geometry

import org.bukkit.Location
import java.util.*

class GeneratedBlockSchema(
    val borderBlocks: MutableList<Location>,
    val fillerBlocks: MutableList<Location>
) {

    val allBlocks: MutableList<Location>
        get() {
            return mutableListOf<Location>().apply {
                this.addAll(fillerBlocks)
                this.addAll(borderBlocks)
            }
        }
}