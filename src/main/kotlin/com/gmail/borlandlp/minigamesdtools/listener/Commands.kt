package com.gmail.borlandlp.minigamesdtools.listener

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools
import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase
import com.gmail.borlandlp.minigamesdtools.arena.exceptions.ArenaAlreadyStartedException
import com.gmail.borlandlp.minigamesdtools.config.exception.InvalidPathException
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider
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
                MinigamesDTools.getInstance().lobbyHubAPI.getLobbyByID("spawn_lobby")!!.registerPlayer(player)
            } else if (args[0].equals("lobby_leave", ignoreCase = true)) {
                MinigamesDTools.getInstance().lobbyHubAPI.getLobbyByID("spawn_lobby")!!.unregisterPlayer(player)
            } else if (args[0].equals("lobby_transfer", ignoreCase = true)) {
                try {
                    MinigamesDTools.getInstance().lobbyHubAPI.getLobbyByID("example_lobby")!!.transferPlayer(
                        player,
                        MinigamesDTools.getInstance().lobbyHubAPI.getLobbyByID("example_lobby2")!!
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else if (!player.hasPermission("arena.admin")) {
                player.sendMessage(MinigamesDTools.getPrefix() + " Недостаточно прав для доступа.")
                return true
            } else if (args[0].equals("fullreload", ignoreCase = true)) {
                try {
                    MinigamesDTools.getInstance().reload()
                } catch (e: InvalidPathException) {
                    e.printStackTrace()
                }
                player.sendMessage(MinigamesDTools.getPrefix() + " Произведена полная перезагрузка плагина.")
                return true
            } else if (args[0].equals("enable", ignoreCase = true)) {
                if (args.size == 2) {
                    val arena = MinigamesDTools.getInstance().arenaAPI.getArena(args[1])
                    if (arena != null) {
                        arena.isEnabled = true
                        player.sendMessage(MinigamesDTools.getPrefix() + " Арена с названием" + args[1] + " включена.")
                    } else {
                        player.sendMessage(MinigamesDTools.getPrefix() + " Арена с названием" + args[1] + " не найдена.")
                    }
                } else {
                    player.sendMessage(MinigamesDTools.getPrefix() + "/arena enable arena_name")
                }
            } else if (args[0].equals("disable", ignoreCase = true)) {
                if (args.size == 2) {
                    val arena = MinigamesDTools.getInstance().arenaAPI.getArena(args[1])
                    if (arena != null) {
                        arena.forceDisable()
                        player.sendMessage(MinigamesDTools.getPrefix() + " Арена с названием" + args[1] + " выключена.")
                    } else {
                        player.sendMessage(MinigamesDTools.getPrefix() + " Арена с названием" + args[1] + " не найдена.")
                    }
                } else {
                    player.sendMessage(MinigamesDTools.getPrefix() + "/arena enable arena_name")
                }
            } else if (args[0].equals("dbg_join", ignoreCase = true) && player.hasPermission("arena.reload")) {
                MinigamesDTools.getInstance().arenaAPI.arenaJoinRequest(args[1], player)
                player.sendMessage("dbg_join: " + args[1])
                return true
            } else if (args[0].equals("dbg_state", ignoreCase = true)) {
                val state = ArenaBase.STATE.valueOf(args[2])
                MinigamesDTools.getInstance().arenaAPI.getArena(args[1]).state = state
                player.sendMessage(
                    "dbg_state:" + args[1] + " " + MinigamesDTools.getInstance().arenaAPI.getArena(
                        args[1]
                    )
                )
                return true
            } else if (args[0].equals("dbg_members", ignoreCase = true)) {
                player.sendMessage("===dbg_teams===")
                for (team in MinigamesDTools.getInstance().arenaAPI.getArena(args[1]).teamController.teams) {
                    player.sendMessage("dbg_teams->" + args[1] + ":" + team.name + "#" + team.players)
                }
                return true
            } else if (args[0].equals("dbg_start", ignoreCase = true)) {
                player.sendMessage("===arena_start===")
                try {
                    MinigamesDTools.getInstance().arenaAPI.getArena(args[1]).startArena()
                } catch (e: ArenaAlreadyStartedException) {
                    e.printStackTrace()
                }
                return true
            } else if (args[0].equals("hotbar", ignoreCase = true)) {
                try {
                    val hotbar = MinigamesDTools.getInstance().hotbarCreatorHub
                        .createHotbar("example_skyhotbar", DataProvider())
                    MinigamesDTools.getInstance().hotbarAPI.bindHotbar(hotbar, player)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else if (args[0].equals("dbg_stop", ignoreCase = true)) {
                player.sendMessage("===arena_stop===")
                player.sendMessage("stopped" + args[1])
                MinigamesDTools.getInstance().arenaAPI.getArena(args[1]).forceDisable()
                return true
            } else if (args[0].equals("leave", ignoreCase = true)) {
                MinigamesDTools.getInstance().arenaAPI.arenaLeaveRequest(player)
                return true
            } else if (args[0].equals("party_create", ignoreCase = true)) {
                val party = MinigamesDTools.getInstance().partyAPI.createParty(player)
                MinigamesDTools.getInstance().partyAPI.addParty(party)
                return true
            } else if (args[0].equals("party_join", ignoreCase = true)) {
                MinigamesDTools.getInstance().partyAPI.getPartyOf(args[1])!!.addPlayer(player)
                return true
            } else if (args[0].equals("party_members", ignoreCase = true)) {
                print(MinigamesDTools.getInstance().partyAPI.getPartyOf(player))
                return true
            } else if (args[0].equals("all_arenas", ignoreCase = true)) {
                player.sendMessage("Arenas list:")
                var msg: String? = null
                for (arena in MinigamesDTools.getInstance().arenaAPI.arenas) {
                    msg = (" -" + ChatColor.GREEN + arena.name
                            + ChatColor.GRAY + " [ID:" + arena.gameId + "]"
                            + ChatColor.GREEN + " state:'" + ChatColor.DARK_GREEN + arena.state + ChatColor.GREEN + "'"
                            + " players:'" + ChatColor.DARK_GREEN + arena.teamController.countCurrentPlayers() + ChatColor.GREEN + "'")
                    player.sendMessage(msg)
                }
            }
            player.sendMessage("Wrong command")
            return true
        }
        return false
    }
}