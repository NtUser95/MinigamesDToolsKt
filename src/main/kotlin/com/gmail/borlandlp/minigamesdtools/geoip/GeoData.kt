package com.gmail.borlandlp.minigamesdtools.geoip

object GeoData {
    @JvmField
    var ip: String? = null
    @JvmField
    var type // ipv4, ipv6
            : String? = null
    @JvmField
    var continentCode // Europe..
            : String? = null
    @JvmField
    var continentName: String? = null
    @JvmField
    var countryCode // EN, RU..
            : String? = null
    var countryName // United Kingdom, Russia..
            : String? = null
    @JvmField
    var locGeonameId: String? = null
    @JvmField
    var locCapital // London, Moscow..
            : String? = null
    @JvmField
    var languages: MutableList<LangData> = mutableListOf()
}