package com.soumik.versionControl.networkflow.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


var apiService = APIClient().apiServices

class APIClient {
    var retrofit : Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://157.245.156.73/")
        .build()

    var apiServices: ApiServices = retrofit.create(
        ApiServices::class.java)
}