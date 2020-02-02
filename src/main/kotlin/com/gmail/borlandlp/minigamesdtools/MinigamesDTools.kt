package com.gmail.borlandlp.minigamesdtools

import com.gmail.borlandlp.minigamesdtools.activepoints.ActivePointController
import com.gmail.borlandlp.minigamesdtools.activepoints.ActivePointsAPI
import com.gmail.borlandlp.minigamesdtools.activepoints.ActivePointsCreatorHub
import com.gmail.borlandlp.minigamesdtools.activepoints.behaviors.BehaviorCreatorHub
import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.ReactionCreatorHub
import com.gmail.borlandlp.minigamesdtools.arena.ArenaAPI
import com.gmail.borlandlp.minigamesdtools.arena.ArenaCreatorHub
import com.gmail.borlandlp.minigamesdtools.arena.ArenaManager
import com.gmail.borlandlp.minigamesdtools.arena.chunkloader.ChunkLoaderCreator
import com.gmail.borlandlp.minigamesdtools.arena.commands.CommandWatcherCreatorHub
import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.GUICreatorHub
import com.gmail.borlandlp.minigamesdtools.arena.scenario.ScenarioChainCreatorHub
import com.gmail.borlandlp.minigamesdtools.arena.scenario.ScenarioCreatorHub
import com.gmail.borlandlp.minigamesdtools.arena.team.TeamCreatorHub
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.ArenaLobbyCreatorHub
import com.gmail.borlandlp.minigamesdtools.conditions.ConditionsCreatorHub
import com.gmail.borlandlp.minigamesdtools.config.ConfigLoader
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath
import com.gmail.borlandlp.minigamesdtools.config.ConfigProvider
import com.gmail.borlandlp.minigamesdtools.config.ConfigUtils
import com.gmail.borlandlp.minigamesdtools.config.exception.InvalidPathException
import com.gmail.borlandlp.minigamesdtools.events.sponge.*
import com.gmail.borlandlp.minigamesdtools.geoip.GeoIp
import com.gmail.borlandlp.minigamesdtools.geoip.GeoIpApi
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.HotbarCreatorHub
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.api.HotbarAPI
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.api.HotbarApiInst
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.HotbarItemCreatorHub
import com.gmail.borlandlp.minigamesdtools.gui.inventory.InventoryGUICreatorHub
import com.gmail.borlandlp.minigamesdtools.gui.inventory.InventoryListener
import com.gmail.borlandlp.minigamesdtools.gui.inventory.api.InventoryAPI
import com.gmail.borlandlp.minigamesdtools.gui.inventory.api.ViewManager
import com.gmail.borlandlp.minigamesdtools.gui.inventory.slots.InventoryGuiSlotCreatorHub
import com.gmail.borlandlp.minigamesdtools.gun.BulletCreatorHub
import com.gmail.borlandlp.minigamesdtools.gun.BulletHandler
import com.gmail.borlandlp.minigamesdtools.gun.BulletHandlerApi
import com.gmail.borlandlp.minigamesdtools.listener.Commands
import com.gmail.borlandlp.minigamesdtools.listener.Events
import com.gmail.borlandlp.minigamesdtools.lobby.LobbyCreatorHub
import com.gmail.borlandlp.minigamesdtools.lobby.LobbyHubController
import com.gmail.borlandlp.minigamesdtools.lobby.LobbyServerAPI
import com.gmail.borlandlp.minigamesdtools.nmsentities.EntityAPI
import com.gmail.borlandlp.minigamesdtools.nmsentities.EntityController
import com.gmail.borlandlp.minigamesdtools.nmsentities.entity.SilentShulker
import com.gmail.borlandlp.minigamesdtools.nmsentities.entity.SkyDragon
import com.gmail.borlandlp.minigamesdtools.nmsentities.entity.SkyZombie
import com.gmail.borlandlp.minigamesdtools.party.PartyAPI
import com.gmail.borlandlp.minigamesdtools.party.PartyManager
import net.minecraft.server.v1_12_R1.EntityEnderDragon
import net.minecraft.server.v1_12_R1.EntityShulker
import net.minecraft.server.v1_12_R1.EntityZombie
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level

