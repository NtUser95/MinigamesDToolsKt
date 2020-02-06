package com.gmail.borlandlp.minigamesdtools.arena.team

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase
import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener
import com.gmail.borlandlp.minigamesdtools.arena.ArenaPhaseComponent
import com.gmail.borlandlp.minigamesdtools.arena.ArenaPlayersRelative
import com.gmail.borlandlp.minigamesdtools.events.ArenaPlayerQuitEvent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

class TeamController(val arena: ArenaBase) : ArenaPhaseComponent {
    val teams = ArrayList<TeamProvider>()
    private var exampleTeamListener: ExampleTeamListener? = null

    fun addTeam(team: TeamProvider) {
        teams.add(team)
    }

    fun removeTeam(team: TeamProvider?) {
        teams.remove(team)
    }

    override fun onInit() {
        exampleTeamListener = ExampleTeamListener(this)
        try {
            arena.eventAnnouncer.register(exampleTeamListener!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        for (team in teams) {
            arena.phaseComponentController.register(team)
        }
    }

    override fun beforeGameStarting() {}
    override fun gameEnded() {
        arena.eventAnnouncer.unregister(exampleTeamListener as ArenaEventListener)
        var event: ArenaPlayerQuitEvent
        for (team in teams) {
            for (player in team.players) { //team.removePlayer(player);
                event = ArenaPlayerQuitEvent(arena, player)
                Bukkit.getPluginManager().callEvent(event)
            }
        }
    }

    override fun update() {}
    override fun beforeRoundStarting() {}
    override fun onRoundEnd() {}
    fun countCurrentPlayers(): Int {
        return teams.stream().mapToInt { t: TeamProvider -> t.players.size }
            .sum()
    }

    fun getTeam(teamName: String?): TeamProvider? {
        return teams.stream()
            .filter { team: TeamProvider? -> team!!.name.equals(teamName) }
            .findFirst()
            .orElse(null)
    }

    fun getTeamOf(nickname: String): TeamProvider? {
        for (team in teams) {
            for (player in team.players) {
                if (player.name == nickname) return team
            }
        }
        return null
    }

    fun getTeamOf(player: Player): TeamProvider? {
        return this.getTeamOf(player.name)
    }

    fun getPlayersRelative(player1: Player, player2: Player): ArenaPlayersRelative {
        val team1 = this.getTeamOf(player1)
        val team2 = this.getTeamOf(player2)
        return if (team1 == null || team2 == null) {
            ArenaPlayersRelative.ENEMY
        } else if (team1 === team2 && !team1.isFriendlyFireAllowed) {
            ArenaPlayersRelative.TEAMMATE
        } else {
            ArenaPlayersRelative.ENEMY
        }
    }

}