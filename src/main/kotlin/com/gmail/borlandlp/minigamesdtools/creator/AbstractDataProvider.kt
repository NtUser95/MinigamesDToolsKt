package com.gmail.borlandlp.minigamesdtools.creator

interface AbstractDataProvider {
    operator fun contains(key: String): Boolean
    operator fun get(key: String): Any
    operator fun set(key: String, value: Any)
    fun remove(key: String)
    val keys: Set<String>
}