class MinigamesDTools : JavaPlugin() {
    var configLoader: ConfigLoader? = null
        private set
    private var customMinigamesLoader: CustomMinigamesLoader? = null
    var apiComponentsController: APIComponentsController? = null
        private set
    var arenaAPI: ArenaAPI? = null
        private set
    var lobbyHubAPI: LobbyServerAPI? = null
        private set
    var arenaCreatorHub: ArenaCreatorHub? = null
        private set
    // TODO: getCreatorHub(HeldHotbarCreator.class) as HeldHotbarCreator with generics
    var configProvider: ConfigProvider? = null
        private set
    var hotbarAPI: HotbarAPI? = null
        private set
    var inventoryGUI_API: InventoryAPI? = null
        private set
    var activePointsAPI: ActivePointsAPI? = null
        private set
    var entityAPI: EntityAPI? = null
        private set
    var bulletHandlerApi: BulletHandlerApi? = null
        private set
    var partyAPI: PartyAPI? = null
        private set
    var geoIpApi: GeoIpApi? = null
        private set
    var guiCreatorHub: GUICreatorHub? = null
        private set
    var hotbarCreatorHub: HotbarCreatorHub? = null
        private set
    var hotbarItemCreatorHub: HotbarItemCreatorHub? = null
        private set
    var reactionCreatorHub: ReactionCreatorHub? = null
        private set
    var behaviorCreatorHub: BehaviorCreatorHub? = null
        private set
    var activePointsCreatorHub: ActivePointsCreatorHub? = null
        private set
    var scenarioCreatorHub: ScenarioCreatorHub? = null
        private set
    var scenarioChainCreatorHub: ScenarioChainCreatorHub? = null
        private set
    var teamCreatorHub: TeamCreatorHub? = null
        private set
    var inventoryGUICreatorHub: InventoryGUICreatorHub? = null
        private set
    var inventoryGuiSlotCreatorHub: InventoryGuiSlotCreatorHub? = null
        private set
    var lobbyCreatorHub: LobbyCreatorHub? = null
        private set
    var chunkLoaderCreator: ChunkLoaderCreator? = null
        private set
    var arenaLobbyCreatorHub: ArenaLobbyCreatorHub? = null
        private set
    var conditionsCreatorHub: ConditionsCreatorHub? = null
        private set
    var bulletCreatorHub: BulletCreatorHub? = null
        private set
    var commandWatcherCreatorHub: CommandWatcherCreatorHub? = null
        private set

    override fun onEnable() {
        instance = this
        server.pluginManager.registerEvents(Events(), this)
        server.pluginManager.registerEvents(InventoryListener(), this)
        getCommand("arena").executor = Commands()
        try {
            loadMisc()
        } catch (e: InvalidPathException) {
            e.printStackTrace()
        }
    }

