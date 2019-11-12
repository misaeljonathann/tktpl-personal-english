package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui.HistoryView.HistoryItemFragment
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui.HistoryView.ProfileFragment
import android.content.Intent
import androidx.core.app.NotificationCompat
import android.app.AlarmManager
import android.app.Notification
import android.os.SystemClock
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.TaskStackBuilder
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.broadcastreceiver.NotificationReceiver
import java.util.*


class MainActivity : AppCompatActivity() {

    companion object {
        val NOTIFICATION_CHANNEL_ID = "10001"
        val default_notification_channel_id = "default"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var actionBar = getSupportActionBar()
        actionBar?.setCustomView(R.layout.actionbar_layout)

        val navbar: BottomNavigationView = findViewById(R.id.navigation)

        scheduleNotification(getNotification("Don't forget to learn new Words!"))
        navbar.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    navigateToOtherMenu(HomepageFragment())
                }

                R.id.navigation_challenge -> {
                    navigateToOtherMenu(HistoryItemFragment())
                }

                R.id.navigation_profile -> {
                    navigateToOtherMenu(ProfileFragment())
                }

                else -> {
                    true
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {

        val orientation = resources.configuration.orientation

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val intent = Intent(this, MainActivityLandscape::class.java)
            startActivity(intent)
        }
        super.onConfigurationChanged(newConfig)
    }

    private fun scheduleNotification(notification: Notification) {
        val notificationIntent = Intent(this, NotificationReceiver::class.java)
        notificationIntent.putExtra(NotificationReceiver.NOTIFICATION_ID, 1)
        notificationIntent.putExtra(NotificationReceiver.NOTIFICATION, notification)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmStartTime = Calendar.getInstance()
        alarmStartTime.set(Calendar.HOUR_OF_DAY, 8)
        alarmStartTime.set(Calendar.MINUTE, 0)
        alarmStartTime.set(Calendar.SECOND, 0)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
            alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent)
    }

    private fun getNotification(content: String): Notification {
        val resultIntent = Intent(this, MainActivity::class.java)
        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(resultIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val builder = NotificationCompat.Builder(this, default_notification_channel_id)
        builder.setContentTitle("Let's Learn New Vocabulary!")
        builder.setContentText(content)
        builder.setContentIntent(resultPendingIntent)
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
        builder.setAutoCancel(true)
        builder.setChannelId(NOTIFICATION_CHANNEL_ID)
        return builder.build()
    }

    fun navigateToOtherMenu(givenFragment: Fragment): Boolean {
        val fragmentTransaction: FragmentTransaction = this.supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_home, givenFragment, givenFragment.toString())
        fragmentTransaction.commit()
        return true
    }
}