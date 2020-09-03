package com.soumik.forceupdate2.classes

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.soumik.forceupdate2.networkflow.models.CheckVersionBody
import com.soumik.forceupdate2.R
import com.soumik.forceupdate2.networkflow.api.WebService
import com.soumik.forceupdate2.networkflow.models.AppDetailsBody
import com.soumik.forceupdate2.networkflow.models.AppDetailsResponse
import com.soumik.forceupdate2.networkflow.models.CheckVersionResponse
import com.soumik.forceupdate2.preferences.PreferenceManager
import com.soumik.utilslibrary.Utills
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
class ForceUpdate {



    companion object {

        fun checkVersion(
            context: Context,
            appID: Int,
            versionCode: String,
            appName: String,
            appIcon: Int,
            dayLimit:Int
        ) {

            val checkVersionBody = CheckVersionBody()
            checkVersionBody.app_id = appID
            checkVersionBody.version_code = versionCode

            checkVersionFromApi(context, checkVersionBody, appName, appIcon,dayLimit)
        }

        private fun checkVersionFromApi(
            context: Context,
            checkVersionBody: CheckVersionBody,
            appName: String,
            appIcon: Int,
            dayLimit: Int
        ) {
            val preferenceManager = PreferenceManager(context)
            WebService.callCheckVersionAPI(checkVersionBody) { response: CheckVersionResponse?, error: String? ->
                if (error == null) {
                    if (response?.success == "true") {
                        try{
                            when (response.details?.status) {
                                1 -> {
                                    //active
                                    preferenceManager.isDeprecated = false
                                    Log.d(TAG, "Already active")
                                }
                                2 -> {
                                    //deprecated
                                    val expiryDate = response.details.expiryDate
                                    if (preferenceManager.isDeprecated) {
                                        Log.d("FORCE UPDATE", "LRD: ${getDiff(preferenceManager.lastReminderDayOfUpdate)}.........REMINDER: ${preferenceManager.showReminder}")
                                        if (getDiff(preferenceManager.lastReminderDayOfUpdate) > dayLimit && preferenceManager.showReminder) {
                                            showDeprecatedDialog(context, appName, appIcon,expiryDate)
                                        } else Log.d(TAG,"Will show after $dayLimit days")
                                    } else showDeprecatedDialog(context, appName, appIcon,expiryDate)

                                }
                                3 -> {
                                    //expired
                                    preferenceManager.isDeprecated = false
                                    showExpiredDialog(context, appName, appIcon)
                                }
                                else->{
                                    preferenceManager.isDeprecated = false
                                    Log.d(TAG, "Already active")
                                }
                            }
                        } catch (e:Exception) {
                            Log.d(TAG,"Details is null on Version: ${checkVersionBody.version_code}")
                            Log.d(TAG,"Error: ${e.localizedMessage}}")
                        }

                    } else Log.d(TAG, "Check Version success is ${response?.success}")
                } else Log.d(TAG, "Check Version Failed...")
            }
        }

        @SuppressLint("SetTextI18n")
        private fun showDeprecatedDialog(context: Context, appName: String, appIcon: Int,expiryDate:String?) {

            val preferenceManager = PreferenceManager(context)
            val dialog = Dialog(context, android.R.style.Theme_Light_NoTitleBar_Fullscreen).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setCancelable(false)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setContentView(R.layout.dialog_deprecated)
            }

            val iconIV = dialog.findViewById<ImageView>(R.id.iv_icon_deprecated)
            val textTV = dialog.findViewById<TextView>(R.id.tv_dialog_deprecated_text)
            val titleTV = dialog.findViewById<TextView>(R.id.tv_dialog_deprecated_text2)
            val instructionTV = dialog.findViewById<TextView>(R.id.tv_dialog_deprecated_inst)
            val updateBtn = dialog.findViewById<Button>(R.id.btn_deprecated_update)
            val remindLaterBtn = dialog.findViewById<Button>(R.id.btn_deprecated_remind_later)
            val notShowCheck = dialog.findViewById<CheckBox>(R.id.check_donT_show)

            iconIV.setImageResource(appIcon)
            textTV.text=appName
            titleTV.text = "The current version is deprecated."
            instructionTV.text = "This version of the $appName is deprecated, will no longer be supported after $expiryDate"

            updateBtn.setOnClickListener {
                Utills.rateApp(context)
                preferenceManager.showReminder != notShowCheck.isChecked
                preferenceManager.isDeprecated = false
                (context as Activity).finish()
                dialog.dismiss()
            }

            remindLaterBtn.setOnClickListener {
                preferenceManager.lastReminderDayOfUpdate = currentDateTime
                preferenceManager.showReminder = !notShowCheck.isChecked
                preferenceManager.isDeprecated = true
                dialog.dismiss()
            }


            dialog.show()
        }

