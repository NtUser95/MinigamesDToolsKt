package com.gmail.borlandlp.minigamesdtools.geoip

import org.bukkit.entity.Player

interface GeoIpApi {
    fun requestGeoData(player: Player): GeoData?
}