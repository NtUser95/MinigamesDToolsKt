package com.gmail.borlandlp.minigamesdtools.creator

import java.util.*

open class DataProvider : AbstractDataProvider {
    private val data: MutableMap<String, Any> =
        Hashtable()

    override fun contains(key: String): Boolean {
        return data.containsKey(key)
    }

    override fun get(key: String): Any {
        return data[key]!!
    }

    override fun set(key: String, value: Any) {
        data[key] = value
    }

    override fun remove(key: String) {
        data.remove(key)
    }

    override val keys: Set<String>
        get() = HashSet(data.keys)
}