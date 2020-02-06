package com.gmail.borlandlp.minigamesdtools.listener

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools
import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase
import com.gmail.borlandlp.minigamesdtools.arena.ArenaPlayersRelative
import com.gmail.borlandlp.minigamesdtools.arena.localevent.*
import com.gmail.borlandlp.minigamesdtools.events.ArenaGameEndedEvent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.*
import org.bukkit.event.player.*
import org.bukkit.event.weather.WeatherChangeEvent
import org.bukkit.inventory.ItemStack

class Events : Listener {
    @EventHandler
    fun onArenaGameEnded(event: ArenaGameEndedEvent) {
        MinigamesDTools.instance!!.arenaAPI!!.restartArena(event.arena.name!!)
    }

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if (event.item == null || event.item.type != Material.BOW) {
            return
        }

        val arena = MinigamesDTools.instance!!.arenaAPI!!.getArenaOf(event.player) ?: return
        if (arena.state == ArenaBase.STATE.COUNTDOWN || arena.state == ArenaBase.STATE.PAUSED) {
            val oldItem = event.player.inventory.itemInMainHand.clone()
            event.player.inventory.itemInMainHand = ItemStack(Material.AIR)
            event.player.sendMessage("{match_hsnt_started_yet}")
            Bukkit.getScheduler().scheduleSyncDelayedTask(
                MinigamesDTools.instance,
                { event.player.inventory.itemInMainHand = oldItem },
                1L
            )
        }
    }

    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        val arena = MinigamesDTools.instance!!.arenaAPI!!.getArenaOf(event.player) ?: return
        if (arena.gameRules!!.beforeFightDisableMoving && arena.state == ArenaBase.STATE.COUNTDOWN || arena.state == ArenaBase.STATE.PAUSED) {
            if (event.from.x != event.to.x || event.from.y != event.to.y || event.from.z != event.to.z) {
                event.isCancelled = true
            }
        }
    }

    /*
    * <PlayerDMG>
    * */
    @EventHandler
    fun onEntityDamageEvent(event: EntityDamageEvent) {
        if (event.entity !is Player || event is EntityDamageByEntityEvent) {
            return
        }
        val player = event.entity as Player
        val arena = MinigamesDTools.instance!!.arenaAPI!!.getArenaOf(player) ?: return
        if (arena.state != ArenaBase.STATE.EMPTY) {
            if (player.health - event.finalDamage <= 0.0) {
                event.damage = 0.0
                val arenaEvent =
                    ArenaPlayerDeathLocalEvent(
                        player
                    )
                arena.eventAnnouncer.announce(arenaEvent)
                if (arenaEvent.isCancelled) {
                    event.isCancelled = true
                }
            } else {
                val arenaEvent =
                    ArenaPlayerDamagedLocalEvent(
                        player,
                        event.finalDamage
                    )
                arena.eventAnnouncer.announce(arenaEvent)
                if (arenaEvent.isCancelled) {
                    event.isCancelled = true
                }
            }
        } else if (arena.state == ArenaBase.STATE.PAUSED && arena.state == ArenaBase.STATE.COUNTDOWN) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onEntityDamageEvent(event: EntityDamageByEntityEvent) {
        if (event.entity !is Player) {
            return
        }
        val player = event.entity as Player
        val arena = MinigamesDTools.instance!!.arenaAPI!!.getArenaOf(player) ?: return
        if (arena.state != ArenaBase.STATE.EMPTY) {
            if (event.damager is Player) {
                val isFriendFire = arena.teamController!!.getPlayersRelative(
                        player,
                        event.damager as Player
                    ) == ArenaPlayersRelative.TEAMMATE
                if (isFriendFire && !arena.teamController!!.getTeamOf(player)!!.isFriendlyFireAllowed) {
                    event.isCancelled = true
                    event.damager.sendMessage("{attack_teammate_msg}")
                    return
                }
            }

            if (player.health - event.finalDamage <= 0.0) {
                event.damage = 0.0
                event.isCancelled = true
                val arenaEvent =
                    ArenaPlayerKilledLocalEvent(
                        player,
                        event.damager
                    )
                arena.eventAnnouncer.announce(arenaEvent)
            } else {
                val arenaEvent =
                    ArenaEntityDamagePlayerLocalEvent(
                        event.damager,
                        player,
                        event.finalDamage
                    )
                arena.eventAnnouncer.announce(arenaEvent)
                if (arenaEvent.isCancelled) {
                    event.isCancelled = true
                    event.damage = 0.0
                }
            }
        } else if (arena.state == ArenaBase.STATE.PAUSED || arena.state == ArenaBase.STATE.COUNTDOWN) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onPlayerDeathEvent(event: PlayerDeathEvent) {
        val player = event.entity ?: return
        val arena = MinigamesDTools.instance!!.arenaAPI!!.getArenaOf(player) ?: return
        val arenaEvent =
            ArenaPlayerDeathLocalEvent(player)
        arena.eventAnnouncer.announce(arenaEvent)
    }

    /*
    * </playerDmg>
    * */

    @EventHandler
    fun onFoodLevelChangeEvent(event: FoodLevelChangeEvent) {
        if (event.entity !is Player) {
            return
        }

        val player = event.entity as Player
        val arena = MinigamesDTools.instance!!.arenaAPI!!.getArenaOf(player) ?: return
        event.isCancelled = arena.state != ArenaBase.STATE.EMPTY && arena.gameRules!!.hungerDisable
    }

    @EventHandler
    fun onBedjoin(event: PlayerBedEnterEvent) {
        if (MinigamesDTools.instance!!.arenaAPI!!.getArenaOf(event.player) == null) {
            return
        }
        event.isCancelled = true
    }

    @EventHandler
    fun onPlayerKick(event: PlayerKickEvent) {
        val arena = MinigamesDTools.instance!!.arenaAPI!!.getArenaOf(event.player) ?: return
        if (arena.state != ArenaBase.STATE.EMPTY) {
            arena.eventAnnouncer.announce(
                ArenaPlayerLeaveLocalEvent(
                    event.player
                )
            )
        }
    }

    @EventHandler
    fun onLogout(event: PlayerQuitEvent) {
        val arena = MinigamesDTools.instance!!.arenaAPI!!.getArenaOf(event.player) ?: return
        if (arena.state != ArenaBase.STATE.EMPTY) {
            arena.eventAnnouncer.announce(
                ArenaPlayerLeaveLocalEvent(
                    event.player
                )
            )
        }
    }

    /*
    @EventHandler
    public void onClickEvent(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        if(player == null) {
            return;
        }
        if(!MinigamesDTools.getInstance().getArenaAPI().getCacheManager().contains(player)) {

            if(event.getCurrentItem() == null) {
                return;
            } else if(!event.getCurrentItem().hasItemMeta()) {
                return;
            } else if(!event.getCurrentItem().getItemMeta().hasDisplayName()) {
                return;
            }

            if(event.getCurrentItem().getItemMeta().getDisplayName().equals("[arena]")) {
                event.getWhoClicked().sendMessage(MinigamesDTools.getPrefix() + ChatColor.RED + " С арены запрещено выносить вещи. Ваш предмет был заменён на землю.");
                event.getCurrentItem().setType(Material.DIRT);
                for(Enchantment enchantment : event.getCurrentItem().getEnchantments().keySet()) {
                    event.getCurrentItem().removeEnchantment(enchantment);
                }
                event.getCurrentItem().setItemMeta(null);
            }
        }
    }
    */
    @EventHandler
    fun onPlayerDrop(event: PlayerDropItemEvent) {
        val arena = MinigamesDTools.instance!!.arenaAPI!!.getArenaOf(event.player) ?: return
        event.isCancelled = !arena.gameRules!!.playerCanItemDrop && arena.state != ArenaBase.STATE.EMPTY
    }

    @EventHandler
    fun onPlayerPickupDrop(event: EntityPickupItemEvent) {
        if (event.entity !is Player) {
            return
        }
        val arena = MinigamesDTools.instance!!.arenaAPI!!.getArenaOf(event.entity as Player) ?: return
        event.isCancelled = !arena.gameRules!!.playerCanItemPickup && arena.state != ArenaBase.STATE.EMPTY
    }

    @EventHandler
    fun onPlayerDamage(event: PlayerItemDamageEvent) {
        val arena = MinigamesDTools.instance!!.arenaAPI!!.getArenaOf(event.player) ?: return
        if (arena.state == ArenaBase.STATE.PAUSED || arena.state == ArenaBase.STATE.COUNTDOWN) {
            event.player.sendMessage("{damage_reject_while_cooldown_msg}")
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onTeleport(event: PlayerTeleportEvent) {
        val arena = MinigamesDTools.instance!!.arenaAPI!!.getArenaOf(event.player) ?: return
        if (arena.state == ArenaBase.STATE.COUNTDOWN || arena.state == ArenaBase.STATE.PAUSED) {
            if (event.from.distance(event.to) > 1.0) {
                event.isCancelled = true
                event.player.sendMessage("{teleport_reject_msg}")
            }
        }
    }

    @EventHandler
    fun onRegain(event: EntityRegainHealthEvent) {
        if (event.entity !is Player) return
        val arena = MinigamesDTools.instance!!.arenaAPI!!.getArenaOf(event.entity as Player) ?: return
        event.isCancelled = !arena.gameRules!!.playerCanRegainHealth
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onWeatherChangeEvent(event: WeatherChangeEvent) {
        if (event.toWeatherState()) {
            event.isCancelled = true
            event.world.weatherDuration = 0
            event.world.isThundering = false
        }
    }
}