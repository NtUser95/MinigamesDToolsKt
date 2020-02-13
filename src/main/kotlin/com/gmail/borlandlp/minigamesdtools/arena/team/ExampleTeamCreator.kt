package com.gmail.borlandlp.minigamesdtools.arena.team

import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.Debug.print
import com.gmail.borlandlp.minigamesdtools.DefaultCreators
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.ArenaLobby
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.respawn.ExampleRespawnLobby
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.respawn.RespawnLobby
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.spectator.SpectatorLobby
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.starter.StarterLobby
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.Creator
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo
import com.gmail.borlandlp.minigamesdtools.creator.DataProvider
import com.gmail.borlandlp.minigamesdtools.util.ArenaUtils.str2Loc
import com.gmail.borlandlp.minigamesdtools.util.InventoryUtil.getArmourItemStack
import com.gmail.borlandlp.minigamesdtools.util.InventoryUtil.getItemStackOufOfString
import org.bukkit.Location
import org.bukkit.inventory.ItemStack
import java.util.*

@CreatorInfo(creatorId = "default_team")
class ExampleTeamCreator : Creator() {
    @Throws(Exception::class)
    override fun create(id: String, dataProvider: AbstractDataProvider): TeamProvider {
        val fileConfiguration = instance!!.configProvider!!.getEntity(ConfigPath.TEAMS, id)?.data
                ?: throw Exception("cant find config of team[ID:$id]")
        val name = fileConfiguration["name"].toString()
        val team = ExampleTeam(name, dataProvider["arena_instance"] as ArenaBase)
        // players spawnPoint
        val spawnPoints: MutableList<Location> =
            ArrayList()
        for (row in fileConfiguration.getStringList("spawn_points_XYZWorldYawPitch")) {
            val splittedSpawnPoint = row.split(":").toTypedArray()
            if (splittedSpawnPoint.size == 6) {
                spawnPoints.add(str2Loc(splittedSpawnPoint))
            } else {
                throw Exception("Invalid spawnPoint format for '$row'. Correct example -> 74:30:113:world:0:0")
            }
        }
        if (spawnPoints.size > 0) {
            team.spawnPoints = spawnPoints
        } else {
            throw Exception("$id must be have a spawn point!")
        }
        team.maxPlayers = fileConfiguration["max_players"].toString().toInt()
        //spectator spawn point
/*List<Location> specPoints = new ArrayList<>();
        for (String row : fileConfiguration.getStringList("spectator_point_XYZWorldYawPitch")) {
            String[] splittedSpawnPoint = row.split(":");
            if(splittedSpawnPoint.length == 6) {
                double x = Integer.parseInt(splittedSpawnPoint[0]);
                double y = Integer.parseInt(splittedSpawnPoint[1]);
                double z = Integer.parseInt(splittedSpawnPoint[2]);
                World world = Bukkit.getWorld(splittedSpawnPoint[3]);
                float yaw = Integer.parseInt(splittedSpawnPoint[4]);
                float pitch = Integer.parseInt(splittedSpawnPoint[5]);
                specPoints.add(new Location(world, x, y, z, yaw, pitch));
            } else {
                throw new Exception("Invalid spectatorPoint format for '" + row + "'. Correct example -> 74:30:113:world:0:0");
            }
        }
        if(spawnPoints.size() > 0) {
            team.setSpectatorPoint(specPoints);
        } else {
            throw new Exception(teamID + " must be have a spectator point!");
        }*/
//armor filler
        val armor = HashMap<String, ItemStack>()
        val manageArmor = fileConfiguration["armor_filler.enabled"].toString().toBoolean()
        if (manageArmor) {
            val armorKeys =
                fileConfiguration.getConfigurationSection("armor_filler.armor").getKeys(false)
            for (armKey in armorKeys) {
                armor[armKey] = getArmourItemStack(fileConfiguration.getString("armor_filler.armor.$armKey"))
            }
        }
        team.isManageArmor = manageArmor
        team.armor = armor
        //inventory
        val inventory = arrayOfNulls<ItemStack>(64)
        team.isManageInventory = fileConfiguration["inventory_filler.enabled"].toString().toBoolean()
        if (team.isManageInventory) {
            var i = 0
            for (row in fileConfiguration.getStringList("inventory_filler.items")) {
                inventory[i++] = getItemStackOufOfString(row!!)[0] as ItemStack
            }
        }
        team.inventory = inventory.filterNotNull().toMutableList()
        // respawn lobby
        if (fileConfiguration.contains("respawn_lobby.enabled") && fileConfiguration["respawn_lobby.enabled"].toString().toBoolean()) {
            val respawnLobby = instance!!.creatorsRegistry.get(DefaultCreators.ARENA_LOBBY.pseudoName)!!.create(
                fileConfiguration["respawn_lobby.lobby_handler"].toString(),
                DataProvider()
            ).apply {
                if(this is ArenaLobby) {
                    this.teamProvider = team
                    this.isEnabled = true
                    team.respawnLobby = this as RespawnLobby
                }
            }
            print(
                Debug.LEVEL.NOTICE,
                "Build respawn lobby for Team[ID:$id]#$respawnLobby"
            )
        } else {
            val l: ArenaLobby = ExampleRespawnLobby()
            l.isEnabled = false
            team.respawnLobby = l as RespawnLobby
            print(
                Debug.LEVEL.NOTICE,
                "Respawn lobby is disabled for Team[ID:$id]"
            )
        }
        // spectator lobby
        if (!fileConfiguration.contains("spectate_lobby.id")) {
            throw Exception("Arena must be config for spectate_lobby")
        }
        print(Debug.LEVEL.NOTICE, "Build spectator lobby for Team[ID:$id]#${fileConfiguration["spectate_lobby.id"]}")
        instance!!.creatorsRegistry.get(DefaultCreators.ARENA_LOBBY.pseudoName)!!.create(
            fileConfiguration["spectate_lobby.id"].toString(),
            DataProvider()
        ).apply {
            if (this is ArenaLobby) {
                this.teamProvider = team
                this.isEnabled = true
                team.spectatorLobby = this as SpectatorLobby
            }
        }


        // starter lobby
        if (!fileConfiguration.contains("starter_lobby")) {
            throw Exception("Arena must be config for starter_lobby")
        }
        print(Debug.LEVEL.NOTICE, "Build starter lobby for Team[ID:$id]#${fileConfiguration["starter_lobby.lobby_handler"]}")
        instance!!.creatorsRegistry.get(DefaultCreators.ARENA_LOBBY.pseudoName)!!.create(
            fileConfiguration["starter_lobby.lobby_handler"].toString(),
            DataProvider()
        ).apply {
            if (this is ArenaLobby) {
                this.teamProvider = team
                this.isEnabled = fileConfiguration["starter_lobby.enabled"].toString().toBoolean()
                team.starterLobby = this as StarterLobby
            }
        }
        team.isFriendlyFireAllowed = fileConfiguration["friendly_fire"].toString().toBoolean()

        return team
    }
}