package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui.RecorderView

import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.lang.Exception

// Not Finished Yet

class RecorderService: IntentService {

    constructor(name:String?) : super("Timer Service") {}

    override fun onHandleIntent(p0: Intent?) {
        for (i in 1..5) {
            Log.v("timer", "Time (secs) = " + i)
        }

        try {
            Thread.sleep(2000)
        } catch (e: Exception) {

        }
    }
    var secs: Int = 0

    override fun onCreate() {
        super.onCreate()
        secs = 0
        Log.v("timer", "Timer service has started")
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}