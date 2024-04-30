package com.creardev.getlocation


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.creardev.location.LocationHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val locationHelper = LocationHelper()

        // Obtener la dirección IP pública
        locationHelper.getPublicIpAddress { ipAddress ->
            if (ipAddress != null) {
                println("Dirección IP pública: $ipAddress")

                // Obtener la ubicación basada en la dirección IP
                locationHelper.getLocation(ipAddress) { locationData ->
                    if (locationData != null) {
                        println("Ubicación: Latitud ${locationData.latitude}, Longitud ${locationData.longitude}, Código postal ${locationData.postal}")
                    } else {
                        println("No se pudo obtener la ubicación")
                    }
                }
            } else {
                println("No se pudo obtener la dirección IP pública")
            }
        }

    }

}
