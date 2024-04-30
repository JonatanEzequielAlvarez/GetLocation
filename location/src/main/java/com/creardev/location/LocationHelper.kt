package com.creardev.location

import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationHelper() {

    fun getPublicIpAddress(callback: (String?) -> Unit) {
        IPService.getPublicIpAddress(callback)
    }

    fun getLocation(ip: String, callback: (LocationData?) -> Unit) {
        val ipNumbers = extractNumbersFromJson(ip)
        if (ipNumbers != null) {
            val call = RetrofitClient.instance.getLocation(ipNumbers)
            call.enqueue(object : Callback<LocationResponse> {
                override fun onResponse(
                    call: Call<LocationResponse>,
                    response: Response<LocationResponse>
                ) {
                    if (response.isSuccessful) {
                        val locationData = response.body()?.data
                        callback(locationData)
                    } else {
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<LocationResponse>, t: Throwable) {
                    callback(null)
                }
            })
        } else {
            callback(null)
        }
    }

    private fun extractNumbersFromJson(jsonString: String): String? {
        try {
            val jsonObject = JSONObject(jsonString)
            val ipString = jsonObject.optString("ip", "")
            val regex = Regex("[^0-9.]")
            return regex.replace(ipString, "")
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}
