package com.creardev.getlocation

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LocationApiService {
    @FormUrlEncoded
    @POST("getLocation")
    fun getLocation(@Field("ip") ip: String): Call<LocationResponse>
}
