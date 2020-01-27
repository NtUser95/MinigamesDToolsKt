package com.gmail.borlandlp.minigamesdtools.arena.localevent;

import com.gmail.borlandlp.minigamesdtools.arena.team.TeamProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class ArenaPlayerRespawnLocalEvent extends ArenaEvent {
    private Player player;
    private TeamProvider teamProvider;

    public ArenaPlayerRespawnLocalEvent(TeamProvider t, Player p) {
        this.player = p;
        this.teamProvider = t;
    }

    public Player getPlayer() {
        return player;
    }

    public TeamProvider getTeamProvider() {
        return teamProvider;
    }
}
