package com.creardev.getlocation

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.Inet4Address
import java.net.NetworkInterface

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        IPService.getPublicIpAddress { ipAddress ->
            if (ipAddress != null) {
                val ip = extractNumbersFromJson(ipAddress)

                Log.d("IP", "Dirección IP pública: $ipAddress")

                val call = RetrofitClient.instance.getLocation(ip.toString())

                call.enqueue(object : Callback<LocationResponse> {
                    override fun onResponse(
                        call: Call<LocationResponse>,
                        response: Response<LocationResponse>
                    ) {
                        if (response.isSuccessful) {
                            val locationData = response.body()?.data
                            Log.i("response", response.toString())
                            Log.d(
                                "Location",
                                "Latitude: ${locationData?.latitude}, Longitude: ${locationData?.longitude}, Postal: ${locationData?.postal}"
                            )
                            // Aquí puedes manejar los datos de ubicación como desees
                        } else {
                            Log.e("Location", "Error al obtener la ubicación")
                        }
                    }

                    override fun onFailure(call: Call<LocationResponse>, t: Throwable) {
                        Log.e("Location", "Error de red: ${t.message}")
                    }
                })
                println("Dirección IP del dispositivo: $ipAddress")


            } else {
                // No se pudo obtener la dirección IP pública
                Log.e("IP", "No se pudo obtener la dirección IP pública")
            }
        }

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            // Estás conectado a una red
            Log.i("statusNetwork", networkInfo.toString())
        } else {
            // No estás conectado a ninguna red
            Log.i("statusNetwork", "not connected")
        }

        //val ipAddress = getIpAddress()



    }



    fun extractNumbersFromJson(jsonString: String): String? {
        try {
            val jsonObject = JSONObject(jsonString)
            val ipString = jsonObject.optString("ip", "")
            val regex = Regex("[^0-9.]")
            val numbers = regex.replace(ipString, "")
            return numbers
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}