        @SuppressLint("SetTextI18n")
        private fun showExpiredDialog(context: Context, appName: String, appIcon: Int) {
            val dialog = Dialog(context, android.R.style.Theme_Light_NoTitleBar_Fullscreen).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setCancelable(false)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setContentView(R.layout.dialog_expired)
            }

            val iconIV = dialog.findViewById<ImageView>(R.id.iv_icon_expired)
            val textTV = dialog.findViewById<TextView>(R.id.tv_dialog_expired_text)
            val titleTV = dialog.findViewById<TextView>(R.id.tv_dialog_expired_text2)
            val instructionTV = dialog.findViewById<TextView>(R.id.tv_dialog_expired_inst)
            val updateBtn = dialog.findViewById<Button>(R.id.btn_expired_update)

            iconIV.setImageResource(appIcon)
            textTV.text = appName
            titleTV.text = "This version is no longer supported."
            instructionTV.text = "To continue using $appName update to the latest version"
            updateBtn.text = "Update $appName to the latest version"

            updateBtn.setOnClickListener {
                Utills.rateApp(context)
                (context as Activity).finish()
                dialog.dismiss()

            }

            dialog.show()
        }

        private const val TAG = "FORCE UPDATE"

        val currentDateTime: String
            get() {
                val c = Calendar.getInstance()
                val df = SimpleDateFormat("yyyy-MM-dd")
                return df.format(c.time)
            }

        @SuppressLint("SimpleDateFormat")
        private fun getDiff(endDate: String): Int {
            val s = SimpleDateFormat("yyyy-MM-dd")
            val cDate = s.format(Date())
            val toDay = s.parse(cDate)
            val eDate = s.parse(endDate)

            return if (endDate.isEmpty()) 0 else ((toDay?.time!! - eDate?.time!!) / (1000 * 60 * 60 * 24)).toInt()
        }

        fun checkAppDetails(context: Context, appID: Int, versionCode: Int,appIcon: Int,appName: String){
            val appDetailsBody = AppDetailsBody()
            val preferenceManager = PreferenceManager(context)
            appDetailsBody.app_id=appID

            if (!preferenceManager.isVersionAvailableDialogShown) {
                fetchDetailsFromServer(context,appDetailsBody,versionCode,appIcon,appName)
            } else {
                Log.d(TAG,"Not Calling app details api, cause already shown")
            }
        }

        private fun fetchDetailsFromServer(context: Context,appDetailsBody: AppDetailsBody,versionCode: Int,appIcon: Int,appName: String) {
            val preferenceManager = PreferenceManager(context)

            WebService.callAppDetails(appDetailsBody){ response: AppDetailsResponse?, error: String? ->

                if (error==null){
                    if (response?.success=="true"){
                        try {
                            if (response.latestVersion?.versionCode!!>versionCode.toInt()) {
                                showNewVersionAvailableDialog(context,versionCode,appIcon,appName)
                            }
                        } catch (e:Exception){
                            Log.d(TAG,"Latest Version is null on ${response.app.appName}")
                            Log.d(TAG,"Error: ${e.localizedMessage}}")
                        }
                    }
                } else Log.d(TAG,"Fetching App Details Failed")
            }
        }

        @SuppressLint("SetTextI18n")
        private fun showNewVersionAvailableDialog(context: Context, versionCode: Int, appIcon: Int, appName: String) {

            val preferenceManager = PreferenceManager(context)
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.dialog_new_version)

            val icon = dialog.findViewById<ImageView>(R.id.iv_icon_version)
            val updateButton = dialog.findViewById<Button>(R.id.btn_update)
            val messageTV = dialog.findViewById<TextView>(R.id.tv_msg_update)

            messageTV.text = "An updated version of $appName is available"
            icon.setImageResource(appIcon)

            updateButton.setOnClickListener {
                Utills.rateApp(context)
                dialog.dismiss()
            }

            preferenceManager.isVersionAvailableDialogShown=true
            dialog.show()

        }
    }


}