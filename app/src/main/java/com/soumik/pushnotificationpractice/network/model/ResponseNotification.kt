package com.soumik.pushnotificationpractice.network.model
import com.google.gson.annotations.SerializedName


//data class ResponseNotification(
//    @SerializedName("message_id")
//    val messageId: Long
//)

data class ResponseNotification(
    @SerializedName("canonical_ids")
    val canonicalIds: Int,
    @SerializedName("failure")
    val failure: Int,
    @SerializedName("multicast_id")
    val multicastId: Long,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("success")
    val success: Int
)

data class Result(
    @SerializedName("message_id")
    val messageId: String
)