    @Throws(InvalidPathException::class)
    fun loadMisc() {
        Debug.print(
            Debug.LEVEL.NOTICE,
            "#######################################################"
        )
        Debug.print(
            Debug.LEVEL.NOTICE,
            "########### MiniGames daemon is starting... ###########"
        )
        Debug.print(
            Debug.LEVEL.NOTICE,
            "#.....................................................#"
        )
        customMinigamesLoader = CustomMinigamesLoader()
        customMinigamesLoader!!.loadAddons()
        ConfigUtils.checkAndRestoreDefaultConfigs()
        configLoader = ConfigLoader()
        configLoader!!.addPath(dataFolder)
        configLoader!!.addPath(ConfigPath.ARENA_FOLDER.path)
        // TODO: getCreatorHub(HeldHotbarCreator.class) as HeldHotbarCreator with generics
        guiCreatorHub = GUICreatorHub()
        hotbarCreatorHub = HotbarCreatorHub()
        hotbarItemCreatorHub = HotbarItemCreatorHub()
        reactionCreatorHub = ReactionCreatorHub()
        behaviorCreatorHub = BehaviorCreatorHub()
        activePointsCreatorHub = ActivePointsCreatorHub()
        scenarioCreatorHub = ScenarioCreatorHub()
        scenarioChainCreatorHub = ScenarioChainCreatorHub()
        arenaCreatorHub = ArenaCreatorHub()
        teamCreatorHub = TeamCreatorHub()
        arenaLobbyCreatorHub = ArenaLobbyCreatorHub()
        chunkLoaderCreator = ChunkLoaderCreator()
        inventoryGuiSlotCreatorHub = InventoryGuiSlotCreatorHub()
        conditionsCreatorHub = ConditionsCreatorHub()
        bulletCreatorHub = BulletCreatorHub()
        commandWatcherCreatorHub = CommandWatcherCreatorHub()
        entityAPI = EntityController()
        inventoryGUICreatorHub = InventoryGUICreatorHub()
        inventoryGUI_API = ViewManager()
        lobbyCreatorHub = LobbyCreatorHub()
        activePointsAPI = ActivePointController()
        arenaAPI = ArenaManager()
        hotbarAPI = HotbarApiInst()
        lobbyHubAPI = LobbyHubController()
        bulletHandlerApi = BulletHandler()
        partyAPI = PartyManager()
        geoIpApi = GeoIp()
        apiComponentsController = APIComponentsController()
        Bukkit.getPluginManager().callEvent(PRE_INITIALIZATION_EVENT())
        // load configs
        configProvider = configLoader!!.load()
        Debug.print(
            Debug.LEVEL.NOTICE,
            "###############################################"
        )
        Debug.print(
            Debug.LEVEL.NOTICE,
            "########### register API components ###########"
        )
        Debug.print(
            Debug.LEVEL.NOTICE,
            "#..............................................#"
        )
        apiComponentsController!!.register(lobbyHubAPI as APIComponent?)
        apiComponentsController!!.register(hotbarAPI as APIComponent?)
        apiComponentsController!!.register(activePointsAPI as APIComponent?)
        apiComponentsController!!.register(entityAPI as APIComponent?)
        apiComponentsController!!.register(inventoryGUI_API as APIComponent?)
        apiComponentsController!!.register(arenaAPI as APIComponent?)
        apiComponentsController!!.register(bulletHandlerApi)
        apiComponentsController!!.register(partyAPI as APIComponent?)
        apiComponentsController!!.register(geoIpApi as APIComponent?)
        DefaultCreatorsLoader().load()
        Bukkit.getPluginManager().callEvent(INITIALIZATION_EVENT())
        Debug.print(
            Debug.LEVEL.NOTICE,
            "##################################################"
        )
        Debug.print(
            Debug.LEVEL.NOTICE,
            "########### prepare daemon to start... ###########"
        )
        Debug.print(
            Debug.LEVEL.NOTICE,
            "#................................................#"
        )
        apiComponentsController!!.announceEvent(APIComponentsController.ComponentEvent.PLUGIN_LOAD)
        Bukkit.getPluginManager().callEvent(POST_INITIALIZATION_EVENT())
        Debug.print(Debug.LEVEL.NOTICE,"###################################################")
        Debug.print(Debug.LEVEL.NOTICE,"########### MiniGames daemon is loaded! ###########")
        Debug.print(Debug.LEVEL.NOTICE,"###################################################")
    }

    override fun onDisable() {
        unloadMisc()
        logger.info("Arena disabled!")
    }

    fun unloadMisc() {
        Debug.print(
            Debug.LEVEL.NOTICE,
            "#############################################################"
        )
        Debug.print(
            Debug.LEVEL.NOTICE,
            "########### MiniGames daemon prepare to unload... ###########"
        )
        Debug.print(
            Debug.LEVEL.NOTICE,
            "#...........................................................#"
        )
        Bukkit.getPluginManager().callEvent(PRE_UNLOAD_EVENT())
        instance!!.entityAPI!!
            .unregister("test", 69, EntityShulker::class.java, SilentShulker::class.java)
        instance!!.entityAPI!!
            .unregister("my_zombie", 54, EntityZombie::class.java, SkyZombie::class.java)
        instance!!.entityAPI!!
            .unregister("sky_dragon", 63, EntityEnderDragon::class.java, SkyDragon::class.java)
        apiComponentsController!!.announceEvent(APIComponentsController.ComponentEvent.PLUGIN_UNLOAD)
        Debug.print(
            Debug.LEVEL.NOTICE,
            "###################################"
        )
        Debug.print(
            Debug.LEVEL.NOTICE,
            "########### unload addons ###########"
        )
        Debug.print(
            Debug.LEVEL.NOTICE,
            "#.................................#"
        )
        Bukkit.getPluginManager().callEvent(UNLOAD_EVENT())
        customMinigamesLoader!!.unloadAddons()
        Debug.print(
            Debug.LEVEL.NOTICE,
            "#######################################################"
        )
        Debug.print(
            Debug.LEVEL.NOTICE,
            "########### MiniGames daemon is unloaded... ###########"
        )
        Debug.print(
            Debug.LEVEL.NOTICE,
            "#.....................................................#"
        )
    }

    @Throws(InvalidPathException::class)
    fun reload() {
        unloadMisc()
        loadMisc()
        logger
            .log(Level.INFO, "$prefix Конфиг арен перезагружен.")
    }

    companion object {
        @JvmStatic
        val prefix =
            ChatColor.BLUE.toString() + "[" + ChatColor.RED + "Arena" + ChatColor.BLUE + "]" + ChatColor.DARK_GREEN
        @JvmStatic
        var instance: MinigamesDTools? = null
            private set

    }
}