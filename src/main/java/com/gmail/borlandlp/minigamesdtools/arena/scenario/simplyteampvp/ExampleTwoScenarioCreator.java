package com.gmail.borlandlp.minigamesdtools.arena.scenario.simplyteampvp;

import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;

import java.util.ArrayList;
import java.util.List;

@CreatorInfo(creatorId = "example_scenario_two")
public class ExampleTwoScenarioCreator extends Creator {
    @Override
    public Object create(String id, AbstractDataProvider dataProvider) throws Exception {
        ExampleTwoScenario exampleScenario = new ExampleTwoScenario();
        exampleScenario.setArena((ArenaBase) dataProvider.get("arena_instance"));

        return exampleScenario;
    }
}
