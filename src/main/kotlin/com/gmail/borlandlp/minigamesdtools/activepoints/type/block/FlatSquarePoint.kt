package com.gmail.borlandlp.minigamesdtools.activepoints.type.block

import com.gmail.borlandlp.minigamesdtools.activepoints.BuildSchema
import com.gmail.borlandlp.minigamesdtools.util.GeometryHelper.generateFlatSquare
import org.bukkit.Location
import org.bukkit.Material
import java.util.*
import java.util.function.Consumer

class FlatSquarePoint : PrimitiveBlockPoint() {
    override val buildSchema: BuildSchema
        get() {
            val generatedBlockSchema =
                generateFlatSquare(location!!, radius, isHollow)
            val blocks: MutableMap<Location, Material> =
                HashMap()
            generatedBlockSchema.borderBlocks.forEach(Consumer { location: Location ->
                blocks[location] = borderMaterial
            })
            generatedBlockSchema.fillerBlocks.forEach(Consumer { location: Location ->
                blocks[location] = fillerMaterial
            })
            return BuildSchema(blocks)
        }
}