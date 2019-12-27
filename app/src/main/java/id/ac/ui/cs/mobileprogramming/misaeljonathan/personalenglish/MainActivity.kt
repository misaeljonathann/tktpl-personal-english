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
import android.util.Log
import androidx.core.app.TaskStackBuilder
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.broadcastreceiver.NotificationBase
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.broadcastreceiver.NotificationReceiver
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui.RecorderView.RecorderFragment
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui.RecorderView.RecorderService
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

        Log.d("GAWAT", "1")
        if (savedInstanceState == null) {
            val extras = intent?.extras;
            Log.d("GAWAT", "2")
            if (extras == null) {
                //Cry about not being clicked on
            } else if (extras.getString("action") == NotificationBase.ACTION_STOP_RECORDER) {
                Log.d("GAWAT", "ANJER BISA TAPI KNP COK")
            }
        }

        navigationToggle(true)
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    fun navigationToggle(isEnabled: Boolean) {
        val navbar: BottomNavigationView = findViewById(R.id.navigation)

        if (isEnabled == true) {
            navbar.setEnabled(true);
            navbar.setFocusable(true);
            navbar.setFocusableInTouchMode(true);
            navbar.setClickable(true);
            navbar.setOnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.navigation_home -> {
                        navigateToOtherMenu(HomepageFragment())
                    }

                    R.id.navigation_challenge -> {
                        navigateToOtherMenu(HistoryItemFragment())
                    }

                    R.id.navigation_profile -> {
                        navigateToOtherMenu(RecorderFragment())
                    }

                    else -> {
                        true
                    }
                }
            }
        } else {
            navbar.setEnabled(false);
            navbar.setFocusable(false);
            navbar.setFocusableInTouchMode(false);
            navbar.setClickable(false);
            navbar.setOnNavigationItemSelectedListener { menuItem ->
                Toast.makeText(applicationContext, "Navigation Bar is disabled until Recorder is stopped", Toast.LENGTH_LONG).show()
                true
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

    fun navigateToOtherMenu(givenFragment: Fragment): Boolean {
        val fragmentTransaction: FragmentTransaction = this.supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_home, givenFragment, givenFragment.toString())
        fragmentTransaction.commit()
        return true
    }
}