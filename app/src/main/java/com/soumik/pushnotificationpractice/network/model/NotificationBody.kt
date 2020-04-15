package com.soumik.pushnotificationpractice.network.model

import com.google.gson.annotations.SerializedName

class NotificationBody{
    var data: Data?=null
    var to: String?=null


}

class Data {
    var message: String?=null
    var title: String?=null
}