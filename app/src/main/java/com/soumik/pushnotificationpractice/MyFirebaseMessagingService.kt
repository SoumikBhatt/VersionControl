package com.soumik.pushnotificationpractice

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.*
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.*

class MyFirebaseMessagingService : FirebaseMessagingService() {

    //used to receive and display notifications

    companion object{
        const val ADMIN_CHANNEL_ID = "my_channel"
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        val intent = Intent(this,MainActivity::class.java)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationID = Random().nextInt(3000)

        /*
        Apps targeting SDK 26 or above (Android O) must implement notification channels and add its notifications
        to at least one of them.
      */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannel(notificationManager)
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val largeIcon = BitmapFactory.decodeResource(
            resources,
            R.drawable.ic_delete
        )

        val notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val soundUri = Uri.parse("android.resource://" + applicationContext.packageName + "/" + R.raw.notificaion)
        val audioAttributes: AudioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_ALARM)
            .build()
//        val notificationBuilder = NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_delete)
//            .setLargeIcon(largeIcon)
//            .setContentTitle(p0.data["title"])
//            .setContentText(p0.data["message"])
//            .setAutoCancel(true)
//            .setSound(notificationSoundUri)
//            .setContentIntent(pendingIntent)

        val resultPendingIntent: PendingIntent? =
            TaskStackBuilder.create(this@MyFirebaseMessagingService)
                .run {
                    // Add the intent, which inflates the back stack
                    addNextIntentWithParentStack(intent)
                    // Get the PendingIntent containing the entire back stack
                    getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                }

        val notificationBuilder = NotificationCompat.Builder(this, ADMIN_CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_delete)
            setContentTitle(p0.data["title"])
            setContentText(p0.data["message"])
            setAutoCancel(true)
            priority = NotificationCompat.PRIORITY_HIGH
//            setSound(soundUri)
            setDefaults(DEFAULT_ALL)
//            setVibrate(longArrayOf(0, 1500, 0, 1500, 0))
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            setFullScreenIntent(resultPendingIntent,true)
        }


        //Set notification color to match your app color template
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.color = resources.getColor(R.color.colorPrimaryDark)
        }

        notificationManager.notify(notificationID, notificationBuilder.build())

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupChannels(notificationManager: NotificationManager) {
        val adminChannelName = "New notification"
        val adminChannelDescription = "Device to device notification"

        val adminChannel: NotificationChannel
        adminChannel = NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_HIGH)
        adminChannel.description = adminChannelDescription
        adminChannel.enableLights(true)
        adminChannel.lightColor = Color.RED
        adminChannel.enableVibration(true)
        notificationManager.createNotificationChannel(adminChannel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupChannel(notificationManager: NotificationManager) {
        Log.d("CCC","CCC")
        val adminChannelName = "New order notification"
        val adminChannelDescription = "notification"

        val notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val soundUri = Uri.parse("android.resource://" + applicationContext.packageName + "/" + R.raw.notificaion)

        val audioAttributes: AudioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_ALARM)
            .build()

        val adminChannel: NotificationChannel
        adminChannel = NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_HIGH)
        adminChannel.description = adminChannelDescription
        adminChannel.enableLights(true)
        adminChannel.lightColor = Color.RED
//        adminChannel.setSound(soundUri, audioAttributes)
        adminChannel.enableVibration(true)
        adminChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        adminChannel.vibrationPattern = longArrayOf(0, 1500, 0, 1500, 0)
        notificationManager.createNotificationChannel(adminChannel)
    }
}
