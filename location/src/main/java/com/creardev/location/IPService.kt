package com.creardev.location

import okhttp3.*
import java.io.IOException

object IPService {

    private val client = OkHttpClient()

    fun getPublicIpAddress(callback: (String?) -> Unit) {
        val request = Request.Builder()
            .url("https://api.ipify.org?format=json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val ipAddress = response.body?.string()
                callback(ipAddress)
            }

            override fun onFailure(call: Call, e: IOException) {
                callback(null)
            }
        })
    }

}
