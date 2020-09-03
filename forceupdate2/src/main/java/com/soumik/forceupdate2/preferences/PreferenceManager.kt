package com.soumik.forceupdate2.preferences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.soumik.forceupdate2.classes.ForceUpdate

@SuppressLint("CommitPrefEdits")
class PreferenceManager(context: Context) {

    private var preference :SharedPreferences
    private var editor: SharedPreferences.Editor

    init {
        preference = context.getSharedPreferences(NAME,MODE)
        editor = preference.edit()
    }

    var lastReminderDayOfUpdate:String
        get() = preference.getString("LRD",ForceUpdate.currentDateTime)!!
        set(value) {editor.putString("LRD",value).commit()}

    var showReminder:Boolean
        get() = preference.getBoolean("SREMIND",true)
        set(value) {editor.putBoolean("SREMIND",value).commit()}

    var isDeprecated:Boolean
        get() = preference.getBoolean("DEP",false)
        set(value) {editor.putBoolean("DEP",value).commit()}

    var isVersionAvailableDialogShown:Boolean
    get() = preference.getBoolean("VADS",false)
    set(value) {editor.putBoolean("VADS",value).commit()}

    companion object{
        const val NAME = "Force Update Preference"
        const val MODE = 0
    }
}