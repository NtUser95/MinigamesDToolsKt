package com.gmail.borlandlp.minigamesdtools.nmsentities.classes

import net.minecraft.server.v1_12_R1.Vec3D

interface PathProvider {
    fun pathEnding(): Boolean
    fun reloadPath()
    val path: Vec3D?
}