package com.soumik.versionControl.networkflow.models
import com.google.gson.annotations.SerializedName


data class CheckVersionResponse(
    @SerializedName("details")
    val details: Details?,
    @SerializedName("success")
    val success: String
)

data class Details(
    @SerializedName("app_id")
    val appId: Int,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("description")
    val description: String,
    @SerializedName("expiry_date")
    val expiryDate: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("launch_date")
    val launchDate: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("version_code")
    val versionCode: Int,
    @SerializedName("version_name")
    val versionName: String
)



data class AppDetailsResponse(
    @SerializedName("app")
    val app: App,
    @SerializedName("latest_version")
    val latestVersion: LatestVersion?,
    @SerializedName("success")
    val success: String
)

data class App(
    @SerializedName("app_name")
    val appName: String,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("latest_version_id")
    val latestVersionId: Int,
    @SerializedName("platform_id")
    val platformId: Int,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("user_id")
    val userId: Int
)

data class LatestVersion(
    @SerializedName("app_id")
    val appId: Int,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("expiry_date")
    val expiryDate: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("launch_date")
    val launchDate: String?,
    @SerializedName("status")
    val status: Int,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("version_code")
    val versionCode: Int,
    @SerializedName("version_name")
    val versionName: String
)