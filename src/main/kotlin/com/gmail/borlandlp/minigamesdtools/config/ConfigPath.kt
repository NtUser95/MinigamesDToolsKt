package com.gmail.borlandlp.minigamesdtools.config

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import java.io.File

enum class ConfigPath(val path: File, val isDir: Boolean, val typeId: String) {
    MAIN(
        File(instance!!.dataFolder, "config.yml"),
        false,
        "main"
    ),
    MESSAGES(
        File(instance!!.dataFolder, "messages.yml"),
        false,
        "messages"
    ),
    CONDITIONS(
        File(instance!!.dataFolder, "conditions.yml"),
        false,
        "conditions"
    ),
    CACHE_POINTS(
        File(
            instance!!.dataFolder,
            "cache" + File.separator + "points"
        ), true, "cache_points"
    ),
    TEAMS(File(instance!!.dataFolder, "teams.yml"), false, "teams"), ARENA_FOLDER(
        File(instance!!.dataFolder, "arenas"),
        true,
        "arenas"
    ),
    HOTBAR_SLOTS(
        File(instance!!.dataFolder.absoluteFile, "hotbarslots.yml"),
        false,
        "hotbar_slots"
    ),
    HOTBAR(
        File(instance!!.dataFolder.absoluteFile, "hotbars.yml"),
        false,
        "hotbar"
    ),
    ACTIVE_POINT(
        File(instance!!.dataFolder.absoluteFile, "activepoints.yml"),
        false,
        "active_point"
    ),
    ACTIVE_POINT_REACTIONS(
        File(
            instance!!.dataFolder.absoluteFile,
            "activepointsreactions.yml"
        ), false, "active_point_reactions"
    ),
    ACTIVE_POINT_BEHAVIORS(
        File(
            instance!!.dataFolder.absoluteFile,
            "activepointbehaviors.yml"
        ), false, "active_point_behaviors"
    ),
    SCENARIO_CHAIN(
        File(
            instance!!.dataFolder.absoluteFile,
            "scenariochain.yml"
        ), false, "scenario_chain"
    ),
    SCENARIO(
        File(instance!!.dataFolder.absoluteFile, "scenario.yml"),
        false,
        "scenario"
    ),
    ARENA_LOBBY(
        File(instance!!.dataFolder.absoluteFile, "arena_lobby.yml"),
        false,
        "arena_lobby"
    ),
    SERVER_LOBBY(
        File(instance!!.dataFolder.absoluteFile, "lobby.yml"),
        false,
        "server_lobby"
    ),
    ADDONS(
        File(instance!!.dataFolder.absoluteFile, "addons"),
        true,
        "addons"
    ),
    INVENTORY_GUI(
        File(
            instance!!.dataFolder.absoluteFile,
            "inventory_gui.yml"
        ), false, "inventory_gui"
    ),
    INVENTORY_GUI_SLOT(
        File(
            instance!!.dataFolder.absoluteFile,
            "inventory_gui_slot.yml"
        ), false, "inventory_gui_slot"
    ),
    BULLETS(
        File(instance!!.dataFolder.absoluteFile, "bullets.yml"),
        false,
        "bullets"
    );

    companion object {
        fun getByPoolId(pool_id: String): ConfigPath? {
            for (path in values()) {
                if (path.typeId == pool_id) return path
            }
            return null
        }
    }

}