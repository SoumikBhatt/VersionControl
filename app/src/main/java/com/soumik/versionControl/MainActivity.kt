package com.soumik.versionControl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.soumik.forceupdate2.classes.ForceUpdate

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ForceUpdate.checkVersion(this,3,42.toString(),getString(R.string.app_name),R.mipmap.ic_launcher,5)
        ForceUpdate.checkAppDetails(this,3,42,R.mipmap.ic_launcher,getString(R.string.app_name))


    }
}
