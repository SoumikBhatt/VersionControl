package com.soumik.forceupdate.networkflow.api

import android.util.Log
import com.google.gson.Gson
import com.soumik.forceupdate.networkflow.models.AdvertisementBody
import com.soumik.forceupdate.networkflow.models.AppDetailsResponse
import com.soumik.forceupdate.networkflow.models.CheckVersionBody
import com.soumik.forceupdate.networkflow.models.CheckVersionResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object WebService {


    fun callCheckVersionAPI(bodyModel: CheckVersionBody, callBack: (CheckVersionResponse?, String?) -> Unit){
        apiService.checkVersionFU(bodyModel,"5zrPZaxoekgpuDA2RMiCXMV1jvyO3CiLqebWl2l8n7klzLvWQS").enqueue(object :
            Callback<CheckVersionResponse> {
            override fun onFailure(call: Call<CheckVersionResponse>, t: Throwable) {
                Log.d("OnFailure","Check Version: ${t.localizedMessage}")
                callBack(null, ON_FAILURE_MESSAGE)
            }

            override fun onResponse(call: Call<CheckVersionResponse>, response: Response<CheckVersionResponse>) {
                Log.d("OnResponse","Check Version Body-> ${Gson().toJson(response.body())}")
                Log.d("OnResponse","Check Version Code-> ${response.code()}")

                if (response.isSuccessful) callBack(response.body(),null)
                else callBack(null, ON_FAILURE_MESSAGE)
            }
        })
    }

    fun callAppDetails(bodyModel: AdvertisementBody,callBack: (AppDetailsResponse?, String?) -> Unit){
        apiService.appDetails(bodyModel,"5zrPZaxoekgpuDA2RMiCXMV1jvyO3CiLqebWl2l8n7klzLvWQS").enqueue(object : Callback<AppDetailsResponse>{
            override fun onFailure(call: Call<AppDetailsResponse>, t: Throwable) {
                Log.d("OnFailure","App Details: ${t.localizedMessage}")
                callBack(null, ON_FAILURE_MESSAGE)
            }

            override fun onResponse(
                call: Call<AppDetailsResponse>,
                response: Response<AppDetailsResponse>
            ) {
                Log.d("OnResponse","App Details Body-> ${Gson().toJson(response.body())}")
                Log.d("OnResponse","App Details Code-> ${response.code()}")

                if (response.isSuccessful) callBack(response.body(),null)
                else callBack(null, ON_FAILURE_MESSAGE)
            }
        })
    }


    const val ON_FAILURE_MESSAGE = "Something went wrong, please try again later"
}