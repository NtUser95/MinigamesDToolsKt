package com.gmail.borlandlp.minigamesdtools.listener

import com.gmail.borlandlp.minigamesdtools.DefaultCreators
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools
import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase
import com.gmail.borlandlp.minigamesdtools.arena.exceptions.ArenaAlreadyStartedException
import com.gmail.borlandlp.minigamesdtools.config.exception.InvalidPathException
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.Hotbar
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Commands : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        cmd: Command,
        label: String,
        args: Array<String>
    ): Boolean {
        if (cmd.name.equals("arena", ignoreCase = true)) { // debug...
            val player = sender as Player
            if (args[0].equals("lobby", ignoreCase = true)) {
                MinigamesDTools.instance!!.lobbyHubAPI!!.getLobbyByID("spawn_lobby")!!.registerPlayer(player)
            } else if (args[0].equals("lobby_leave", ignoreCase = true)) {
                MinigamesDTools.instance!!.lobbyHubAPI!!.getLobbyByID("spawn_lobby")!!.unregisterPlayer(player)
            } else if (args[0].equals("lobby_transfer", ignoreCase = true)) {
                try {
                    MinigamesDTools.instance!!.lobbyHubAPI!!.getLobbyByID("example_lobby")!!.transferPlayer(
                        player,
                        MinigamesDTools.instance!!.lobbyHubAPI!!.getLobbyByID("example_lobby2")!!
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else if (!player.hasPermission("arena.admin")) {
                player.sendMessage(MinigamesDTools.prefix + " Недостаточно прав для доступа.")
                return true
            } else if (args[0].equals("fullreload", ignoreCase = true)) {
                try {
                    MinigamesDTools.instance!!.reload()
                } catch (e: InvalidPathException) {
                    e.printStackTrace()
                }
                player.sendMessage(MinigamesDTools.prefix + " Произведена полная перезагрузка плагина.")
                return true
            } else if (args[0].equals("enable", ignoreCase = true)) {
                if (args.size == 2) {
                    val arena = MinigamesDTools.instance!!.arenaAPI!!.getArena(args[1])
                    if (arena != null) {
                        arena.isEnabled = true
                        player.sendMessage(MinigamesDTools.prefix + " Арена с названием" + args[1] + " включена.")
                    } else {
                        player.sendMessage(MinigamesDTools.prefix + " Арена с названием" + args[1] + " не найдена.")
                    }
                } else {
                    player.sendMessage(MinigamesDTools.prefix + "/arena enable arena_name")
                }
            } else if (args[0].equals("disable", ignoreCase = true)) {
                if (args.size == 2) {
                    val arena = MinigamesDTools.instance!!.arenaAPI!!.getArena(args[1])
                    if (arena != null) {
                        arena.forceDisable()
                        player.sendMessage(MinigamesDTools.prefix + " Арена с названием" + args[1] + " выключена.")
                    } else {
                        player.sendMessage(MinigamesDTools.prefix + " Арена с названием" + args[1] + " не найдена.")
                    }
                } else {
                    player.sendMessage(MinigamesDTools.prefix + "/arena enable arena_name")
                }
            } else if (args[0].equals("dbg_join", ignoreCase = true) && player.hasPermission("arena.reload")) {
                MinigamesDTools.instance!!.arenaAPI!!.arenaJoinRequest(args[1], player)
                player.sendMessage("dbg_join: " + args[1])
                return true
            } else if (args[0].equals("dbg_state", ignoreCase = true)) {
                val state = ArenaBase.STATE.valueOf(args[2])
                MinigamesDTools.instance!!.arenaAPI!!.getArena(args[1])!!.state = state
                player.sendMessage(
                    "dbg_state:" + args[1] + " " + MinigamesDTools.instance!!.arenaAPI!!.getArena(
                        args[1]
                    )
                )
                return true
            } else if (args[0].equals("dbg_members", ignoreCase = true)) {
                player.sendMessage("===dbg_teams===")
                for (team in MinigamesDTools.instance!!.arenaAPI!!.getArena(args[1])!!.teamController!!.teams) {
                    player.sendMessage("dbg_teams->" + args[1] + ":" + team.name + "#" + team.players)
                }
                return true
            } else if (args[0].equals("dbg_start", ignoreCase = true)) {
                player.sendMessage("===arena_start===")
                try {
                    MinigamesDTools.instance!!.arenaAPI!!.getArena(args[1])!!.startArena()
                } catch (e: ArenaAlreadyStartedException) {
                    e.printStackTrace()
                }
                return true
            } else if (args[0].equals("hotbar", ignoreCase = true)) {
                try {
                    MinigamesDTools.instance!!.creatorsRegistry.get(DefaultCreators.HOTBAR.pseudoName)!!
                        .create("example_skyhotbar",
                            DataProvider()
                        ).apply {
                            if (this is Hotbar) {
                                MinigamesDTools.instance!!.hotbarAPI!!.bindHotbar(this, player)
                            }
                        }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else if (args[0].equals("dbg_stop", ignoreCase = true)) {
                player.sendMessage("===arena_stop===")
                player.sendMessage("stopped" + args[1])
                MinigamesDTools.instance!!.arenaAPI!!.getArena(args[1])!!.forceDisable()
                return true
            } else if (args[0].equals("leave", ignoreCase = true)) {
                MinigamesDTools.instance!!.arenaAPI!!.arenaLeaveRequest(player)
                return true
            } else if (args[0].equals("party_create", ignoreCase = true)) {
                val party = MinigamesDTools.instance!!.partyAPI!!.createParty(player)
                MinigamesDTools.instance!!.partyAPI!!.addParty(party)
                return true
            } else if (args[0].equals("party_join", ignoreCase = true)) {
                MinigamesDTools.instance!!.partyAPI!!.getPartyOf(args[1])!!.addPlayer(player)
                return true
            } else if (args[0].equals("party_members", ignoreCase = true)) {
                print(MinigamesDTools.instance!!.partyAPI!!.getPartyOf(player))
                return true
            } else if (args[0].equals("all_arenas", ignoreCase = true)) {
                player.sendMessage("Arenas list:")
                var msg: String?
                for (arena in MinigamesDTools.instance!!.arenaAPI!!.arenas) {
                    msg = (" -" + ChatColor.GREEN + arena.name
                            + ChatColor.GRAY + " [ID:" + arena.gameId + "]"
                            + ChatColor.GREEN + " state:'" + ChatColor.DARK_GREEN + arena.state + ChatColor.GREEN + "'"
                            + " players:'" + ChatColor.DARK_GREEN + arena.teamController!!.countCurrentPlayers() + ChatColor.GREEN + "'")
                    player.sendMessage(msg)
                }
            }
            player.sendMessage("Wrong command")
            return true
        }
        return false
    }
}