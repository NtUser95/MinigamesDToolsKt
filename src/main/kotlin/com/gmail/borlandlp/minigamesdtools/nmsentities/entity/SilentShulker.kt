package com.gmail.borlandlp.minigamesdtools.nmsentities.entity

import net.minecraft.server.v1_12_R1.EntityShulker
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.craftbukkit.v1_12_R1.CraftServer
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftShulker
import org.bukkit.entity.Shulker
import org.bukkit.event.entity.CreatureSpawnEvent

class SilentShulker(world: World) :
    EntityShulker((world as CraftWorld).handle as net.minecraft.server.v1_12_R1.World) {

    private val wrappedBukkitEntity: CraftShulker = CraftShulker(Bukkit.getServer() as CraftServer, this)

    override fun getBukkitEntity(): CraftShulker {
        return wrappedBukkitEntity
    }

    fun spawn(loc: Location): Shulker {
        setLocation(loc.x, loc.y, loc.z, loc.yaw, loc.pitch)
        world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM)
        return getBukkitEntity()
    }
}