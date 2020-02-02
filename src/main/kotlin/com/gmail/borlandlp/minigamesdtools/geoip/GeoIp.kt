package com.gmail.borlandlp.minigamesdtools.geoip

import com.gmail.borlandlp.minigamesdtools.APIComponent
import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.MinigamesDTools
import com.gmail.borlandlp.minigamesdtools.config.ConfigPath
import com.gmail.borlandlp.minigamesdtools.geoip.ipstack.IpstackRequestBuilder
import org.bukkit.entity.Player

class GeoIp : GeoIpApi, APIComponent {
    private var apiKey: String? = null

    override fun requestGeoData(player: Player): GeoData? {
        val ip = player.address.hostName
        val response = IpstackRequestBuilder().setApiKey(apiKey!!).setIp(ip).doRequest()
        return if (response.isSuccessful ) {
            Debug.print(
                Debug.LEVEL.WARNING,
                "GeoIPRequest error msg:" + response.error?.errMessage
            )
            null
        } else {
            response.data
        }
    }

    override fun onLoad() {
        val config =
            MinigamesDTools.instance!!.configProvider!!.getEntity(ConfigPath.MAIN, "minigamesdtools").data
        apiKey = config.getString("geoip.key")
    }

    override fun onUnload() {}
}