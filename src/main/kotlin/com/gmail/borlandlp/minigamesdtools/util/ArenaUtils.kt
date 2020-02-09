package com.gmail.borlandlp.minigamesdtools.util

import net.minecraft.server.v1_12_R1.ChatMessageType
import net.minecraft.server.v1_12_R1.IChatBaseComponent
import net.minecraft.server.v1_12_R1.PacketPlayOutChat
import org.apache.commons.codec.digest.DigestUtils
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.metadata.Metadatable
import java.util.*
import kotlin.math.abs

object ArenaUtils {
    @JvmStatic
    fun generateRandID(): String {
        return DigestUtils.md5Hex((Random().nextFloat() * 100000000).toString() + "")
    }

    //code MCore utils
    @JvmStatic
    fun isNpc(`object`: Any?): Boolean {
        if (`object` !is Metadatable) return false
        return try {
            `object`.hasMetadata("NPC")
        } catch (e: UnsupportedOperationException) { // ProtocolLib
// UnsupportedOperationException: The method hasMetadata is not supported for temporary players.
            false
        }
    }

    @JvmStatic
    fun sendMessage(
        player: Player,
        chatMessageType: ChatMessageType?,
        message: String
    ) {
        val comp = IChatBaseComponent.ChatSerializer
            .a("{\"text\":\"" + ChatColor.RED + message + "\"}")
        val packet = PacketPlayOutChat(comp, chatMessageType)
        (player as CraftPlayer).handle.playerConnection.sendPacket(packet)
    }

    @JvmStatic
    fun getNearestEntity(
        location: Location,
        entityCollection: List<Entity>
    ): Entity {
        val differences: MutableMap<Int, Entity> =
            HashMap()
        for (entity in entityCollection) {
            val diffX =
                abs(abs(location.x) - abs(entity.location.x)).toInt()
            val diffY =
                abs(abs(location.y) - abs(entity.location.y)).toInt()
            val diffZ =
                abs(abs(location.z) - abs(entity.location.z)).toInt()
            differences[diffX + diffY + diffZ] = entity
        }

        return differences.toSortedMap(kotlin.Comparator { o1, o2 -> o1 - o2 }).values.first()
    }

    @JvmStatic
    fun str2Loc(str: Array<String>): Location {
        val x = str[0].toInt().toDouble()
        val y = str[1].toInt().toDouble()
        val z = str[2].toInt().toDouble()
        val world = Bukkit.getWorld(str[3])
        val loc = Location(world, x, y, z)
        if (str.size == 5) {
            loc.yaw = str[4].toInt().toFloat()
        }
        if (str.size == 6) {
            loc.yaw = str[5].toInt().toFloat()
        }
        return loc
    }

    @JvmStatic
    fun loc2Str(loc: Location): Array<String> {
        val strs: MutableList<String> = ArrayList()
        strs.add(loc.blockX.toString() + "")
        strs.add(loc.blockY.toString() + "")
        strs.add(loc.blockZ.toString() + "")
        strs.add(loc.world.name)
        strs.add(loc.yaw.toString() + "")
        strs.add(loc.pitch.toString() + "")
        return strs.toTypedArray()
    }
}