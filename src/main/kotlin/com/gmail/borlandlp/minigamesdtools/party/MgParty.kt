package com.gmail.borlandlp.minigamesdtools.party

import com.gmail.borlandlp.minigamesdtools.party.event.PartyChangeLeaderEvent
import com.gmail.borlandlp.minigamesdtools.party.event.PartyPlayerJoinedEvent
import com.gmail.borlandlp.minigamesdtools.party.event.PartyPlayerKickedEvent
import com.gmail.borlandlp.minigamesdtools.party.event.PartyPlayerLeaveEvent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*
import java.util.stream.Collectors

class MgParty(leader: Player) : Party {
    private val members: MutableMap<String, Player> = HashMap()
    override var leader: Player = leader
        set(value) {
            if (members.containsKey(value.name)) {
                Bukkit.getPluginManager().callEvent(PartyChangeLeaderEvent(this, leader, value))
                field = value
            }
        }
    override var maxSize = 2
    override fun addPlayer(player: Player) {
        members[player.name] = player
        Bukkit.getPluginManager().callEvent(PartyPlayerJoinedEvent(this, player))
    }
    override val players: MutableList<Player>
        get() = ArrayList(members.values)
    override fun removePlayer(player: Player) {
        if (members.remove(player.name) != null) {
            Bukkit.getPluginManager().callEvent(PartyPlayerLeaveEvent(this, player))
        }
    }

    override fun broadcast(message: String) {
        for (player in this.players) {
            player.sendMessage(message)
        }
    }

    override fun kickPlayer(player: Player) {
        if (!members.containsKey(player.name)) {
            return
        }
        members.remove(player.name)
        Bukkit.getPluginManager().callEvent(PartyPlayerKickedEvent(this, player))
        player.sendMessage("{kicked_from_party_msg}")
    }

    override fun toString(): String {
        val members = members.keys.stream().map<String> { obj: String -> obj }.collect(Collectors.joining("|"))
        return "{Party leader=" + leader.name + ", members=" + members + ", maxSize=" + maxSize + "}"
    }

    init {
        members[leader.name] = leader
    }
}