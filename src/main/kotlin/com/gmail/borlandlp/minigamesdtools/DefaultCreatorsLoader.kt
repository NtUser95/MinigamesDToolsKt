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
    private val hubs: MutableMap<ConfigPath, CreatorHub> = Hashtable()

    private fun linkCreators(path: ConfigPath) {
        instance!!.configProvider!!.getPoolContents(path).forEach { configEntity ->
            val conf = instance!!.configProvider!!.getEntity(path, configEntity.id)!!.data
            try {
                val creatorHub = hubs[path]
                val configEntityId = configEntity.id
                val creatorId = conf["creator_id"].toString()
                creatorHub!!.registerRouteId2Creator(configEntityId, creatorId)
            } catch (e: Exception) {
                print( Debug.LEVEL.WARNING, "Error while link creator: " + configEntity.id)
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
            instance!!.creatorsRegistry.get(DefaultCreators.HOTBAR_ITEM.pseudoName)!!.registerCreator(ExampleItemCreator())
            instance!!.creatorsRegistry.get(DefaultCreators.HOTBAR_ITEM.pseudoName)!!.registerCreator(ExampleGunCreator())
            instance!!.creatorsRegistry.get(DefaultCreators.HOTBAR_ITEM.pseudoName)!!.registerCreator(InventoryPageOpenRequesterCreator())
            linkCreators(ConfigPath.HOTBAR_SLOTS)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // hotbar
        try {
            instance!!.creatorsRegistry.get(DefaultCreators.HOTBAR.pseudoName)!!.registerCreator(HeldHotbarCreator())
            instance!!.creatorsRegistry.get(DefaultCreators.HOTBAR.pseudoName)!!.registerCreator(ItemInterractHotbarCreator())
            linkCreators(ConfigPath.HOTBAR)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // scenario chain
        try {
            instance!!.creatorsRegistry.get(DefaultCreators.SCENARIO_CHAIN.pseudoName)!!.registerCreator(DefaultScenarioChainCreator())
            linkCreators(ConfigPath.SCENARIO_CHAIN)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // scenario
        try {
            instance!!.creatorsRegistry.get(DefaultCreators.SCENARIO.pseudoName)!!.registerCreator(ExampleScenarioCreator())
            instance!!.creatorsRegistry.get(DefaultCreators.SCENARIO.pseudoName)!!.registerCreator(ExampleTwoScenarioCreator())
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
            instance!!.creatorsRegistry.get(DefaultCreators.REACTION.pseudoName)!!.registerCreator(TeamIncrementWinTicketsReactionCreator())
            instance!!.creatorsRegistry.get(DefaultCreators.REACTION.pseudoName)!!.registerCreator(ItemGiveReactionCreator())
            linkCreators(ConfigPath.ACTIVE_POINT_REACTIONS)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // behavior
        try {
            instance!!.creatorsRegistry.get(DefaultCreators.BEHAVIOR.pseudoName)!!.registerCreator(ExampleBehaviorCreator())
            linkCreators(ConfigPath.ACTIVE_POINT_BEHAVIORS)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // active_point
        try {
            instance!!.creatorsRegistry.get(DefaultCreators.ACTIVE_POINTS.pseudoName)!!.registerCreator(PrimitiveEntityCreator())
            instance!!.creatorsRegistry.get(DefaultCreators.ACTIVE_POINTS.pseudoName)!!.registerCreator(PrimitivePointCreator())
            linkCreators(ConfigPath.ACTIVE_POINT)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // team
        try {
            instance!!.creatorsRegistry.get(DefaultCreators.TEAM.pseudoName)!!.registerCreator(ExampleTeamCreator())
            linkCreators(ConfigPath.TEAMS)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // lobby
        try {
            instance!!.creatorsRegistry.get(DefaultCreators.ARENA_LOBBY.pseudoName)!!.registerCreator(StarterLobbyCreator())
            instance!!.creatorsRegistry.get(DefaultCreators.ARENA_LOBBY.pseudoName)!!.registerCreator(ExampleRespawnLobbyCreator())
            instance!!.creatorsRegistry.get(DefaultCreators.ARENA_LOBBY.pseudoName)!!.registerCreator(ExampleSpectatorLobbyCreator())
            linkCreators(ConfigPath.ARENA_LOBBY)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // inventory gui
        try {
            instance!!.creatorsRegistry.get(DefaultCreators.INVENTORY_GUI.pseudoName)!!.registerCreator(DefaultViewInventoryCreator())
            linkCreators(ConfigPath.INVENTORY_GUI)
            instance!!.creatorsRegistry.get(DefaultCreators.INVENTORY_GUI_SLOT.pseudoName)!!.registerCreator(ExampleInventorySlotCreator())
            linkCreators(ConfigPath.INVENTORY_GUI_SLOT)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // GUIProvider
        try {
            instance!!.creatorsRegistry.get(DefaultCreators.DISPLAY_GUI.pseudoName)!!.registerCreator(BossbarExampleCreator())
            instance!!.creatorsRegistry.get(DefaultCreators.DISPLAY_GUI.pseudoName)!!.registerCreator(ScoreboardExampleCreator())
            instance!!.creatorsRegistry.get(DefaultCreators.DISPLAY_GUI.pseudoName)!!.registerRouteId2Creator(
                "BossbarExample",
                "bossbar_example"
            )
            instance!!.creatorsRegistry.get(DefaultCreators.DISPLAY_GUI.pseudoName)!!.registerRouteId2Creator(
                "ScoreboardExample",
                "scoreboard_example"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // arena
        try {
            instance!!.creatorsRegistry.get(DefaultCreators.ARENA.pseudoName)!!.registerCreator(ExampleArenaCreator())
            linkCreators(ConfigPath.ARENA_FOLDER)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // server lobby
        try {
            instance!!.creatorsRegistry.get(DefaultCreators.SERVER_LOBBY.pseudoName)!!.registerCreator(ExampleLobbyCreator())
            linkCreators(ConfigPath.SERVER_LOBBY)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // conditions
        try {
            instance!!.creatorsRegistry.get(DefaultCreators.CONDITION.pseudoName)!!.registerCreator(ExampleConditionCreator())
            instance!!.creatorsRegistry.get(DefaultCreators.CONDITION.pseudoName)!!.registerCreator(EmptyInventoryConditionCreator())
            linkCreators(ConfigPath.CONDITIONS)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // bullets
        try {
            instance!!.creatorsRegistry.get(DefaultCreators.BULLET.pseudoName)!!.registerCreator(GhostBulletCreator())
            linkCreators(ConfigPath.BULLETS)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // ArenaCommandWatcher
        try {
            instance!!.creatorsRegistry.get(DefaultCreators.COMMAND_WATCHER.pseudoName)!!.registerCreator(DefaultWatcherCreator())
            instance!!.creatorsRegistry.get(DefaultCreators.COMMAND_WATCHER.pseudoName)!!.registerRouteId2Creator(
                "default_command_watcher",
                "default_command_watcher"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    init {
        hubs[ConfigPath.HOTBAR_SLOTS] = instance!!.creatorsRegistry.get(DefaultCreators.HOTBAR_ITEM.pseudoName) as CreatorHub
        hubs[ConfigPath.HOTBAR] = instance!!.creatorsRegistry.get(DefaultCreators.HOTBAR.pseudoName) as CreatorHub
        hubs[ConfigPath.SCENARIO_CHAIN] = instance!!.creatorsRegistry.get(DefaultCreators.SCENARIO_CHAIN.pseudoName) as CreatorHub
        hubs[ConfigPath.SCENARIO] = instance!!.creatorsRegistry.get(DefaultCreators.SCENARIO.pseudoName) as CreatorHub
        hubs[ConfigPath.ACTIVE_POINT_REACTIONS] = instance!!.creatorsRegistry.get(DefaultCreators.REACTION.pseudoName) as CreatorHub
        hubs[ConfigPath.ACTIVE_POINT_BEHAVIORS] = instance!!.creatorsRegistry.get(DefaultCreators.BEHAVIOR.pseudoName) as CreatorHub
        hubs[ConfigPath.ACTIVE_POINT] = instance!!.creatorsRegistry.get(DefaultCreators.ACTIVE_POINTS.pseudoName) as CreatorHub
        hubs[ConfigPath.ARENA_LOBBY] = instance!!.creatorsRegistry.get(DefaultCreators.ARENA_LOBBY.pseudoName) as CreatorHub
        hubs[ConfigPath.INVENTORY_GUI] = instance!!.creatorsRegistry.get(DefaultCreators.INVENTORY_GUI.pseudoName) as CreatorHub
        hubs[ConfigPath.INVENTORY_GUI_SLOT] = instance!!.creatorsRegistry.get(DefaultCreators.INVENTORY_GUI_SLOT.pseudoName) as CreatorHub
        hubs[ConfigPath.ARENA_FOLDER] = instance!!.creatorsRegistry.get(DefaultCreators.ARENA.pseudoName) as CreatorHub
        hubs[ConfigPath.SERVER_LOBBY] = instance!!.creatorsRegistry.get(DefaultCreators.SERVER_LOBBY.pseudoName) as CreatorHub
        hubs[ConfigPath.CONDITIONS] = instance!!.creatorsRegistry.get(DefaultCreators.CONDITION.pseudoName) as CreatorHub
        hubs[ConfigPath.TEAMS] = instance!!.creatorsRegistry.get(DefaultCreators.TEAM.pseudoName) as CreatorHub
        hubs[ConfigPath.BULLETS] = instance!!.creatorsRegistry.get(DefaultCreators.BULLET.pseudoName) as CreatorHub
    }
}