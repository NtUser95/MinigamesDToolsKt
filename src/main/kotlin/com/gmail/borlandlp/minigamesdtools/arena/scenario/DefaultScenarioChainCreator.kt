package com.gmail.borlandlp.minigamesdtools.arena.scenario

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import com.gmail.borlandlp.minigamesdtools.arena.ArenaBase
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.Creator
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo
import java.util.*

@CreatorInfo(creatorId = "default_scenario_chain")
class DefaultScenarioChainCreator : Creator() {
    @Throws(Exception::class)
    override fun create(id: String, dataProvider: AbstractDataProvider): ScenarioChainController {
        val configurationSection =
            instance!!.configProvider!!.getEntity(ConfigPath.SCENARIO_CHAIN, id)?.data
                ?: throw Exception("Cant find config for ScenarioChain[ID:$id]")
        //load scenarios
        val scenarioMap: MutableMap<String, Scenario> = HashMap()
        for (scenarioPhase in configurationSection.getConfigurationSection("chain").getKeys(false)) {
            val scenarioID =
                configurationSection["chain.$scenarioPhase.scenario_id"].toString()
            val scenario =
                instance!!.scenarioCreatorHub!!.createScenario(scenarioID, dataProvider)
            if (scenario != null) {
                scenarioMap[scenarioPhase] = scenario
            } else {
                throw Exception("internal error while build Scenario.")
            }
        }
        // add parents for scenarios
        for (scenarioPhase in configurationSection.getConfigurationSection("chain").getKeys(false)) {
            val parents =
                configurationSection.getStringList("chain.$scenarioPhase.parent")
            val scenarioAbstract = scenarioMap[scenarioPhase] as ScenarioAbstract?
            if (parents != null && parents.size > 0) {
                for (parentID in parents) {
                    if (scenarioMap.containsKey(parentID)) {
                        scenarioAbstract!!.addParent(scenarioMap[parentID]!!)
                    } else {
                        throw Exception("invalid parentScenarioID")
                    }
                }
            }
        }

        return ScenarioChainController((dataProvider["arena_instance"] as ArenaBase)).apply {
            this.scenarios = ArrayList<Scenario>(scenarioMap.values)
        }
    }
}