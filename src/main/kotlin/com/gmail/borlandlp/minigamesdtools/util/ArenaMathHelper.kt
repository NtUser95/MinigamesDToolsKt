package com.gmail.borlandlp.minigamesdtools.util

import net.minecraft.server.v1_12_R1.Vec3D
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import kotlin.math.acos
import kotlin.math.sqrt

object ArenaMathHelper {
    @JvmStatic
    fun degreesBetweenPlayerAndLocation(player: Player, targetLoc: Location): Double {
        val reqVec = getVec2Location(player.location, targetLoc)
        return degreesBetweenTwoVectors_XZ(reqVec, player.location.direction)
    }

    fun degreesBetweenTwoVectors_XZ(vec1: Vec3D, vec2: Vec3D): Double {
        return Math.toDegrees(getAngle(vec1.x, vec1.z, vec2.x, vec2.z))
    }

    fun degreesBetweenTwoVectors_XZ(vec1: Vec3D, vec2: Vector): Double {
        return Math.toDegrees(getAngle(vec1.x, vec1.z, vec2.x, vec2.z))
    }

    fun degreesBetweenTwoVectors_XZ(vec1: Vector, vec2: Vector): Double {
        return Math.toDegrees(getAngle(vec1.x, vec1.z, vec2.x, vec2.z))
    }

    fun degreesBetweenTwoVectors_XZ(vec1: Vector, vec2: Vec3D): Double {
        return Math.toDegrees(getAngle(vec1.x, vec1.z, vec2.x, vec2.z))
    }

    fun getAngle(x1: Double, y1: Double, x2: Double, y2: Double): Double {
        var t = (x1 * x2 + y1 * y2) / (sqrt(x1 * x1 + y1 * y1) * sqrt(x2 * x2 + y2 * y2))
        if (t < -1) {
            t = -1.0
        } else if (t > 1) {
            t = 1.0
        }
        return acos(t)
    }

    fun getVec2Location(from: Location, to: Location): Vec3D {
        val x = to.x - from.x
        val y = to.y - from.y
        val z = to.z - from.z
        return Vec3D(x, y, z)
    }

    fun getInterpositionOfPoint(
        fromLoc: Location,
        toLoc: Location,
        point: Location
    ): Interposition {
        return getInterpositionOfPoint(
            fromLoc.x,
            fromLoc.z,
            toLoc.x,
            toLoc.z,
            point.x,
            point.z
        )
    }

    // TODO: Проверить корректность отображения для верхнестоящих и нижестоящих целей
    fun getInterpositionOfPoint(
        fromX: Double,
        fromZ: Double,
        toX: Double,
        toZ: Double,
        pointX: Double,
        pointZ: Double
    ): Interposition { //(х3 - х1) * (у2 - у1) - (у3 - у1) * (х2 - х1)
        val d = (pointX - fromX) * (toZ - fromZ) - (pointZ - fromZ) * (toX - fromX)
        return when {
            d == 0.0 -> {
                Interposition.UPSIDE
            }
            d > 0 -> {
                Interposition.LEFTSIDE
            }
            else -> {
                Interposition.RIGHTSIDE
            }
        }
    }

    enum class Interposition {
        UPSIDE, RIGHTSIDE, LEFTSIDE
    }
}