package com.gmail.borlandlp.minigamesdtools.creator

abstract class Creator {
    open val dataProviderRequiredFields: List<String> = listOf()

    @Throws(Exception::class)
    abstract fun create(id: String, dataProvider: AbstractDataProvider): Any
}