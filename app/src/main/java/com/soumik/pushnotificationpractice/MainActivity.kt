package com.soumik.pushnotificationpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.JsonIOException
import com.google.gson.JsonObject
import com.soumik.pushnotificationpractice.network.apiService
import com.soumik.pushnotificationpractice.network.model.Data
import com.soumik.pushnotificationpractice.network.model.NotificationBody
import com.soumik.pushnotificationpractice.network.model.ResponseNotification
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private val serverKey = "key=AAAAjCXDKhY:APA91bGEKi0Du5uNGHkyigqStjGC4d7o36380SJVMiNwbwLOsEEWpX6__U9So5483AZ9lzriAC37eyqN3AakP-zYIZoo1l2ya89CGG9frEJ8hZVmM-C6iRMkEdRnWJmFL3dasVrsVldg"
    private var token = ""
    private lateinit var notificationBody: NotificationBody
    private lateinit var data: Data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        FirebaseMessaging.getInstance().subscribeToTopic("/topics/push")

            FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(this) { instanceIdResult ->
                token = instanceIdResult.token
                Log.e("TAG", token)
            }

//        val updater =
//
//
//        Log.d("PUSH_UPDATE",update)

//        val notification = JSONObject()
//        val notificationBody= JSONObject()

        notificationBody = NotificationBody()
        data = Data()



        button.setOnClickListener {
            if (TextUtils.isEmpty(et_msg.text)) et_msg.error= "write"
            else if (TextUtils.isEmpty(et_push.text)) et_push.error= "write"
            else {
                data.message = et_msg.text.toString()
                data.title = et_push.text.toString()
                notificationBody.to=token
                notificationBody.data=data



//                try {
//                    notificationBody.put("title","${et_push.text}")
//                    notificationBody.put("message","${et_msg.text}")
//
//                    notification.put("to", token)
//                    notification.put("data",notificationBody)
//
//                    Log.e("TAG", "try")
//                } catch (e:JSONException){
//                    Log.e("onCreate",e.localizedMessage!!)
//                }

                sendNotification(notificationBody)
            }
        }

    }

    private fun sendNotification(notification: NotificationBody?) {

        val headerMap= HashMap<String,String>()

        headerMap["Authorization"] = " $serverKey"
        headerMap["Content-Type"]="application/json"

        Log.d("TAG", headerMap["Authorization"]!!)
        Log.d("TAG", headerMap["Content-Type"]!!)
        Log.d("TAG", "$notification")

        try {
            apiService.sendPush(notification!!,headerMap).enqueue(object : Callback<ResponseNotification>{

                override fun onFailure(call: Call<ResponseNotification>, t: Throwable) {
                    Log.d("OnFailure",t.localizedMessage!!)
                }

                override fun onResponse(
                    call: Call<ResponseNotification>,
                    response: Response<ResponseNotification>
                ) {
                    Log.d("onResponse","Code->${response.code()}")
                    Log.d("onResponse","Body->${notification}")
                    Log.d("onResponse","Body->${response.body()}")
                    Log.d("onResponse","Body->${response.errorBody()}")
                    Log.d("onResponse","Body->${response.raw()}")
                    Log.d("onResponse","Body->${response.message()}")

                    if (response.isSuccessful){
                        Log.d("onResponse","REsponse: ${response.body()?.success}")
                        Log.d("onResponse","REsponse: ${response.body()?.results!![0].messageId}")
                    }

                    et_push.setText("")
                    et_msg.setText("")
                }
            })
        } catch (e:Exception){
            e.printStackTrace()
            e.localizedMessage
        }

    }
}
