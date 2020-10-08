package com.soumik.versionControl.networkflow.api

import android.util.Log
import com.soumik.versionControl.classes.VersionControl
import com.soumik.versionControl.networkflow.models.AppDetailsBody
import com.soumik.versionControl.networkflow.models.AppDetailsResponse
import com.soumik.versionControl.networkflow.models.CheckVersionBody
import com.soumik.versionControl.networkflow.models.CheckVersionResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object WebService {


    fun callCheckVersionAPI(bodyModel: CheckVersionBody, callBack: (CheckVersionResponse?, String?) -> Unit){
        apiService.checkVersionFU(bodyModel,"5zrPZaxoekgpuDA2RMiCXMV1jvyO3CiLqebWl2l8n7klzLvWQS").enqueue(object :
            Callback<CheckVersionResponse> {
            override fun onFailure(call: Call<CheckVersionResponse>, t: Throwable) {
                Log.e(VersionControl.TAG,"onFailure: Check Version: ${t.localizedMessage}")
                callBack(null, ON_FAILURE_MESSAGE)
            }

            override fun onResponse(call: Call<CheckVersionResponse>, response: Response<CheckVersionResponse>) {
                Log.d(VersionControl.TAG,"OnResponse: Check Version Body-> ${response.body()}")
                Log.d(VersionControl.TAG,"OnResponse: Check Version Code-> ${response.code()}")

                if (response.isSuccessful) callBack(response.body(),null)
                else callBack(null, ON_FAILURE_MESSAGE)
            }
        })
    }

    fun callAppDetails(bodyModel: AppDetailsBody, callBack: (AppDetailsResponse?, String?) -> Unit){
        apiService.appDetails(bodyModel,"5zrPZaxoekgpuDA2RMiCXMV1jvyO3CiLqebWl2l8n7klzLvWQS").enqueue(object : Callback<AppDetailsResponse>{
            override fun onFailure(call: Call<AppDetailsResponse>, t: Throwable) {
                Log.d(VersionControl.TAG,"OnFailure: App Details: ${t.localizedMessage}")
                callBack(null, ON_FAILURE_MESSAGE)
            }

            override fun onResponse(
                call: Call<AppDetailsResponse>,
                response: Response<AppDetailsResponse>
            ) {
                Log.d(VersionControl.TAG,"OnResponse: App Details Body-> ${response.body()}")
                Log.d(VersionControl.TAG,"OnResponse: App Details Code-> ${response.code()}")

                if (response.isSuccessful) callBack(response.body(),null)
                else callBack(null, ON_FAILURE_MESSAGE)
            }
        })
    }


    const val ON_FAILURE_MESSAGE = "Something went wrong, please try again later"
}