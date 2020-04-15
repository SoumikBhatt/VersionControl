package com.soumik.forceupdate.classes

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.soumik.forceupdate.R
import com.soumik.forceupdate.networkflow.api.WebService
import com.soumik.forceupdate.networkflow.models.CheckVersionBody
import com.soumik.forceupdate.networkflow.models.CheckVersionResponse

class ForceUpdate {

    companion object{

        fun checkVersion(context: Context,appID:Int,versionCode:String){

            val checkVersionBody = CheckVersionBody()
            checkVersionBody.app_id = appID
            checkVersionBody.version_code = versionCode

            checkVersionFromApi(context,checkVersionBody)
        }

        private fun checkVersionFromApi(context: Context,checkVersionBody: CheckVersionBody,appName:String,appIcon:Int) {
            WebService.callCheckVersionAPI(checkVersionBody){ response: CheckVersionResponse?, error: String? ->
                if (error==null){
                    if (response?.success=="true"){
                        when (response.details.status) {
                            1 -> {
                                //active
                            }
                            2 -> {
                                //deprecated
                            }
                            else -> {
                                //expired
                                showExpiredDialog(context,appName,appIcon)
                            }
                        }
                    } else Log.d(TAG,"Check Version success is ${response?.success}")
                } else Log.d(TAG,"Check Version Failed...")
            }
        }

        private fun showExpiredDialog(context: Context,appName: String,appIcon: Int) {
            val dialog = Dialog(context,android.R.style.Theme_Light_NoTitleBar_Fullscreen).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setCancelable(false)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setContentView(R.layout.dialog_expired)
            }

            val iconIV = dialog.findViewById<ImageView>(R.id.iv_icon_expired)
            val textTV = dialog.findViewById<TextView>(R.id.tv_dialog_expired_text)
            val instructionTV = dialog.findViewById<TextView>(R.id.tv_dialog_expired_inst)

            iconIV.setImageResource(appIcon)

        }


        const val TAG = "FORCE UPDATE"
    }


}