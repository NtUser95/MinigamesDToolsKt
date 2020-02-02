package com.gmail.borlandlp.minigamesdtools.database

import com.gmail.borlandlp.minigamesdtools.APIComponent
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools.Companion.instance
import com.gmail.borlandlp.minigamesdtools.database.tables.Account
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.jdbc.JdbcConnectionSource
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import java.io.File
import java.io.IOException
import java.sql.SQLException
import java.util.logging.Level

class ExampleDatabaseInteraction : APIComponent {
    private var connectionSource: ConnectionSource? = null
    override fun onLoad() {
        val dataFolder = File(instance!!.dataFolder, "test.db")
        if (!dataFolder.exists()) {
            try {
                dataFolder.createNewFile()
            } catch (e: IOException) {
                instance!!.logger
                    .log(Level.SEVERE, "File write error: " + "test.db")
            }
        }
        val databaseUrl = "jdbc:sqlite:" + dataFolder.absolutePath
        try {
            connectionSource = JdbcConnectionSource(databaseUrl)
            val accountDao =
                DaoManager.createDao<Dao<Account, String>, Account>(
                    connectionSource,
                    Account::class.java
                )
            TableUtils.createTable(
                connectionSource,
                Account::class.java
            )
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    override fun onUnload() {
        try {
            connectionSource!!.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}