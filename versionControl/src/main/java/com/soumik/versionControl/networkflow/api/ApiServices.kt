package com.soumik.versionControl.networkflow.api

import com.soumik.versionControl.networkflow.models.AppDetailsBody
import com.soumik.versionControl.networkflow.models.AppDetailsResponse
import com.soumik.versionControl.networkflow.models.CheckVersionBody
import com.soumik.versionControl.networkflow.models.CheckVersionResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiServices {

    @POST("api/version")
    fun checkVersionFU(@Body checkVersionBody: CheckVersionBody, @Header("X-Auth-Token") key:String): Call<CheckVersionResponse>

    @POST("api/app")
    fun appDetails(@Body bodyModel: AppDetailsBody, @Header("X-Auth-Token") key:String): Call<AppDetailsResponse>
}