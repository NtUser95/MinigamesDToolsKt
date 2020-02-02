package com.gmail.borlandlp.minigamesdtools.database.tables

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "accounts")
class Account {
    @DatabaseField(id = true)
    private val name: String? = null
    @DatabaseField(canBeNull = false)
    private val uid: String? = null
}