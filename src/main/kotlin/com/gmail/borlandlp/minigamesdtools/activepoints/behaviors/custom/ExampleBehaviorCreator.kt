package com.gmail.borlandlp.minigamesdtools.activepoints.behaviors.custom

import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import com.gmail.borlandlp.minigamesdtools.activepoints.ActivePoint
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath
import com.gmail.borlandlp.minigamesdtools.creator.AbstractDataProvider
import com.gmail.borlandlp.minigamesdtools.creator.Creator
import com.gmail.borlandlp.minigamesdtools.creator.CreatorInfo

@CreatorInfo(creatorId = "example_behavior")
class ExampleBehaviorCreator : Creator() {
    @Throws(Exception::class)
    override fun create(id: String, dataProvider: AbstractDataProvider): Any {

        val conf =
            instance!!.configProvider!!.getEntity(ConfigPath.ACTIVE_POINT_BEHAVIORS, id)?.data
                ?: throw Exception("Cant find config for Behavior[ID:$id]")
        val activePoint = (dataProvider["active_point_instance"] as ActivePoint)
        val delay = conf["delay"].toString().toInt()
        val period = conf["period"].toString().toInt()
        return ExampleBehavior(id, activePoint, delay, period)
    }
}