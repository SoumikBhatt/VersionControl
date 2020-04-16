package com.soumik.forceupdate2.preferences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

@SuppressLint("CommitPrefEdits")
class PreferenceManager(context: Context) {

    private var preference :SharedPreferences
    private var editor: SharedPreferences.Editor

    init {
        preference = context.getSharedPreferences(NAME,MODE)
        editor = preference.edit()
    }

    var currentDateTime:String
    get()=preference.getString("DT","")!!
    set(value) {editor.putString("DT",value).commit()}

    companion object{
        const val NAME = "Force Update Preference"
        const val MODE = 0
    }
}