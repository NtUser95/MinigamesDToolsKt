package com.gmail.borlandlp.minigamesdtools.config

import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.config.exception.DuplicateConfigException
import java.util.*

class ConcreteConfigProvider : ConfigProvider {
    // String[poolId] -> String[configEntityId], ConfigEntity
    private val configsPool: MutableMap<String, MutableMap<String, ConfigEntity?>?> =
        Hashtable()

    @Throws(DuplicateConfigException::class)
    override fun registerEntity(p: ConfigPath, file: ConfigEntity) {
        this.registerEntity(p.typeId, file)
    }

    @Throws(DuplicateConfigException::class)
    override fun registerEntity(poolId: String, file: ConfigEntity) {
        val configs =
            configsPool.getOrDefault(poolId, Hashtable<String, ConfigEntity>())!!
        if (configs.containsKey(file.id)) {
            throw DuplicateConfigException(poolId + " already contains " + file.id)
        }
        configs[file.id] = file
        configsPool[poolId] = configs
    }

    override fun getEntity(p: ConfigPath, entityId: String): ConfigEntity? {
        return this.getEntity(p.typeId, entityId)
    }

    override fun getEntity(poolId: String, entityId: String): ConfigEntity? {
        return if (!configsPool.containsKey(poolId)) {
            Debug.print(
                Debug.LEVEL.WARNING,
                "[ConfigProvider] Detected an attempt to get entityID:$entityId from unregistered poolId:$poolId!"
            )
            null
        } else if (!configsPool[poolId]!!.containsKey(entityId)) {
            Debug.print(
                Debug.LEVEL.WARNING,
                "[ConfigProvider] Detected an attempt to get unknown entityID:$entityId from poolId:$poolId!"
            )
            null
        } else {
            configsPool[poolId]!![entityId]
        }
    }

    override fun getPoolContents(path: ConfigPath): List<ConfigEntity> {
        return this.getPoolContents(path.typeId)
    }

    override fun getPoolContents(poolId: String): List<ConfigEntity> {
        return if (configsPool.containsKey(poolId)) {
            ArrayList<ConfigEntity>(configsPool[poolId]!!.values)
        } else {
            Debug.print(
                Debug.LEVEL.WARNING,
                "[ConfigProvider] Detected an attempt to get poolContents for unregistered poolId:$poolId!"
            )
            ArrayList()
        }
    }
}