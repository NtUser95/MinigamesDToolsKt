package com.gmail.borlandlp.minigamesdtools.nmsentities.entity

import org.bukkit.Location
import org.bukkit.entity.Entity

interface ISkybattleEntity {
    fun spawn(loc: Location): Entity
}