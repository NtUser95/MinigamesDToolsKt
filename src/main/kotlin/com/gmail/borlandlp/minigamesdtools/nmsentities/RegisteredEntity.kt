package com.gmail.borlandlp.minigamesdtools.nmsentities

import net.minecraft.server.v1_12_R1.Entity

class RegisteredEntity(
    val name: String,
    val id: Int,
    val oldClass: Class<out Entity>,
    val newClass: Class<out Entity>
)