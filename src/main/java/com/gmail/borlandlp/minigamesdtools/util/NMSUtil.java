package com.gmail.borlandlp.minigamesdtools.util;

import net.minecraft.server.v1_12_R1.BiomeBase;
import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityTypes;
import net.minecraft.server.v1_12_R1.MinecraftKey;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
* ToDo: Отказаться от рефлексии для использования кастомного поведения для энтити?
* */
public final class NMSUtil {
    public static void registerEntity(final String name, final int id, final Class<? extends Entity> nmsClass,
                                      final Class<? extends Entity> customClass) {
        try {
            final List<Map<?, ?>> dataMaps = new ArrayList<Map<?, ?>>();
            for (Field f : EntityTypes.class.getDeclaredFields()) {
                if (f.getType().equals(Map.class)) {
                    f.setAccessible(true);
                    dataMaps.add((Map<?, ?>) f.get(null));
                }
            }
            EntityTypes.b.a(id, new MinecraftKey(name), customClass);
            for (Field f : BiomeBase.class.getDeclaredFields()) {
                /*
                 * Viva64 report
                 * https://www.viva64.com/ru/w/v6054/
                 * */
                //if (f.getType().getSimpleName().equals(Map.class.getSimpleName())) {
                if (f.getType().equals(BiomeBase.class)) {
                    if (f.get(null) != null) {
                        for (Field list : BiomeBase.class.getDeclaredFields()) {
                            if (list.getType().equals(List.class)) {
                                list.setAccessible(true);
                                @SuppressWarnings("unchecked")
                                List<BiomeBase.BiomeMeta> metaList = (List<BiomeBase.BiomeMeta>) list.get(f.get(null));

                                for (BiomeBase.BiomeMeta meta : metaList) {
                                    Field clazz = BiomeBase.BiomeMeta.class.getDeclaredFields()[0];
                                    if (clazz.get(meta).equals(nmsClass))
                                        clazz.set(meta, customClass);
                                }
                            }
                        }
                    }
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static void unregisterEntity(final String name, final int id, final Class<? extends Entity> nmsClass,
                                      final Class<? extends Entity> customClass) {
        try {
            final List<Map<?, ?>> dataMaps = new ArrayList<Map<?, ?>>();
            for (Field f : EntityTypes.class.getDeclaredFields()) {
                /*
                * Viva64 report
                * https://www.viva64.com/ru/w/v6054/
                * */
                //if (f.getType().getSimpleName().equals(Map.class.getSimpleName())) {
                if (f.getType().equals(Map.class)) {
                    f.setAccessible(true);
                    dataMaps.add((Map<?, ?>) f.get(null));
                }
            }
            EntityTypes.b.a(id, new MinecraftKey(name), customClass);
            for (Field f : BiomeBase.class.getDeclaredFields()) {
                if (f.getType().equals(BiomeBase.class)) {
                    if (f.get(null) != null) {
                        for (Field list : BiomeBase.class.getDeclaredFields()) {
                            if (list.getType().equals(List.class)) {
                                list.setAccessible(true);
                                @SuppressWarnings("unchecked")
                                List<BiomeBase.BiomeMeta> metaList = (List<BiomeBase.BiomeMeta>) list.get(f.get(null));

                                for (BiomeBase.BiomeMeta meta : metaList) {
                                    Field clazz = BiomeBase.BiomeMeta.class.getDeclaredFields()[0];
                                    if (clazz.get(meta).equals(customClass))
                                        clazz.set(meta, nmsClass);
                                }
                            }
                        }
                    }
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}