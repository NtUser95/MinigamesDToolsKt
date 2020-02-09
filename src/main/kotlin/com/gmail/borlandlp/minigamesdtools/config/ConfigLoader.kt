package com.gmail.borlandlp.minigamesdtools.config

import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.config.exception.DuplicateConfigException
import com.gmail.borlandlp.minigamesdtools.config.exception.InvalidPathException
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.util.*

class ConfigLoader {
    private val paths: MutableList<File> = ArrayList()

    @Throws(InvalidPathException::class)
    fun addPath(path2Load: File) {
        if (!path2Load.isDirectory) {
            throw InvalidPathException("Path must be a directory!")
        }
        paths.add(path2Load)
    }

    /*
    * Подгружаем из каталогов конфигурационные файлы и регистрируем секции из них в их пулах.
    * пул - хранилище, имеющее свой псевдоним(пример: @hotbars) и содержащее в себе List<ConfigEntity>.
    * */
    @Throws(InvalidPathException::class)
    fun load(): ConfigProvider {
        if (paths.size == 0) {
            Debug.print(
                Debug.LEVEL.WARNING,
                "Loader has no directories."
            )
        }
        val configProvider: ConfigProvider = ConcreteConfigProvider()
        for (path in paths) {
            for (fileName in Objects.requireNonNull(path.list())) {
                if (!fileName.contains(".yml")) {
                    continue
                }
                val yaml =
                    YamlConfiguration.loadConfiguration(File(path, fileName))
                if (!yaml.contains("meta_info")) {
                    Debug.print(
                        Debug.LEVEL.NOTICE,
                        "$fileName doesn't contain meta_info - ignore file."
                    )
                    continue
                } else {
                    Debug.print(
                        Debug.LEVEL.NOTICE,
                        "Load file: $fileName"
                    )
                }
                val poolId = yaml["meta_info.pool_id"].toString()
                for (keyID in yaml.getConfigurationSection("data").getKeys(false)) {
                    val data = yaml.getConfigurationSection("data.$keyID")
                    val configEntity = ConfigEntity(keyID, data)
                    try {
                        configProvider.registerEntity(poolId, configEntity)
                    } catch (e: DuplicateConfigException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        return configProvider
    }
}