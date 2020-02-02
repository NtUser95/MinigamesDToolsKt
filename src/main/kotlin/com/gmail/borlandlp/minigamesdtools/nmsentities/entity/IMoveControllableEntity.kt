package com.gmail.borlandlp.minigamesdtools.nmsentities.entity

import com.gmail.borlandlp.minigamesdtools.nmsentities.classes.PathProvider
import net.minecraft.server.v1_12_R1.Vec3D

interface IMoveControllableEntity {
    fun setPath(path: PathProvider)
    fun vec2Path(vec3D: Vec3D): PathProvider
    fun isFollowsThisPath(pathProvider: PathProvider): Boolean
    fun teleport(pathProvider: PathProvider)
}