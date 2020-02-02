package com.gmail.borlandlp.minigamesdtools.activepoints.type.block

import com.gmail.borlandlp.minigamesdtools.activepoints.BuildSchema
import com.gmail.borlandlp.minigamesdtools.util.GeometryHelper.generateFlatSphere
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.BlockFace
import java.util.*
import java.util.function.Consumer

class FlatSpherePoint : PrimitiveBlockPoint() {
    override val buildSchema: BuildSchema
        get() {
            val direction = BlockFace.valueOf(direction!!)
            val generatedBlockSchema = generateFlatSphere(location!!, direction, radius, isHollow)
            val blocks: MutableMap<Location, Material> = mutableMapOf()
            generatedBlockSchema.borderBlocks.forEach(Consumer { location: Location ->
                blocks[location] = borderMaterial
            })
            generatedBlockSchema.fillerBlocks.forEach(Consumer { location: Location ->
                blocks[location] = fillerMaterial
            })
            return BuildSchema(blocks)
        }
}