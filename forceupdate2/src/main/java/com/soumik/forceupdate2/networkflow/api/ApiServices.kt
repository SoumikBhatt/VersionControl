package com.soumik.forceupdate2.networkflow.api

import com.soumik.forceupdate2.networkflow.models.AdvertisementBody
import com.soumik.forceupdate2.networkflow.models.AppDetailsResponse
import com.soumik.forceupdate2.networkflow.models.CheckVersionBody
import com.soumik.forceupdate2.networkflow.models.CheckVersionResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiServices {

    @POST("api/version")
    fun checkVersionFU(@Body checkVersionBody: CheckVersionBody, @Header("X-Auth-Token") key:String): Call<CheckVersionResponse>

    @POST("api/app")
    fun appDetails(@Body bodyModel: AdvertisementBody, @Header("X-Auth-Token") key:String): Call<AppDetailsResponse>
}