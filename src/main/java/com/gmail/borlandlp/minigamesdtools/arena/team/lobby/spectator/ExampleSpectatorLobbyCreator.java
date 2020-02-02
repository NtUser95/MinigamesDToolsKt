package com.gmail.borlandlp.minigamesdtools.arena.team.lobby.spectator;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;
import com.gmail.borlandlp.minigamesdtools.util.ArenaUtils;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

@CreatorInfo(creatorId = "default_spectator_lobby")
public class ExampleSpectatorLobbyCreator extends Creator {
    @Override
    public ExampleSpectatorLobby create(String id, AbstractDataProvider dataProvider) throws Exception {
        ExampleSpectatorLobby lobby = new ExampleSpectatorLobby();

        ConfigurationSection conf = MinigamesDTools.Companion.getInstance().getConfigProvider().getEntity(ConfigPath.ARENA_LOBBY, id).getData();
        if(conf.contains("hotbar.enabled") && conf.get("hotbar.enabled").toString().equals("true")) {
            lobby.setHotbarId(conf.get("hotbar.id").toString());
            lobby.setHotbarEnabled(true);
        }

        String[] splittedSpawnPoint = conf.get("location_XYZWorldYawPitch").toString().split(":");
        lobby.setSpawnPoint(ArenaUtils.str2Loc(splittedSpawnPoint));

        return lobby;
    }
}
