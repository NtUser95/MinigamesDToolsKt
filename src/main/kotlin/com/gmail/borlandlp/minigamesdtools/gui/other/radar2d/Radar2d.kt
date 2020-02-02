package com.gmail.borlandlp.minigamesdtools.gui.other.radar2d

import com.gmail.borlandlp.minigamesdtools.util.ArenaMathHelper.Interposition
import com.gmail.borlandlp.minigamesdtools.util.ArenaMathHelper.degreesBetweenPlayerAndLocation
import com.gmail.borlandlp.minigamesdtools.util.ArenaMathHelper.getInterpositionOfPoint
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.block.Block
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import java.util.*

class Radar2d(style: BarStyle, color: BarColor) : Radar {
    private val markers: MutableList<Marker?> =
        ArrayList()
    override var viewer: Player? = null
        set(value) {
            field = value
            bossBar.addPlayer(value)
        }
    override var drawDistance = 30 // blocks
    private val bossBar: BossBar
    var radarSize = 30 // 15 symbols <- 1 middle symbol -> 15 symbols

    override fun draw() {
        val maxDegreesDiff = 90.0
        /*
        * Если размер радара чётный(к примеру - 20), то (middleCell + drawSymbol) спровоцирует выход за границы массива.
        * (middleCell - drawSymbol) = 10 - 10 = 0 // В пределах массива
        * (middleCell + drawSymbol) = 10 + 10 = 20 // Массив заканчивается на 19 индексе, начинается на - 0.
        * Поэтому, к чётным массивам мы прибавляем +1
        * */
        val strs =
            arrayOfNulls<String>(if (radarSize % 2 == 0) radarSize + 1 else radarSize)
        for (marker in markers) {
            val viewerLocation = viewer!!.location
            if (viewerLocation.distance(marker!!.location) > drawDistance) {
                continue
            }
            val hopVec = viewerLocation.clone().direction.multiply(drawDistance)
            val hopLoc = viewerLocation.clone().add(hopVec)
            val interposition =
                getInterpositionOfPoint(viewerLocation, hopLoc, marker.location)
            val currentDegreesDiff =
                degreesBetweenPlayerAndLocation(viewer!!, marker.location)
            val degreesDiffPercent = currentDegreesDiff / (maxDegreesDiff * 2.0) * 100.0
            val drawSymbol = (degreesDiffPercent / 100.0 * radarSize).toInt()
            val middleCell = radarSize / 2
            if (middleCell - drawSymbol < 0) { // out of bound
                continue
            }
            when {
                interposition === Interposition.LEFTSIDE -> {
                    val charPosition = middleCell - drawSymbol
                    strs[charPosition] = marker.color.toString() + "#"
                }
                interposition === Interposition.RIGHTSIDE -> {
                    val charPosition = middleCell + drawSymbol
                    strs[charPosition] = marker.color.toString() + "#"
                }
                interposition === Interposition.UPSIDE -> {
                    strs[middleCell] = ChatColor.RED.toString() + "#"
                }
            }
        }
        val stringBuilder = StringBuilder()
        for (i in 0 until radarSize) {
            if (strs[i] == null) {
                stringBuilder.append(ChatColor.WHITE).append("_")
            } else {
                stringBuilder.append(strs[i])
            }
        }
        bossBar.title = stringBuilder.toString()
    }

    override fun addMarker(marker: Marker) {
        markers.add(marker)
    }

    override fun removeMarker(marker: Marker) {
        markers.remove(marker)
    }

    override fun getCurrentMarker(block: Block): Marker? {
        return markers.stream()
            .filter { m: Marker? ->
                m!!.isOwner(block)
            }.findFirst().orElse(null)
    }

    override fun getCurrentMarker(entity: Entity): Marker? {
        return markers.stream()
            .filter { m: Marker? ->
                m!!.isOwner(entity)
            }.findFirst().orElse(null)
    }

    override fun onUnload() {
        bossBar.removePlayer(viewer)
    }

    override fun onLoad() {}

    init {
        bossBar = Bukkit.createBossBar("Radar2d", color, style)
    }
}