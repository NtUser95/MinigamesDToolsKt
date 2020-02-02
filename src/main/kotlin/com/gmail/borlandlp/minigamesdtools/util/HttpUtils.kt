package com.gmail.borlandlp.minigamesdtools.util

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object HttpUtils {
    @Throws(IOException::class)
    fun doGetRequest(reqUrl: String?): String {
        val result = StringBuilder()
        val url = URL(reqUrl)
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        val rd = BufferedReader(InputStreamReader(conn.inputStream))
        var line: String?
        while (rd.readLine().also { line = it } != null) {
            result.append(line)
        }
        rd.close()
        return result.toString()
    }
}