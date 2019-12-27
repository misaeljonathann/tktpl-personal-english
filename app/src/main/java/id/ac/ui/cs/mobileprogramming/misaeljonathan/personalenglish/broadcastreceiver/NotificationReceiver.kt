package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.broadcastreceiver

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.content.ContextCompat.getSystemService
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.MainActivity.Companion.NOTIFICATION_CHANNEL_ID
import android.app.PendingIntent
import android.media.session.PlaybackState.ACTION_PAUSE
import android.media.session.PlaybackState.ACTION_PLAY




class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification: Notification? = intent?.getParcelableExtra(NOTIFICATION)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "PERSONAL_ENGLISH_NOTIFICATION",
                importance
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }

//        va

    }

    companion object {
        // Notification ID
        var NOTIFICATION_AUDIO_ID = 9001
        var NOTIFICATION_ID = "notification-id"

        var NOTIFICATION = "notification"
    }
}