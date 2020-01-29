package com.gmail.borlandlp.minigamesdtools.geoip.ipstack

import com.gmail.borlandlp.minigamesdtools.geoip.GeoData
import org.json.simple.JSONObject

class IpstackResponse {
    var error: ErrorResponse? = null
    private var response: JSONObject? = null

    val isSuccessful: Boolean
        get() = error == null

    val data: GeoData?
        get() = if (response != null) IpstackResponseParser(response!!).parse() else null

    fun setResponse(jsonObject: JSONObject) {
        response = jsonObject
    }
}