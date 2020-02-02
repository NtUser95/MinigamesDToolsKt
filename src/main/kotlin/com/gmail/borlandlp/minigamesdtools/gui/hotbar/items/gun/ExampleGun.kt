package com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.gun

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.SlotItem
import org.bukkit.Sound
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
import org.bukkit.entity.Player

class ExampleGun : SlotItem() {
    override fun use(player: Player): Boolean {
        try {
            val bullet = MinigamesDTools.instance!!.bulletCreatorHub!!
                .createBullet("ExampleBullet", object : DataProvider() {
                    init {
                        this["world"] = player.world
                    }
                })
            bullet.setLocation(
                player.eyeLocation.x,
                player.eyeLocation.y,
                player.eyeLocation.z,
                player.location.yaw,
                player.location.pitch
            )
            bullet.f(
                player.location.direction.x * 1.5,
                player.location.direction.y * 1.5,
                player.location.direction.z * 1.5
            ) // velocity
            bullet.shooter = (player as CraftPlayer?)!!.handle
            player.world.playSound(player.location, Sound.ENTITY_FIREWORK_BLAST, 1f, 1f)
            MinigamesDTools.instance!!.bulletHandlerApi!!.addBullet(bullet)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }
}