package com.gmail.borlandlp.minigamesdtools.activepoints.reaction.custom;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.activepoints.ActivePoint;
import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.Reaction;
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

@CreatorInfo(creatorId = "team_increment_win_ticket_reaction")
public class TeamIncrementWinTicketsReactionCreator extends Creator {
    @Override
    public Reaction create(String id, AbstractDataProvider dataProvider) throws Exception {
        TeamIncrementWinTicketsReaction reaction = new TeamIncrementWinTicketsReaction();
        ConfigurationSection configurationSection = MinigamesDTools.Companion.getInstance().getConfigProvider().getEntity(ConfigPath.ACTIVE_POINT_REACTIONS, id).getData();
        reaction.setActivePoint((ActivePoint) dataProvider.get("active_point_instance"));
        reaction.setValue(Integer.parseInt(configurationSection.get("value").toString()));

        return reaction;
    }
}
