package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.broadcastreceiver

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Icon
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.MainActivity
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.R
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui.HistoryView.HistoryItemFragment
import java.util.*
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui.RecorderView.RecorderFragment




class NotificationBase (val ctx: Context) {

    companion object {
        val ACTION_STOP_RECORDER = "ACTION_STOP_RECORDER"
    }

    fun initAudioNotification() {
        nonSchedulNotification(NotificationReceiver.NOTIFICATION_AUDIO_ID, getAudioNotification())
    }

    fun cancelAudioNotification() {
        val ns = Context.NOTIFICATION_SERVICE
        val notifManager = ctx.getSystemService(ns) as NotificationManager
        notifManager.cancel(NotificationReceiver.NOTIFICATION_AUDIO_ID)
    }

    private fun nonSchedulNotification(notifID: Int, notification: Notification) {
        with(NotificationManagerCompat.from(ctx)) {
            notify(notifID, notification)
        }
    }

    private fun scheduleNotification(notification: Notification) {
        val notificationIntent = Intent(ctx, NotificationReceiver::class.java)
        notificationIntent.putExtra(NotificationReceiver.NOTIFICATION_ID, 1)
        notificationIntent.putExtra(NotificationReceiver.NOTIFICATION, notification)
        val pendingIntent = PendingIntent.getBroadcast(
            ctx,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmStartTime = Calendar.getInstance()
        alarmStartTime.set(Calendar.HOUR_OF_DAY, 8)
        alarmStartTime.set(Calendar.MINUTE, 0)
        alarmStartTime.set(Calendar.SECOND, 0)

        val alarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent)
    }

    private fun getAudioNotification(): Notification {
//        val resultIntent = Intent(ctx, MainActivity::class.java).apply {
//            action = ACTION_STOP_RECORDER
//            putExtra("action", ACTION_STOP_RECORDER)
//        }

        val contentIntent = PendingIntent.getActivity(
            ctx,
            0,
            Intent(), // add this
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(ctx)
            .setContentTitle("PersonalEnglish is Recording")
            .setContentText("-")
            .setContentIntent(contentIntent)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(false)
            .setProgress(100, 0, true)
            .setLights(Color.BLUE, 100, 100)
            .setChannelId(MainActivity.NOTIFICATION_CHANNEL_ID)

        return builder.build()
    }

    private fun notificationAction(actionNumber: Int): NotificationCompat.Action? {
        var actionCourier = Intent(ctx, RecorderFragment::class.java)

        Log.d("NOTIFICATION", "DONE DAPET KOK HWEHWEHWE")
        when (actionNumber) {
            0 -> {
                Log.d("NOTIFICATION", "DONE DAPET KOK")
                actionCourier.putExtra("action", ACTION_STOP_RECORDER)

                val contentIntent = PendingIntent.getActivity(
                    ctx,
                    0,
                    actionCourier, // add this
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                val action = NotificationCompat.Action.Builder(
                    R.drawable.trophy,
                    "Stop",
                    contentIntent
                ).build()

                return action
            }

            else -> {
                return null
            }
        }
    }
}