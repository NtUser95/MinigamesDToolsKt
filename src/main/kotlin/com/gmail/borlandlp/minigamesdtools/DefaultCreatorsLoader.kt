package com.gmail.borlandlp.minigamesdtools

import com.gmail.borlandlp.minigamesdtools.Debug.print
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import com.gmail.borlandlp.minigamesdtools.activepoints.behaviors.custom.ExampleBehaviorCreator
import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.custom.ItemGiveReactionCreator
import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.custom.TeamIncrementWinTicketsReactionCreator
import com.gmail.borlandlp.minigamesdtools.activepoints.type.block.PrimitivePointCreator
import com.gmail.borlandlp.minigamesdtools.activepoints.type.entity.PrimitiveEntityCreator
import com.gmail.borlandlp.minigamesdtools.arena.ExampleArenaCreator
import com.gmail.borlandlp.minigamesdtools.arena.commands.DefaultWatcherCreator
import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.examples.bossbar.BossbarExampleCreator
import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.examples.scoreboard.ScoreboardExampleCreator
import com.gmail.borlandlp.minigamesdtools.arena.scenario.DefaultScenarioChainCreator
import com.gmail.borlandlp.minigamesdtools.arena.scenario.simplyteampvp.ExampleScenarioCreator
import com.gmail.borlandlp.minigamesdtools.arena.scenario.simplyteampvp.ExampleTwoScenarioCreator
import com.gmail.borlandlp.minigamesdtools.arena.team.ExampleTeamCreator
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.respawn.ExampleRespawnLobbyCreator
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.spectator.ExampleSpectatorLobbyCreator
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.starter.StarterLobbyCreator
import com.gmail.borlandlp.minigamesdtools.conditions.examples.EmptyInventoryConditionCreator
import com.gmail.borlandlp.minigamesdtools.conditions.examples.ExampleConditionCreator
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.example.ExampleItemCreator
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.gun.ExampleGunCreator
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.inventory_gui.InventoryPageOpenRequesterCreator
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.type.HeldHotbarCreator
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.type.ItemInterractHotbarCreator
import com.gmail.borlandlp.minigamesdtools.gui.inventory.DefaultViewInventoryCreator
import com.gmail.borlandlp.minigamesdtools.gui.inventory.slots.ExampleInventorySlotCreator
import com.gmail.borlandlp.minigamesdtools.gun.bullet.GhostBulletCreator
import com.gmail.borlandlp.minigamesdtools.lobby.ExampleLobbyCreator
import com.gmail.borlandlp.minigamesdtools.nmsentities.entity.SilentShulker
import com.gmail.borlandlp.minigamesdtools.nmsentities.entity.SkyDragon
import com.gmail.borlandlp.minigamesdtools.nmsentities.entity.SkyZombie
import net.minecraft.server.v1_12_R1.EntityEnderDragon
import net.minecraft.server.v1_12_R1.EntityShulker
import net.minecraft.server.v1_12_R1.EntityZombie
import java.util.*

class DefaultCreatorsLoader {
    private val hubs: MutableMap<ConfigPath, CreatorHub?> = Hashtable()
    private fun linkCreators(path: ConfigPath) {
        val keys = instance!!.configProvider!!.getPoolContents(path)
        for (configEntity in keys) {
            val conf =
                instance!!.configProvider!!.getEntity(path, configEntity.id)!!.data
            try {
                val creatorHub = hubs[path]
                val configEntityId = configEntity.id
                val creatorId = conf["creator_id"].toString()
                creatorHub!!.registerRouteId2Creator(configEntityId, creatorId)
            } catch (e: Exception) {
                print(
                    Debug.LEVEL.WARNING,
                    "Error while link creator: " + configEntity.id
                )
                e.printStackTrace()
            }
        }
    }

