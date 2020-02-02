package com.gmail.borlandlp.minigamesdtools.arena.scenario;

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools;
import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase;
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath;
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider;
import com.gmail.borlandlp.minigamesdtools.creator.Creator;
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CreatorInfo(creatorId = "default_scenario_chain")
public class DefaultScenarioChainCreator extends Creator {
    @Override
    public ScenarioChainController create(String id, AbstractDataProvider dataProvider) throws Exception {
        ConfigurationSection configurationSection = MinigamesDTools.Companion.getInstance().getConfigProvider().getEntity(ConfigPath.SCENARIO_CHAIN, id).getData();
        if(configurationSection == null) {
            throw new Exception("Cant find config for ScenarioChain[ID:" + id + "]");
        }

        //load scenarios
        Map<String, Scenario> scenarioMap = new HashMap<>();
        for (String scenarioPhase : configurationSection.getConfigurationSection("chain").getKeys(false)) {
            String scenarioID = configurationSection.get("chain." + scenarioPhase + ".scenario_id").toString();
            Scenario scenario = MinigamesDTools.Companion.getInstance().getScenarioCreatorHub().createScenario(scenarioID, dataProvider);
            if(scenario != null) {
                scenarioMap.put(scenarioPhase, scenario);
            } else {
                throw new Exception("internal error while build Scenario.");
            }
        }

        // add parents for scenarios
        for (String scenarioPhase : configurationSection.getConfigurationSection("chain").getKeys(false)) {
            List<String> parents = configurationSection.getStringList("chain." + scenarioPhase + ".parent");
            ScenarioAbstract scenarioAbstract = (ScenarioAbstract) scenarioMap.get(scenarioPhase);
            if(parents != null && parents.size() > 0) {
                for (String parentID : parents) {
                    if(scenarioMap.containsKey(parentID)) {
                        scenarioAbstract.addParent(scenarioMap.get(parentID));
                    } else {
                        throw new Exception("invalid parentScenarioID");
                    }
                }
            }
        }

        ScenarioChainController scenarioChainController = new ScenarioChainController((ArenaBase) dataProvider.get("arena_instance"));
        scenarioChainController.setScenarios(new ArrayList<>(scenarioMap.values()));

        return scenarioChainController;
    }
}
