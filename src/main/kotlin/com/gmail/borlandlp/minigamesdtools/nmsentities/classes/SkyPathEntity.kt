package com.gmail.borlandlp.minigamesdtools.nmsentities.classes

import net.minecraft.server.v1_12_R1.PathEntity
import net.minecraft.server.v1_12_R1.PathPoint
import net.minecraft.server.v1_12_R1.Vec3D

/*
* Spigot: net.minecraft.server.v1_12_R1.PathEntity
* Vanilla: net.minecraft.pathfinding.Path
* */
class SkyPathEntity(pathPoints: Array<PathPoint?>?) : PathEntity(pathPoints), PathProvider {
    override fun pathEnding(): Boolean {
        return this.b()
    }

    override fun reloadPath() {
        this.c(0)
    }

    override val path: Vec3D
        get() = f()
}