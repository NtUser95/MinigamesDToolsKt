package com.gmail.borlandlp.minigamesdtools.creator

import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.Debug.print
import java.util.*

abstract class CreatorHub : ICreatorHub {
    private val creators: MutableMap<String, Creator?> =
        Hashtable()
    private val route: MutableMap<String, String?> =
        Hashtable()

    /*
     * TODO: перевести на английский
     * Регистрирует создателя объектов.
     * */
    @Throws(Exception::class)
    override fun registerCreator(factory: Creator) {
        val annotation = factory.javaClass.getDeclaredAnnotation(CreatorInfo::class.java)
        if (annotation != null) {
            if (creators.containsKey(annotation.creatorId)) {//append(this.creators.get(annotation.creatorId()).getClass().getName())
                val msg = StringBuilder()
                    .append("Creator with ID:").append(annotation.creatorId)
                    .append(" is already registered for class:")
                    .append(creators[annotation.creatorId]!!::class.java.name)
                    .append(" class trying to register:").append(factory.javaClass.name)
                throw Exception(msg.toString())
            }
            creators[annotation.creatorId] = factory
            print(
                Debug.LEVEL.NOTICE,
                "Register #class:" + factory.javaClass.simpleName + " with ID:" + annotation.creatorId
            )
        } else {
            throw Exception("Creator must be have ID!")
        }
    }

    override fun unregisterCreator(creator: Creator) { //this.route.keySet().parallelStream().filter(n -> this.routeId2Creator(n) == creator).forEach(n -> this.route.remove(n));
        creators.keys.parallelStream()
            .filter { n: String? -> creators[n] === creator }
            .forEach { n: String? -> creators.remove(n) }
    }

    override fun unregisterCreator(creatorID: String) {
        if (creators.containsKey(creatorID)) {
            return
        }
        this.unregisterCreator(creators[creatorID]!!)
    }

    /*
     * TODO: перевести на английский
     * Регистрирует перенаправление с ID/псевдонима собираемого объекта(Фигурирует обычно в конфигах),
     * на конкретного создателя(ID указывается через аннотацию @CreatorInfo к создателю),
     * Который мог бы собрать этот объект.
     * */
    @Throws(Exception::class)
    override fun registerRouteId2Creator(itemID: String, creator: String) {
        if (!creators.containsKey(creator)) {
            throw Exception("Cant find creator ID:$creator, current itemID:$itemID")
        } else {
            route[itemID] = creator
            print(
                Debug.LEVEL.NOTICE,
                "Register @route itemID:$itemID for creatorID:$creator"
            )
        }
    }

    fun routeId2Creator(Id: String): Creator {
        return creators[route[Id]]!!
    }

    override fun unregisterRouteId2Creator(itemID: String) {
        route.remove(itemID)
    }

    /*
     * TODO: перевести на английский
     * Базовый метод создания объекта
     * */
    @Throws(Exception::class)
    override fun create(itemID: String, dataProvider: AbstractDataProvider): Any {
        if (!containsRouteId2Creator(itemID)) {
            throw Exception("itemID:$itemID isnt registered routeID!")
        }
        val creator = routeId2Creator(itemID)
        for (reqField in creator.dataProviderRequiredFields) {
            if (!dataProvider.contains(reqField)) {
                throw Exception("Invalid DataProvider. Missing field '$reqField'. itemID:$itemID")
            }
        }

        return creator.create(itemID, dataProvider)
    }

    override fun containsCreator(ID: String): Boolean {
        return creators.containsKey(ID)
    }

    override fun containsCreator(creator: Creator): Boolean {
        return creators.containsValue(creator)
    }

    override fun containsRouteId2Creator(itemId: String): Boolean {
        return route.containsKey(itemId)
    }
}