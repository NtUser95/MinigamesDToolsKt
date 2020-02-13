package com.gmail.borlandlp.minigamesdtools

import com.gmail.borlandlp.minigamesdtools.creator.ICreatorHub

class CreatorsRegistry {
    val data: MutableMap<String, ICreatorHub> = mutableMapOf()

    fun register(creator:  ICreatorHub, id: String? = null) {
        val pseudoName = id ?: creator.javaClass.simpleName
        if (data[pseudoName] != null) {
            throw Exception("Creator:${creator.javaClass.name} tried to overwrite with pseudoName:$pseudoName")
        }
        data[pseudoName] = creator
        Debug.print(Debug.LEVEL.NOTICE, "register create name:$pseudoName class:${ICreatorHub::javaClass.name}")
    }

    fun unregister(id: String) {
        data.remove(id)
    }

    fun unregisterAll() {
        data.clear()
    }

    fun get(id: String): ICreatorHub? = data[id]
}