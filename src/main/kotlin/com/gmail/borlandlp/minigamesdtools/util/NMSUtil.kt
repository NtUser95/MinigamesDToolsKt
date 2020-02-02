package com.gmail.borlandlp.minigamesdtools.util

import net.minecraft.server.v1_12_R1.BiomeBase
import net.minecraft.server.v1_12_R1.BiomeBase.BiomeMeta
import net.minecraft.server.v1_12_R1.Entity
import net.minecraft.server.v1_12_R1.EntityTypes
import net.minecraft.server.v1_12_R1.MinecraftKey
import java.util.*

/*
* ToDo: Отказаться от рефлексии для использования кастомного поведения для энтити?
* */
object NMSUtil {
    @JvmStatic
    fun registerEntity(
        name: String?, id: Int, nmsClass: Class<out Entity?>,
        customClass: Class<out Entity?>?
    ) {
        try {
            val dataMaps: MutableList<Map<*, *>> =
                ArrayList()
            for (f in EntityTypes::class.java.declaredFields) {
                if (f.type == MutableMap::class.java) {
                    f.isAccessible = true
                    dataMaps.add(f[null] as Map<*, *>)
                }
            }
            EntityTypes.b.a(id, MinecraftKey(name), customClass)
            for (f in BiomeBase::class.java.declaredFields) {
                if (f.type == BiomeBase::class.java) {
                    if (f[null] != null) {
                        for (list in BiomeBase::class.java.declaredFields) {
                            if (list.type == MutableList::class.java) {
                                list.isAccessible = true
                                val metaList =
                                    list[f[null]] as List<BiomeMeta>
                                for (meta in metaList) {
                                    val clazz =
                                        BiomeMeta::class.java.declaredFields[0]
                                    if (clazz[meta] == nmsClass) clazz[meta] = customClass
                                }
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun unregisterEntity(
        name: String?, id: Int, nmsClass: Class<out Entity?>?,
        customClass: Class<out Entity?>
    ) {
        try {
            val dataMaps: MutableList<Map<*, *>> =
                ArrayList()
            for (f in EntityTypes::class.java.declaredFields) { /*
                * Viva64 report
                * https://www.viva64.com/ru/w/v6054/
                * */
//if (f.getType().getSimpleName().equals(Map.class.getSimpleName())) {
                if (f.type == MutableMap::class.java) {
                    f.isAccessible = true
                    dataMaps.add(f[null] as Map<*, *>)
                }
            }
            EntityTypes.b.a(id, MinecraftKey(name), customClass)
            for (f in BiomeBase::class.java.declaredFields) {
                if (f.type == BiomeBase::class.java) {
                    if (f[null] != null) {
                        for (list in BiomeBase::class.java.declaredFields) {
                            if (list.type == MutableList::class.java) {
                                list.isAccessible = true
                                val metaList =
                                    list[f[null]] as List<BiomeMeta>
                                for (meta in metaList) {
                                    val clazz =
                                        BiomeMeta::class.java.declaredFields[0]
                                    if (clazz[meta] == customClass) clazz[meta] = nmsClass
                                }
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}