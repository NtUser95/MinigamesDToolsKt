package com.gmail.borlandlp.minigamesdtools.arena.scenario;

import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorHub;

public class ScenarioCreatorHub extends CreatorHub {
    public Scenario createScenario(String scenarioID, AbstractDataProvider dataProvider) throws Exception {
        return (Scenario) this.create(scenarioID, dataProvider);
    }
}
