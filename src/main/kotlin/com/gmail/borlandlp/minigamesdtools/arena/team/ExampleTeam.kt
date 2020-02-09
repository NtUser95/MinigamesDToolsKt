package com.gmail.borlandlp.minigamesdtools.arena.team

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaPlayerRespawnLocalEvent
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.ArenaLobby
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.respawn.RespawnLobby
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.spectator.SpectatorLobby
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.starter.StarterLobby
import com.gmail.borlandlp.minigamesdtools.util.ArenaUtils.isNpc
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

class ExampleTeam(
    override val name: String,
    override var arena: ArenaBase
) : TeamProvider {
    override var respawnLobby: RespawnLobby? = null
    override var spectatorLobby: SpectatorLobby? = null
    var maxPlayers = 0
    var spawnPoints: List<Location> = mutableListOf()
    var armor: MutableMap<String, ItemStack> = mutableMapOf()
    var inventory: MutableList<ItemStack> = mutableListOf()
    override val players: MutableSet<Player> = mutableSetOf()
    protected var fromTeleport = HashMap<String, Location>()
    var starterLobby: StarterLobby? = null
    protected var lobbyPlayers: MutableMap<Player, ArenaLobby?> = Hashtable()
    override var isManageInventory: Boolean = false
    override var isManageArmor: Boolean = false
    override var color: String? = null
    override var isFriendlyFireAllowed: Boolean = false
    override val spectators: Set<Player>
        get() = spectatorLobby!!.players.toSet()

    fun prepareToFight(player: Player?) {
        if (player == null) {
            return
        }
        if (isManageInventory) {
            player.inventory.clear()
            giveItems(player, inventory.toTypedArray())
        }
        player.gameMode = GameMode.SURVIVAL
        player.health = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).value
        player.foodLevel = 20
        player.fireTicks = 0
        if (player.isInsideVehicle) {
            player.leaveVehicle()
        }
    }

    fun tpUserAtHome(player: Player) {
        val nickname = player.name
        val location = fromTeleport[nickname]
        if (location != null && !isNpc(player)) {
            player.teleport(location)
        } else {
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().consoleSender, "spawn $nickname")
        }
    }

    fun getPlayer(nickname: String): Player? {
        return players.firstOrNull { player: Player -> player.name == nickname }
    }

    override fun spawn(player: Player) {
        if (!player.isDead) {
            var spawnLoc = 0
            if (spawnPoints.size > 1) {
                spawnLoc = Random().nextInt(spawnPoints.size - 1)
            }
            player.teleport(spawnPoints[spawnLoc])
            val arenaPlayerRespawnLocalEvent = ArenaPlayerRespawnLocalEvent(this, player)
            arena.eventAnnouncer.announce(arenaPlayerRespawnLocalEvent)
        }
    }

    private fun giveItems(player: Player, itemstacks: Array<ItemStack>) {
        if (isNpc(player)) {
            return
        }
        val helmet = armor["helmet"]
        val chestPlate = armor["chestplate"]
        val leggings = armor["leggings"]
        val boots = armor["boots"]
        val itemMeta = helmet!!.itemMeta
        itemMeta.displayName = "[arena]"
        helmet.itemMeta = itemMeta
        chestPlate!!.itemMeta = itemMeta
        leggings!!.itemMeta = itemMeta
        boots!!.itemMeta = itemMeta
        player.inventory.helmet = helmet
        player.inventory.chestplate = chestPlate
        player.inventory.leggings = leggings
        player.inventory.boots = boots
        if (itemstacks.isNotEmpty()) {
            val newItemMeta = itemMeta.clone()
            if (newItemMeta.hasEnchants()) {
                newItemMeta.enchants.keys.forEach { enchant ->
                    newItemMeta.removeEnchant(enchant)
                }
            }
            itemstacks.forEach { itemStack ->
                itemStack.itemMeta = newItemMeta
                player.inventory.addItem(itemStack)
            }
        }
    }

    override fun movePlayerTo(lobby: ArenaLobby, p: Player): Boolean {
        if (lobbyPlayers.containsKey(p)) {
            lobbyPlayers[p]!!.forceReleasePlayer(p)
        }
        lobby.addPlayer(p)
        lobbyPlayers[p] = lobby
        return true
    }

    override fun movePlayerTo(team: TeamProvider, p: Player): Boolean {
        if (lobbyPlayers.containsKey(p)) {
            lobbyPlayers[p]!!.forceReleasePlayer(p)
        }
        return if (team.addPlayer(p)) {
            removePlayer(p)
            true
        } else {
            false
        }
    }

    override fun addPlayer(player: Player): Boolean {
        players.add(player)
        return true
    }

    override fun removePlayer(player: Player): Boolean {
        players.remove(player)
        return true
    }

    operator fun contains(player: Player): Boolean {
        return players.contains(player)
    }

    fun containsByName(name: String): Boolean {
        return players.firstOrNull { player ->
            player.name == name
        } == null
    }

    override fun setSpectate(p: Player, trueOrFalse: Boolean) {
        if (trueOrFalse) {
            spectatorLobby!!.addPlayer(p)
        } else {
            spectatorLobby!!.removePlayer(p)
            spawn(p)
        }
    }

    override fun containsFreeSlots(forAmountPlayers: Int): Boolean {
        return players.size + forAmountPlayers <= maxPlayers
    }

    override fun onInit() {
        arena.phaseComponentController.register(respawnLobby!!)
        arena.phaseComponentController.register(spectatorLobby!!)
        arena.phaseComponentController.register(starterLobby!!)
    }

    override fun beforeGameStarting() {}
    override fun gameEnded() {}
    override fun update() {
        if ((respawnLobby as ArenaLobby?)!!.isEnabled) {
            respawnLobby!!.update()
            val respPlayers = respawnLobby!!.readyPlayersToRespawn
            if (respPlayers.isNotEmpty()) {
                for (player in respPlayers) {
                    respawnLobby!!.removePlayer(player)
                    lobbyPlayers.remove(player) // TODO: переделать в нечто осмысленное
                    spawn(player)
                }
            }
        }
    }

    override fun beforeRoundStarting() {
        var respPlayers: Set<Player> = HashSet()
        if ((respawnLobby as ArenaLobby).isEnabled) {
            respPlayers = respawnLobby!!.waitingPlayers.keys
            if (respPlayers.isNotEmpty()) {
                respPlayers.forEach { player ->
                    respawnLobby!!.removePlayer(player)
                    lobbyPlayers.remove(player)
                    spawn(player)
                }
            }
        }
        players.forEach { player ->
            if (!respPlayers.contains(player)) {
                spawn(player)
            }
        }
    }

    override fun onRoundEnd() {
        if (spectatorLobby!!.players.size > 0) {
            for (player in spectatorLobby!!.players) {
                spectatorLobby!!.removePlayer(player)
            }
        }
    }
}