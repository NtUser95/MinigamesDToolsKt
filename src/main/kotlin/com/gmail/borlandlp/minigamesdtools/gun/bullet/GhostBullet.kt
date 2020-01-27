package com.gmail.borlandlp.minigamesdtools.gun.bullet

import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools
import com.gmail.borlandlp.minigamesdtools.events.BlockDamageByEntityEvent
import net.minecraft.server.v1_12_R1.EntityDragonFireball
import net.minecraft.server.v1_12_R1.EntityLiving
import net.minecraft.server.v1_12_R1.MovingObjectPosition
import net.minecraft.server.v1_12_R1.WorldServer
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.World
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld
import org.bukkit.entity.LivingEntity

class GhostBullet(private val bukkitWorld: World) : EntityDragonFireball((bukkitWorld as CraftWorld).handle) {
    private var inc = 0
    var maxLivingTicks = 100
    var livedTicks = 0
    private var craftWorld: WorldServer = (bukkitWorld as CraftWorld).handle

    // onImpact
    override fun a(position: MovingObjectPosition) {
        Debug.print(
            Debug.LEVEL.NOTICE,
            "GhostBullet explode"
        )
        MinigamesDTools.getInstance().bulletHandlerApi.removeBullet(this)
        if (position.entity == null || !position.entity.s(shooter)) {
            if (!craftWorld.isClientSide) {
                val var2: List<EntityLiving> =
                    craftWorld.a<EntityLiving>(EntityLiving::class.java, boundingBox.grow(1.0, 1.0, 1.0))
                if (var2.isNotEmpty()) {
                    val var4 = var2.iterator()
                    while (var4.hasNext()) {
                        val craftEntity = var4.next().bukkitEntity
                        (craftEntity as LivingEntity).damage(5.0)
                    }
                }
                if (position.type == MovingObjectPosition.EnumMovingObjectType.BLOCK) {
                    val blockposition = position.a()
                    val hitBlock =
                        bukkitWorld.getBlockAt(blockposition.x, blockposition.y, blockposition.z)
                    val livingEntity = shooter.bukkitEntity as LivingEntity
                    Bukkit.getPluginManager().callEvent(BlockDamageByEntityEvent(livingEntity, hitBlock, null, true))
                }
                die()
            }
        }
    }

    override fun B_() {
        val prevMotX = motX
        val prevMotY = motY
        val prevMotZ = motZ
        super.B_()
        motX = prevMotX
        motY = prevMotY
        motZ = prevMotZ
        if (inc++ >= 1) {
            bukkitWorld.spawnParticle(
                Particle.FIREWORKS_SPARK,
                Location(bukkitWorld, locX, locY, locZ),
                0,
                0.0,
                0.0,
                0.0
            )
            inc = 0
        }
        if (++livedTicks > maxLivingTicks) {
            die()
        }
    }

    override fun die() {
        super.die()
    }

}