package com.soumik.pushnotificationpractice.network

import com.google.gson.JsonObject
import com.soumik.pushnotificationpractice.network.model.NotificationBody
import com.soumik.pushnotificationpractice.network.model.ResponseNotification
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiServices {

    @POST("fcm/send")
    fun sendPush(@Body notificationBody: NotificationBody,@HeaderMap headers: Map<String,String>):Call<ResponseNotification>
}