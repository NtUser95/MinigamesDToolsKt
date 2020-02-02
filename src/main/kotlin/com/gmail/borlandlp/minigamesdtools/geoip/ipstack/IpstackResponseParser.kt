package com.gmail.borlandlp.minigamesdtools.geoip.ipstack

import com.gmail.borlandlp.minigamesdtools.geoip.GeoData
import com.gmail.borlandlp.minigamesdtools.geoip.LangData
import org.json.simple.JSONArray
import org.json.simple.JSONObject

class IpstackResponseParser(private val jsonObject: JSONObject) {
    fun parse(): GeoData {
        val geoData = GeoData
        geoData.ip = jsonObject["ip"] as String?
        geoData.type = jsonObject["type"] as String?
        geoData.continentCode = jsonObject["continent_code"] as String?
        geoData.continentName = jsonObject["continent_name"] as String?
        geoData.countryCode = jsonObject["country_code"] as String?
        geoData.continentName = jsonObject["country_name"] as String?
        val locSection = jsonObject["location"] as JSONObject?
        geoData.locGeonameId = locSection!!["geoname_id"] as String?
        geoData.locCapital = locSection["capital"] as String?
        val langSection = locSection["languages"] as JSONArray?
        var langObj: JSONObject?
        for (i in langSection!!.indices) {
            langObj = langSection[i] as JSONObject?
            val langData = LangData
            langData.langCode = langObj!!["code"] as String?
            langData.langName = langObj["name"] as String?
            langData.langNativeCode = langObj["native"] as String?
            geoData.languages.add(langData)
        }
        return geoData
    }
}