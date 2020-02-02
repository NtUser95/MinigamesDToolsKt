package com.gmail.borlandlp.minigamesdtools;

import com.gmail.borlandlp.minigamesdtools.activepoints.behaviors.custom.ExampleBehaviorCreator;
import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.custom.ItemGiveReactionCreator;
import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.custom.TeamIncrementWinTicketsReactionCreator;
import com.gmail.borlandlp.minigamesdtools.activepoints.type.block.PrimitivePointCreator;
import com.gmail.borlandlp.minigamesdtools.activepoints.type.entity.PrimitiveEntityCreator;
import com.gmail.borlandlp.minigamesdtools.arena.ExampleArenaCreator;
import com.gmail.borlandlp.minigamesdtools.arena.commands.DefaultWatcherCreator;
import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.examples.bossbar.BossbarExampleCreator;
import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.examples.scoreboard.ScoreboardExampleCreator;
import com.gmail.borlandlp.minigamesdtools.arena.scenario.DefaultScenarioChainCreator;
import com.gmail.borlandlp.minigamesdtools.arena.scenario.simplyteampvp.ExampleScenarioCreator;
import com.gmail.borlandlp.minigamesdtools.arena.scenario.simplyteampvp.ExampleTwoScenarioCreator;
import com.gmail.borlandlp.minigamesdtools.arena.team.ExampleTeamCreator;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.respawn.ExampleRespawnLobbyCreator;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.spectator.ExampleSpectatorLobbyCreator;
import com.gmail.borlandlp.minigamesdtools.arena.team.lobby.starter.StarterLobbyCreator;
import com.gmail.borlandlp.minigamesdtools.conditions.examples.EmptyInventoryConditionCreator;
import com.gmail.borlandlp.minigamesdtools.conditions.examples.ExampleConditionCreator;
import com.gmail.borlandlp.minigamesdtools.config.ConfigEntity;
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.inventory_gui.InventoryPageOpenRequesterCreator;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.type.HeldHotbarCreator;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.type.ItemInterractHotbarCreator;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.example.ExampleItemCreator;
import com.gmail.borlandlp.minigamesdtools.gui.inventory.DefaultViewInventoryCreator;
import com.gmail.borlandlp.minigamesdtools.gun.bullet.GhostBulletCreator;
import com.gmail.borlandlp.minigamesdtools.gui.hotbar.items.gun.ExampleGunCreator;
import com.gmail.borlandlp.minigamesdtools.gui.inventory.slots.ExampleInventorySlotCreator;
import com.gmail.borlandlp.minigamesdtools.lobby.ExampleLobbyCreator;
import com.gmail.borlandlp.minigamesdtools.nmsentities.entity.SilentShulker;
import com.gmail.borlandlp.minigamesdtools.nmsentities.entity.SkyDragon;
import com.gmail.borlandlp.minigamesdtools.nmsentities.entity.SkyZombie;
import net.minecraft.server.v1_12_R1.EntityEnderDragon;
import net.minecraft.server.v1_12_R1.EntityShulker;
import net.minecraft.server.v1_12_R1.EntityZombie;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class DefaultCreatorsLoader {
    private Map<ConfigPath, CreatorHub> hubs = new Hashtable<>();

    {
        hubs.put(ConfigPath.HOTBAR_SLOTS, MinigamesDTools.Companion.getInstance().getHotbarItemCreatorHub());
        hubs.put(ConfigPath.HOTBAR, MinigamesDTools.Companion.getInstance().getHotbarCreatorHub());
        hubs.put(ConfigPath.SCENARIO_CHAIN, MinigamesDTools.Companion.getInstance().getScenarioChainCreatorHub());
        hubs.put(ConfigPath.SCENARIO, MinigamesDTools.Companion.getInstance().getScenarioCreatorHub());
        hubs.put(ConfigPath.ACTIVE_POINT_REACTIONS, MinigamesDTools.Companion.getInstance().getReactionCreatorHub());
        hubs.put(ConfigPath.ACTIVE_POINT_BEHAVIORS, MinigamesDTools.Companion.getInstance().getBehaviorCreatorHub());
        hubs.put(ConfigPath.ACTIVE_POINT, MinigamesDTools.Companion.getInstance().getActivePointsCreatorHub());
        hubs.put(ConfigPath.ARENA_LOBBY, MinigamesDTools.Companion.getInstance().getArenaLobbyCreatorHub());
        hubs.put(ConfigPath.INVENTORY_GUI, MinigamesDTools.Companion.getInstance().getInventoryGUICreatorHub());
        hubs.put(ConfigPath.INVENTORY_GUI_SLOT, MinigamesDTools.Companion.getInstance().getInventoryGuiSlotCreatorHub());
        hubs.put(ConfigPath.ARENA_FOLDER, MinigamesDTools.Companion.getInstance().getArenaCreatorHub());
        hubs.put(ConfigPath.SERVER_LOBBY, MinigamesDTools.Companion.getInstance().getLobbyCreatorHub());
        hubs.put(ConfigPath.CONDITIONS, MinigamesDTools.Companion.getInstance().getConditionsCreatorHub());
        hubs.put(ConfigPath.TEAMS, MinigamesDTools.Companion.getInstance().getTeamCreatorHub());
        hubs.put(ConfigPath.BULLETS, MinigamesDTools.Companion.getInstance().getBulletCreatorHub());
    }

    private void linkCreators(ConfigPath path) {
        List<ConfigEntity> keys = MinigamesDTools.Companion.getInstance().getConfigProvider().getPoolContents(path);
        for(ConfigEntity configEntity : keys) {
            ConfigurationSection conf = MinigamesDTools.Companion.getInstance().getConfigProvider().getEntity(path, configEntity.getID()).getData();
            try {
                CreatorHub creatorHub = this.hubs.get(path);
                String configEntityId = configEntity.getID();
                String creatorId = conf.get("creator_id").toString();
                creatorHub.registerRouteId2Creator(configEntityId, creatorId);
            } catch (Exception e) {
                Debug.print(Debug.LEVEL.WARNING, "Error while link creator: " + configEntity.getID());
                e.printStackTrace();
            }
        }
    }

    // TODO: getCreatorHub(HeldHotbarCreator.class) as HeldHotbarCreator with generics
    public void load() {
        Debug.print(Debug.LEVEL.NOTICE, "###########################################");
        Debug.print(Debug.LEVEL.NOTICE, "########### load code for tests ###########");
        Debug.print(Debug.LEVEL.NOTICE, "#.........................................#");

        // hotbar slots
        try {
            MinigamesDTools.Companion.getInstance().getHotbarItemCreatorHub().registerCreator(new ExampleItemCreator());
            MinigamesDTools.Companion.getInstance().getHotbarItemCreatorHub().registerCreator(new ExampleGunCreator());
            MinigamesDTools.Companion.getInstance().getHotbarItemCreatorHub().registerCreator(new InventoryPageOpenRequesterCreator());

            this.linkCreators(ConfigPath.HOTBAR_SLOTS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // hotbar
        try {
            MinigamesDTools.Companion.getInstance().getHotbarCreatorHub().registerCreator(new HeldHotbarCreator());
            MinigamesDTools.Companion.getInstance().getHotbarCreatorHub().registerCreator(new ItemInterractHotbarCreator());

            this.linkCreators(ConfigPath.HOTBAR);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // scenario chain
        try {
            MinigamesDTools.Companion.getInstance().getScenarioChainCreatorHub().registerCreator(new DefaultScenarioChainCreator());
            this.linkCreators(ConfigPath.SCENARIO_CHAIN);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // scenario
        try {
            MinigamesDTools.Companion.getInstance().getScenarioCreatorHub().registerCreator(new ExampleScenarioCreator());
            MinigamesDTools.Companion.getInstance().getScenarioCreatorHub().registerCreator(new ExampleTwoScenarioCreator());

            this.linkCreators(ConfigPath.SCENARIO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Entity controllers
        MinigamesDTools.Companion.getInstance().getEntityAPI().register("test", 69, EntityShulker.class, SilentShulker.class);
        MinigamesDTools.Companion.getInstance().getEntityAPI().register("my_zombie", 54, EntityZombie.class, SkyZombie.class);
        MinigamesDTools.Companion.getInstance().getEntityAPI().register("sky_dragon", 63, EntityEnderDragon.class, SkyDragon.class);

        // reaction
        try {
            MinigamesDTools.Companion.getInstance().getReactionCreatorHub().registerCreator(new TeamIncrementWinTicketsReactionCreator());
            MinigamesDTools.Companion.getInstance().getReactionCreatorHub().registerCreator(new ItemGiveReactionCreator());

            this.linkCreators(ConfigPath.ACTIVE_POINT_REACTIONS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // behavior
        try {
            MinigamesDTools.Companion.getInstance().getBehaviorCreatorHub().registerCreator(new ExampleBehaviorCreator());

            this.linkCreators(ConfigPath.ACTIVE_POINT_BEHAVIORS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // active_point
        try {
            MinigamesDTools.Companion.getInstance().getActivePointsCreatorHub().registerCreator(new PrimitiveEntityCreator());
            MinigamesDTools.Companion.getInstance().getActivePointsCreatorHub().registerCreator(new PrimitivePointCreator());

            this.linkCreators(ConfigPath.ACTIVE_POINT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // team
        try {
            MinigamesDTools.Companion.getInstance().getTeamCreatorHub().registerCreator(new ExampleTeamCreator());

            this.linkCreators(ConfigPath.TEAMS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // lobby
        try {
            MinigamesDTools.Companion.getInstance().getArenaLobbyCreatorHub().registerCreator(new StarterLobbyCreator());
            MinigamesDTools.Companion.getInstance().getArenaLobbyCreatorHub().registerCreator(new ExampleRespawnLobbyCreator());
            MinigamesDTools.Companion.getInstance().getArenaLobbyCreatorHub().registerCreator(new ExampleSpectatorLobbyCreator());

            this.linkCreators(ConfigPath.ARENA_LOBBY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // inventory gui
        try {
            MinigamesDTools.Companion.getInstance().getInventoryGUICreatorHub().registerCreator(new DefaultViewInventoryCreator());
            this.linkCreators(ConfigPath.INVENTORY_GUI);

            MinigamesDTools.Companion.getInstance().getInventoryGuiSlotCreatorHub().registerCreator(new ExampleInventorySlotCreator());
            this.linkCreators(ConfigPath.INVENTORY_GUI_SLOT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // GUIProvider
        try {
            MinigamesDTools.Companion.getInstance().getGuiCreatorHub().registerCreator(new BossbarExampleCreator());
            MinigamesDTools.Companion.getInstance().getGuiCreatorHub().registerCreator(new ScoreboardExampleCreator());

            MinigamesDTools.Companion.getInstance().getGuiCreatorHub().registerRouteId2Creator("BossbarExample", "bossbar_example");
            MinigamesDTools.Companion.getInstance().getGuiCreatorHub().registerRouteId2Creator("ScoreboardExample", "scoreboard_example");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // arena
        try {
            MinigamesDTools.Companion.getInstance().getArenaCreatorHub().registerCreator(new ExampleArenaCreator());

            this.linkCreators(ConfigPath.ARENA_FOLDER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // server lobby
        try {
            MinigamesDTools.Companion.getInstance().getLobbyCreatorHub().registerCreator(new ExampleLobbyCreator());

            this.linkCreators(ConfigPath.SERVER_LOBBY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // conditions
        try {
            MinigamesDTools.Companion.getInstance().getConditionsCreatorHub().registerCreator(new ExampleConditionCreator());
            MinigamesDTools.Companion.getInstance().getConditionsCreatorHub().registerCreator(new EmptyInventoryConditionCreator());

            this.linkCreators(ConfigPath.CONDITIONS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // bullets
        try {
            MinigamesDTools.Companion.getInstance().getBulletCreatorHub().registerCreator(new GhostBulletCreator());

            this.linkCreators(ConfigPath.BULLETS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ArenaCommandWatcher
        try {
            MinigamesDTools.Companion.getInstance().getCommandWatcherCreatorHub().registerCreator(new DefaultWatcherCreator());

            MinigamesDTools.Companion.getInstance().getCommandWatcherCreatorHub().registerRouteId2Creator("default_command_watcher", "default_command_watcher");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
