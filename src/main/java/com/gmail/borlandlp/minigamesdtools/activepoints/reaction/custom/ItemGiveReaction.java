package com.gmail.borlandlp.minigamesdtools.activepoints.reaction.custom;

import com.gmail.borlandlp.minigamesdtools.Debug;
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.activepoints.reaction.Reaction;
import org.bukkit.entity.Entity;

public class ItemGiveReaction extends Reaction {
    @Override
    public void performDamage(Entity reactionInitiator, double damage) {
        Debug.print(Debug.LEVEL.NOTICE, "ItemGiveReaction->performDamage(" + reactionInitiator.getName() + ", " + damage + ")");
    }

    @Override
    public void performIntersection(Entity reactionInitiator) {
        Debug.print(Debug.LEVEL.NOTICE, "ItemGiveReaction->performIntersection(" + reactionInitiator.getName() + ")");
    }

    @Override
    public void performInteraction(Entity reactionInitiator) {
        Debug.print(Debug.LEVEL.NOTICE, "ItemGiveReaction->performInteraction(" + reactionInitiator.getName() + ")");
    }

    @Override
    public String toString() {
        return "{Reaction type=ItemGive}";
    }
}
