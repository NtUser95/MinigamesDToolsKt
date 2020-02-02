package com.gmail.borlandlp.minigamesdtools.activepoints

import com.gmail.borlandlp.minigamesdtools.events.BlockDamageByEntityEvent
import org.bukkit.entity.Entity
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*

class ActivePointsListener(private val controller: ActivePointController) : Listener {
    /* nickname -> coordX + "" + coordY + "" + coordZ */
    private val playersPosCache: MutableMap<String, String> = mutableMapOf()
    //ToDO: all cooldowns to pattern chain-of-responsibility
    private val damageCooldowns: MutableMap<Entity, Long> = mutableMapOf()
    private val intersectionCooldowns: MutableMap<Entity, Long> = mutableMapOf()
    private val interactCooldowns: MutableMap<Entity, Long> = mutableMapOf()
    private val damageCd_Ms: Long = 500 // 0.5 sec
    private val intersectionCd_Ms: Long = 1000 // 1 sec
    private val interactCd_Ms: Long = 750

    @EventHandler(priority = EventPriority.MONITOR)
    fun onInteract(event: PlayerInteractEvent) {
        if (event.clickedBlock == null) return
        if (!interactCooldowns.containsKey(event.player)) {
            interactCooldowns[event.player] = System.nanoTime()
        } else {
            val msDiff = (System.nanoTime() - interactCooldowns[event.player]!!) / 1000000
            if (msDiff <= interactCd_Ms) {
                return
            } else {
                interactCooldowns[event.player] = System.nanoTime()
            }
        }
        val activePoint = controller.staticPointsCache[event.clickedBlock.location]
        if (activePoint != null && activePoint.isPerformInteraction) {
            activePoint.performInteract(event.player)
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onPlayerMove(event: PlayerMoveEvent) {
        val curXYZ =
            event.to.blockX.toString() + "" + event.to.blockY + "" + event.to.blockZ
        if (!playersPosCache.containsKey(event.player.name) || playersPosCache[event.player.name] != curXYZ) {
            playersPosCache[event.player.name] = curXYZ
            if (controller.staticPointsCache.contains(event.to)) { //check cd
                if (!intersectionCooldowns.containsKey(event.player)) {
                    intersectionCooldowns[event.player] = System.nanoTime()
                } else {
                    val msDiff =
                        (System.nanoTime() - intersectionCooldowns[event.player]!!) / 1000000
                    if (msDiff <= intersectionCd_Ms) {
                        return
                    } else {
                        intersectionCooldowns[event.player] = System.nanoTime()
                    }
                }
                val activePoint = controller.staticPointsCache[event.to]
                if (activePoint.isPerformEntityIntersection) {
                    activePoint.performIntersect(event.player)
                }
            }
        }
    }

    /*
    * Ломание блока рукой игрока
    * */
    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        if (controller.staticPointsCache.contains(event.block.location)) { //check cd
            if (!damageCooldowns.containsKey(event.player)) {
                damageCooldowns[event.player] = System.nanoTime()
            } else {
                val msDiff = (System.nanoTime() - damageCooldowns[event.player]!!) / 1000000
                if (msDiff <= damageCd_Ms) {
                    event.isCancelled = true
                    return
                } else {
                    damageCooldowns[event.player] = System.nanoTime()
                }
            }
            val activePoint = controller.staticPointsCache[event.block.location]
            if (activePoint.isPerformDamage) {
                activePoint.performDamage(event.player, 1.0)
            }
            if (!activePoint.isDamageable) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onBlockDamage(event: BlockDamageByEntityEvent) {
        if (controller.staticPointsCache.contains(event.block.location)) { //check cd
            if (!damageCooldowns.containsKey(event.entity)) {
                damageCooldowns[event.entity] = System.nanoTime()
            } else {
                val msDiff = (System.nanoTime() - damageCooldowns[event.entity]!!) / 1000000
                if (msDiff <= damageCd_Ms) {
                    event.isCancelled = true
                    return
                } else {
                    damageCooldowns[event.entity] = System.nanoTime()
                }
            }
            val activePoint = controller.staticPointsCache[event.block.location]
            if (activePoint.isPerformDamage) {
                activePoint.performDamage(event.entity, 1.0)
            }
            if (!activePoint.isDamageable) {
                event.isCancelled = true
            }
        }
    }

    /* <freeing memory from disconnected players> */
    @EventHandler
    fun onPlayerDisc(event: PlayerQuitEvent) {
        playersPosCache.remove(event.player.name)
    }

    @EventHandler
    fun onPlayerKick(event: PlayerKickEvent) {
        playersPosCache.remove(event.player.name)
    } /* </freeing memory from disconnected players> */

}