    // TODO: getCreatorHub(HeldHotbarCreator.class) as HeldHotbarCreator with generics
    fun load() {
        print(
            Debug.LEVEL.NOTICE,
            "###########################################"
        )
        print(
            Debug.LEVEL.NOTICE,
            "########### load code for tests ###########"
        )
        print(
            Debug.LEVEL.NOTICE,
            "#.........................................#"
        )
        // hotbar slots
        try {
            instance!!.hotbarItemCreatorHub!!.registerCreator(ExampleItemCreator())
            instance!!.hotbarItemCreatorHub!!.registerCreator(ExampleGunCreator())
            instance!!.hotbarItemCreatorHub!!.registerCreator(InventoryPageOpenRequesterCreator())
            linkCreators(ConfigPath.HOTBAR_SLOTS)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // hotbar
        try {
            instance!!.hotbarCreatorHub!!.registerCreator(HeldHotbarCreator())
            instance!!.hotbarCreatorHub!!.registerCreator(ItemInterractHotbarCreator())
            linkCreators(ConfigPath.HOTBAR)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // scenario chain
        try {
            instance!!.scenarioChainCreatorHub!!.registerCreator(DefaultScenarioChainCreator())
            linkCreators(ConfigPath.SCENARIO_CHAIN)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // scenario
        try {
            instance!!.scenarioCreatorHub!!.registerCreator(ExampleScenarioCreator())
            instance!!.scenarioCreatorHub!!.registerCreator(ExampleTwoScenarioCreator())
            linkCreators(ConfigPath.SCENARIO)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // Entity controllers
        instance!!.entityAPI!!.register(
            "test",
            69,
            EntityShulker::class.java,
            SilentShulker::class.java
        )
        instance!!.entityAPI!!.register(
            "my_zombie",
            54,
            EntityZombie::class.java,
            SkyZombie::class.java
        )
        instance!!.entityAPI!!.register(
            "sky_dragon",
            63,
            EntityEnderDragon::class.java,
            SkyDragon::class.java
        )
        // reaction
        try {
            instance!!.reactionCreatorHub!!.registerCreator(TeamIncrementWinTicketsReactionCreator())
            instance!!.reactionCreatorHub!!.registerCreator(ItemGiveReactionCreator())
            linkCreators(ConfigPath.ACTIVE_POINT_REACTIONS)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // behavior
        try {
            instance!!.behaviorCreatorHub!!.registerCreator(ExampleBehaviorCreator())
            linkCreators(ConfigPath.ACTIVE_POINT_BEHAVIORS)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // active_point
        try {
            instance!!.activePointsCreatorHub!!.registerCreator(PrimitiveEntityCreator())
            instance!!.activePointsCreatorHub!!.registerCreator(PrimitivePointCreator())
            linkCreators(ConfigPath.ACTIVE_POINT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // team
        try {
            instance!!.teamCreatorHub!!.registerCreator(ExampleTeamCreator())
            linkCreators(ConfigPath.TEAMS)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // lobby
        try {
            instance!!.arenaLobbyCreatorHub!!.registerCreator(StarterLobbyCreator())
            instance!!.arenaLobbyCreatorHub!!.registerCreator(ExampleRespawnLobbyCreator())
            instance!!.arenaLobbyCreatorHub!!.registerCreator(ExampleSpectatorLobbyCreator())
            linkCreators(ConfigPath.ARENA_LOBBY)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // inventory gui
        try {
            instance!!.inventoryGUICreatorHub!!.registerCreator(DefaultViewInventoryCreator())
            linkCreators(ConfigPath.INVENTORY_GUI)
            instance!!.inventoryGuiSlotCreatorHub!!.registerCreator(ExampleInventorySlotCreator())
            linkCreators(ConfigPath.INVENTORY_GUI_SLOT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // GUIProvider
        try {
            instance!!.guiCreatorHub!!.registerCreator(BossbarExampleCreator())
            instance!!.guiCreatorHub!!.registerCreator(ScoreboardExampleCreator())
            instance!!.guiCreatorHub!!.registerRouteId2Creator(
                "BossbarExample",
                "bossbar_example"
            )
            instance!!.guiCreatorHub!!.registerRouteId2Creator(
                "ScoreboardExample",
                "scoreboard_example"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // arena
        try {
            instance!!.arenaCreatorHub!!.registerCreator(ExampleArenaCreator())
            linkCreators(ConfigPath.ARENA_FOLDER)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // server lobby
        try {
            instance!!.lobbyCreatorHub!!.registerCreator(ExampleLobbyCreator())
            linkCreators(ConfigPath.SERVER_LOBBY)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // conditions
        try {
            instance!!.conditionsCreatorHub!!.registerCreator(ExampleConditionCreator())
            instance!!.conditionsCreatorHub!!.registerCreator(EmptyInventoryConditionCreator())
            linkCreators(ConfigPath.CONDITIONS)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // bullets
        try {
            instance!!.bulletCreatorHub!!.registerCreator(GhostBulletCreator())
            linkCreators(ConfigPath.BULLETS)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // ArenaCommandWatcher
        try {
            instance!!.commandWatcherCreatorHub!!.registerCreator(DefaultWatcherCreator())
            instance!!.commandWatcherCreatorHub!!.registerRouteId2Creator(
                "default_command_watcher",
                "default_command_watcher"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    init {
        hubs[ConfigPath.HOTBAR_SLOTS] = instance!!.hotbarItemCreatorHub
        hubs[ConfigPath.HOTBAR] = instance!!.hotbarCreatorHub
        hubs[ConfigPath.SCENARIO_CHAIN] = instance!!.scenarioChainCreatorHub
        hubs[ConfigPath.SCENARIO] = instance!!.scenarioCreatorHub
        hubs[ConfigPath.ACTIVE_POINT_REACTIONS] = instance!!.reactionCreatorHub
        hubs[ConfigPath.ACTIVE_POINT_BEHAVIORS] = instance!!.behaviorCreatorHub
        hubs[ConfigPath.ACTIVE_POINT] = instance!!.activePointsCreatorHub
        hubs[ConfigPath.ARENA_LOBBY] = instance!!.arenaLobbyCreatorHub
        hubs[ConfigPath.INVENTORY_GUI] = instance!!.inventoryGUICreatorHub
        hubs[ConfigPath.INVENTORY_GUI_SLOT] = instance!!.inventoryGuiSlotCreatorHub
        hubs[ConfigPath.ARENA_FOLDER] = instance!!.arenaCreatorHub
        hubs[ConfigPath.SERVER_LOBBY] = instance!!.lobbyCreatorHub
        hubs[ConfigPath.CONDITIONS] = instance!!.conditionsCreatorHub
        hubs[ConfigPath.TEAMS] = instance!!.teamCreatorHub
        hubs[ConfigPath.BULLETS] = instance!!.bulletCreatorHub
    }
}