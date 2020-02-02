package com.gmail.borlandlp.minigamesdtools.creator

interface ICreatorHub {
    @Throws(Exception::class)
    fun registerCreator(factory: Creator)

    @Throws(Exception::class)
    fun registerRouteId2Creator(itemID: String, creator: String)

    @Throws(Exception::class)
    fun create(itemID: String, dataProvider: AbstractDataProvider): Any

    fun unregisterCreator(creator: Creator)
    fun unregisterCreator(creatorID: String)
    fun unregisterRouteId2Creator(itemID: String)
    fun containsCreator(ID: String): Boolean
    fun containsCreator(creator: Creator): Boolean
    fun containsRouteId2Creator(itemId: String): Boolean
}