package com.gmail.borlandlp.minigamesdtools.arena.gui.providers.examples.bossbar;

import com.gmail.borlandlp.minigamesdtools.arena.gui.providers.GUIProvider;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;

import java.util.ArrayList;
import java.util.List;

@CreatorInfo(creatorId = "bossbar_example")
public class BossbarExampleCreator extends Creator {
    @Override
    public GUIProvider create(String id, AbstractDataProvider dataProvider) throws Exception {
        return new BossbarExample();
    }
}
