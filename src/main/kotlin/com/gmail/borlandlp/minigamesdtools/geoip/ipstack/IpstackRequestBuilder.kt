package com.gmail.borlandlp.minigamesdtools.geoip.ipstack

import com.gmail.borlandlp.minigamesdtools.geoip.ipstack.ErrorResponse.Companion.getByErrCode
import com.gmail.borlandlp.minigamesdtools.util.HttpUtils
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import sun.net.ConnectionResetException

class IpstackRequestBuilder {
    private var ip: String? = null
    private var apiKey: String? = null

    fun setIp(ip: String?): IpstackRequestBuilder {
        this.ip = ip
        return this
    }

    fun setApiKey(apiKey: String): IpstackRequestBuilder {
        this.apiKey = apiKey
        return this
    }

    /**
     * @throws ConnectionResetException
     * @throws ParseException
     */
    fun doRequest(): IpstackResponse {
        var strResponse: String?
        val response = IpstackResponse()
        strResponse = try {
            HttpUtils.doGetRequest("http://api.ipstack.com/$ip?access_key=$apiKey")
        } catch (e: Exception) {
            e.printStackTrace()
            return response.apply {
                response.error = ErrorResponse.NETWORK_ERROR
            }
        }

        val json = try {
            JSONParser().parse(strResponse) as JSONObject
        } catch (exception: ParseException) {
            exception.printStackTrace()
            return response.apply {
                response.error = ErrorResponse.PARSE_ERROR
            }
        }

        if (json.containsKey("success")) {
            val obj = json["error"] as JSONObject?
            val errCode = obj!!["code"].toString().toLong().toInt() // err codes range 0 - 500
            response.error = getByErrCode(errCode)
        } else {
            response.setResponse(json)
        }

        return response
    }
}