package com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.gun

import com.gmail.borlandlp.minigamesdtools.DefaultCreators
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.SlotItem
import com.gmail.borlandlp.minigamesdtools.gun.bullet.GhostBullet
import org.bukkit.Sound
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
import org.bukkit.entity.Player

class ExampleGun : SlotItem() {
    override fun use(player: Player): Boolean {
        try {
            val bullet = MinigamesDTools.instance!!.creatorsRegistry.get(DefaultCreators.BULLET.pseudoName)!!
                .create("ExampleBullet", object : DataProvider() {
                    init {
                        this["world"] = player.world
                    }
                }).apply {
                    if (this is GhostBullet) {
                        this.setLocation(
                            player.eyeLocation.x,
                            player.eyeLocation.y,
                            player.eyeLocation.z,
                            player.location.yaw,
                            player.location.pitch
                        )
                        this.f(
                            player.location.direction.x * 1.5,
                            player.location.direction.y * 1.5,
                            player.location.direction.z * 1.5
                        ) // velocity
                        this.shooter = (player as CraftPlayer).handle
                        player.world.playSound(player.location, Sound.ENTITY_FIREWORK_BLAST, 1f, 1f)
                        MinigamesDTools.instance!!.bulletHandlerApi!!.addBullet(this)
                    }
                }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }
}