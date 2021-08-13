package com.example.zockettask.utils


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.zockettask.R
import com.example.zockettask.ui.view.HomeActivity

private val NOTIFICATION_ID = 0

fun sendNotification(
    messageBody: String,
    applicationContext: Context,
    title: String
) {

    val notificationCompatManger = NotificationManagerCompat.from(applicationContext)
    val contentIntent = Intent(applicationContext, HomeActivity::class.java)
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val eggImage = BitmapFactory.decodeResource(
        applicationContext.resources,
        R.drawable.ic_sync
    )

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.notification_channel_id)
    )
        .setSmallIcon(R.drawable.ic_sync)
        .setContentTitle(
            title
        )
        .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .setStyle(NotificationCompat.InboxStyle())
        .setLargeIcon(eggImage)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelId = applicationContext.getString(R.string.notification_channel_id)
        val channel =
            NotificationChannel(
                channelId,
                applicationContext.getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )
        channel.enableVibration(true)
        channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500)
        notificationCompatManger.createNotificationChannel(channel)
        builder.setChannelId(channelId)
    }
    notificationCompatManger.notify(NOTIFICATION_ID, builder.build())
}
