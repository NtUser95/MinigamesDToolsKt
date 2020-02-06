package com.gmail.borlandlp.minigamesdtools.arena

import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.Debug.print
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import com.gmail.borlandlp.minigamesdtools.arena.chunkloader.ChunkLoaderController
import com.gmail.borlandlp.minigamesdtools.arena.chunkloader.ChunksLoader
import com.gmail.borlandlp.minigamesdtools.arena.gui.hotbar.HotbarController
import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.GUIController
import com.gmail.borlandlp.minigamesdtools.arena.team.TeamController
import com.gmail.borlandlp.minigamesdtools.conditions.AbstractCondition
import com.gmail.borlandlp.minigamesdtools.conditions.ConditionsChain
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.Creator
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider
import com.gmail.borlandlp.minigamesdtools.util.ArenaUtils.generateRandID
import com.gmail.borlandlp.minigamesdtools.util.Glowing.availableColors
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import java.util.*
import java.util.function.Consumer

@CreatorInfo(creatorId = "default_arena")
class ExampleArenaCreator : Creator() {
    @Throws(Exception::class)
    override fun create(id: String, dataProvider: AbstractDataProvider): ArenaBase {
        val debugPrefix = "[$id] "
        val arenaConfig =
            instance!!.configProvider!!.getEntity(ConfigPath.ARENA_FOLDER, id)!!.data
        print(
            Debug.LEVEL.NOTICE,
            "$debugPrefix started loading..."
        )
        val builder = ArenaBase.newBuilder()
        print(
            Debug.LEVEL.NOTICE,
            "$debugPrefix load chunkLoaders..."
        )
        val chunkLoaderController = ChunkLoaderController()
        chunkLoaderController.arena = builder.arena
        val chunksLoaderMap: MutableMap<String, ChunksLoader> =
            HashMap()
        if (arenaConfig.contains("chunk_loaders")) {
            for (loaderName in arenaConfig.getConfigurationSection("chunk_loaders").getKeys(false)) {
                if (arenaConfig.contains("chunk_loaders.$loaderName.min_point_xz") && arenaConfig.contains("chunk_loaders.$loaderName.max_point_xz")) {
                    val minXZ =
                        arenaConfig["chunk_loaders.$loaderName.min_point_xz"].toString().split(":")
                            .toTypedArray()
                    val maxXZ =
                        arenaConfig["chunk_loaders.$loaderName.max_point_xz"].toString().split(":")
                            .toTypedArray()
                    val world =
                        Bukkit.getWorld(arenaConfig["chunk_loaders.$loaderName.world"].toString())
                    val min_loc =
                        world.getBlockAt(minXZ[0].toInt(), 1, minXZ[1].toInt()).location
                    val max_loc =
                        world.getBlockAt(maxXZ[0].toInt(), 1, maxXZ[1].toInt()).location
                    chunksLoaderMap[loaderName] =
                        instance!!.chunkLoaderCreator!!.buildChunkLoader(world, min_loc, max_loc)
                } else {
                    print(
                        Debug.LEVEL.NOTICE,
                        "ChunkLoaderConfig does not contain a field 'min_point_xz' or 'max_point_xz' -> Skipping it."
                    )
                }
            }
            chunkLoaderController.loaders = chunksLoaderMap
        } else {
            print(
                Debug.LEVEL.NOTICE,
                "ArenaConfig does not contain a field 'ChunkLoaders' -> Skipping it."
            )
        }
        builder.setChunkLoaderController(chunkLoaderController)
        print(
            Debug.LEVEL.NOTICE,
            "$debugPrefix load GUIController..."
        )
        val guiController = GUIController()
        guiController.arena = builder.arena
        for (GUI_ID in arenaConfig.getStringList("gui_provider")) {
            val guiProvider =
                instance!!.guiCreatorHub!!.createGuiProvider(GUI_ID!!, DataProvider())
            guiProvider.arena = builder.arena
            guiController.addProvider(guiProvider)
        }
        builder.setGuiController(guiController)
        print(
            Debug.LEVEL.NOTICE,
            "$debugPrefix load ScenarioChain..."
        )
        val abstractDataProvider: AbstractDataProvider = DataProvider()
        abstractDataProvider["arena_instance"] = builder.arena
        val scenarioChainController =
            instance!!.scenarioChainCreatorHub!!.createChain(
                arenaConfig["scenarios_chain"].toString(),
                abstractDataProvider
            )
        builder.setScenarioChainController(scenarioChainController)
        print(
            Debug.LEVEL.NOTICE,
            "$debugPrefix load teams..."
        )
        val teamController = TeamController(builder.arena)
        for (teamID in arenaConfig.getStringList("teams")) {
            val teamProvider =
                instance!!.teamCreatorHub!!.createTeam(teamID!!, abstractDataProvider)
            teamProvider.arena = builder.arena
            teamController.addTeam(teamProvider)
        }
        builder.setTeamController(teamController)
        //conditions
        print(
            Debug.LEVEL.NOTICE,
            "$debugPrefix load conditions..."
        )
        val conditions: MutableList<AbstractCondition> = ArrayList()
        for (conditionId in arenaConfig.getStringList("join_conditions")) {
            conditions.add(
                instance!!.conditionsCreatorHub!!.createCondition(
                    conditionId!!,
                    abstractDataProvider
                )
            )
        }
        builder.setJoinConditionsChain(ConditionsChain(conditions))
        print(
            Debug.LEVEL.NOTICE,
            "$debugPrefix load hotbar..."
        )
        val hotbarController = HotbarController()
        hotbarController.arena = builder.arena
        if (arenaConfig["interactive_hotbar.enabled"].toString().toBoolean()) {
            val hotbarID = arenaConfig["interactive_hotbar.hotbar_id"].toString()
            hotbarController.defaultHotbarId = hotbarID
            hotbarController.isEnabled = true
        } else {
            print(
                Debug.LEVEL.NOTICE,
                "Hotbar is disabled for Arena $id"
            )
        }
        builder.setHotbarController(hotbarController)
        print(
            Debug.LEVEL.NOTICE,
            debugPrefix + "generate random ID..."
        )
        builder.setGameId(generateRandID().substring(0, 14))
        builder.setName(id)
        // gen colors
        /*
         * GlowAPI contains the last element color "None". We exclude it.
         * */
        if (builder.arena.teamController!!.teams.size > availableColors.size - 1) {
            throw Exception("Max team limit reached! [limit=" + (availableColors.size - 1) + "]")
        }
        val usedColors: MutableList<String> = ArrayList()
        for (team in builder.arena.teamController!!.teams) {
            var color: ChatColor
            var strColor: String
            do {
                val rnd = Random()
                    .nextInt(availableColors.size - 2) // exclude last color GlowAPI.Color -> "None"
                strColor = availableColors[rnd]
            } while (usedColors.contains(strColor))
            usedColors.add(strColor)
            team.color = strColor
        }
        // <GameRules>
        print(Debug.LEVEL.NOTICE, "$debugPrefix load options...")
        val gameRules = GameRules()
        gameRules.roundTime = arenaConfig["roundTime"].toString().toInt()
        gameRules.maxRounds = arenaConfig["maxRounds"].toString().toInt()
        gameRules.playerCanItemDrop = arenaConfig["playerItemDrop"].toString().toBoolean()
        gameRules.playerCanItemPickup = arenaConfig["playerItemPickUp"].toString().toBoolean()
        gameRules.beforeRoundStartingWaitDuration = arenaConfig["countdown.beforeFight.duration"].toString().toInt()
        gameRules.beforeArenaTeleportWaitDuration =
            arenaConfig["countdown.beforeTeleport.duration"].toString().toInt()
        gameRules.beforeFightDisableMoving = arenaConfig["countdown.beforeFight.disableMoving"].toString().toBoolean()
        gameRules.hungerDisable = arenaConfig["playerHungerDisable"].toString().toBoolean()
        gameRules.minPlayersToStart = arenaConfig["min_players_to_start"].toString().toInt()
        if (arenaConfig.contains("regain_health")) {
            gameRules.playerCanRegainHealth =
                arenaConfig["regain_health"].toString().equals("true", ignoreCase = true)
        }
        builder.setGameRules(gameRules)
        // </GameRules>
        val arenaTemplate = builder.build()
        arenaTemplate.isEnabled = arenaConfig["enabled"].toString().equals("true", ignoreCase = true)
        print(
            Debug.LEVEL.NOTICE,
            debugPrefix + "register phase components..."
        )
        // register
        arenaTemplate.phaseComponentController.register(arenaTemplate.chunkLoaderController as ArenaPhaseComponent)
        arenaTemplate.phaseComponentController.register(arenaTemplate.teamController as ArenaPhaseComponent)
        arenaTemplate.phaseComponentController.register(arenaTemplate.scenarioChainController as ArenaPhaseComponent)
        arenaTemplate.phaseComponentController.register(arenaTemplate.guiController as ArenaPhaseComponent)
        arenaTemplate.phaseComponentController.register(arenaTemplate.hotbarController as ArenaPhaseComponent)
        arenaTemplate.phaseComponentController.register(arenaTemplate.handlersController)
        print(
            Debug.LEVEL.NOTICE,
            debugPrefix + "try init command watcher..."
        )
        // ArenaCommandWatcher
        if (arenaConfig.contains("commands")) {
            val cDataProvider: AbstractDataProvider = DataProvider()
            cDataProvider["arena_instance"] = arenaTemplate
            val blacklisted: MutableList<Array<String>> =
                ArrayList()
            arenaConfig.getStringList("commands.blacklist")
                .forEach(Consumer { e: String -> blacklisted.add(e.split(" ").toTypedArray()) })
            cDataProvider["blacklist_rules"] = blacklisted
            val whitelisted: MutableList<Array<String>> =
                ArrayList()
            arenaConfig.getStringList("commands.whitelist")
                .forEach(Consumer { e: String -> whitelisted.add(e.split(" ").toTypedArray()) })
            cDataProvider["whitelist_rules"] = whitelisted
            val handlerID = arenaConfig["commands.handler"].toString()
            val creatorHub = instance!!.commandWatcherCreatorHub
            val watcher = creatorHub!!.createCommandWatcher(handlerID, cDataProvider)
            arenaTemplate.phaseComponentController.register(watcher)
        }
        print(
            Debug.LEVEL.NOTICE,
            debugPrefix + "loading is done!"
        )
        return arenaTemplate
    }
}