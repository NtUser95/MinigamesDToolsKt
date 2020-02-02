package com.gmail.borlandlp.minigamesdtools.config

import com.gmail.borlandlp.minigamesdtools.config.exception.DuplicateConfigException

interface ConfigProvider {
    @Throws(DuplicateConfigException::class)
    fun registerEntity(p: ConfigPath, file: ConfigEntity)

    @Throws(DuplicateConfigException::class)
    fun registerEntity(poolId: String, file: ConfigEntity)

    fun getEntity(p: ConfigPath, entityId: String): ConfigEntity?
    fun getEntity(poolId: String, entityId: String): ConfigEntity?
    fun getPoolContents(path: ConfigPath): List<ConfigEntity>
    fun getPoolContents(poolId: String): List<ConfigEntity>
}