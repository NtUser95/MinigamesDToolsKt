package com.gmail.borlandlp.minigamesdtools.events.sponge;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LOAD_COMPLETE_EVENT extends Event {
    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
