package id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.ui.RecorderView

import android.Manifest
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.content.pm.PackageManager
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.databinding.FragmentRecorderBinding
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.R
import android.media.MediaRecorder
import java.io.IOException
import android.media.MediaPlayer
import android.opengl.GLSurfaceView
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.MainActivity
import id.ac.ui.cs.mobileprogramming.misaeljonathan.personalenglish.broadcastreceiver.NotificationBase

class RecorderFragment(): Fragment(), View.OnClickListener {
    private lateinit var chronometer: Chronometer
    private lateinit var mView: View
    private lateinit var playButton: Button
    private lateinit var sosButton: Button
    private lateinit var notifBase: NotificationBase
    private lateinit var navbar: BottomNavigationView
    private lateinit var glsurface: GLSurfaceView

    var mAudioRecorder: MediaRecorder? = null
    var running: Boolean = false
    var pauseOffset: Long = 0

    val PERMISSION_REQUEST_RECORD_AUDIO = 111

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentRecorderBinding  = FragmentRecorderBinding.inflate(inflater, container, false)
        this.mView = binding.getRoot()

        val action = activity?.intent?.extras?.getString("action")

        if (action == NotificationBase.ACTION_STOP_RECORDER) {
            Toast.makeText(context, "YES BISA", Toast.LENGTH_LONG).show()
        }

        playButton = mView.findViewById(R.id.play_button)
        sosButton = mView.findViewById(R.id.start_or_stop_button)

        notifBase = NotificationBase(mView.context) // Critical

        chronometer = mView.findViewById(R.id.chronometer)

        binding.startOrStopButton.setOnClickListener(this)
        binding.playButton.setOnClickListener(this)

        checkAudioPermission()

        return mView
    }

    fun checkAudioPermission() {

        if (ContextCompat.checkSelfPermission(mView.context, "android.permission.RECORD_AUDIO") != PackageManager.PERMISSION_GRANTED) {

            if (shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)) {
                val builder = AlertDialog.Builder(context)
                builder.setMessage("Permission to access the microphone is required for this app to record audio.")
                        .setTitle("Permission required")

                builder.setPositiveButton("OK") { dialog, id ->
                        permissionRequest()
                    }

                    val dialog = builder.create()
                dialog.show()
            } else {
                permissionRequest()
            }

        }
    }

    fun mediaRecorderSetup() {

        mAudioRecorder = MediaRecorder()

        val outputFile = context?.filesDir?.absolutePath + "/recording.3gp"

        mAudioRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mAudioRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mAudioRecorder?.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB)
        mAudioRecorder?.setOutputFile(outputFile)
    }

    fun permissionRequest() {
        requestPermissions(
            arrayOf(Manifest.permission.RECORD_AUDIO),
            PERMISSION_REQUEST_RECORD_AUDIO)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.start_or_stop_button -> {
                if (!running) {
                    startRecording(v)
                } else {
                    stopRecording(v)
                }
            }
            R.id.play_button -> {
                playRecord()
            }
        }
    }

    fun playRecord() {

        val outputFile = context?.filesDir?.absolutePath + "/recording.3gp"
        val mediaPlayer = MediaPlayer()
        try {
            mediaPlayer.setDataSource(outputFile)
            mediaPlayer.prepare()
            mediaPlayer.start()
            Toast.makeText(context, "Playing Audio", Toast.LENGTH_LONG)
                .show()
        } catch (e: Exception) {
            // make something
        }
    }

    fun stopRecording(v: View) {

        notifBase.cancelAudioNotification()

        // Chronometer
        chronometer.stop()
        pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase()
        running = false
        sosButton.setText("start")
        pauseOffset = 0

        // Recorder
        mAudioRecorder?.stop()
        mAudioRecorder?.release()
        mAudioRecorder = null

        playButton.isEnabled = true
        Toast.makeText(context, "Audio Recorder stopped", Toast.LENGTH_LONG).show()

        // Enable Navbar
        (activity as MainActivity).navigationToggle(true)
    }

    fun startRecording(v: View) {

        notifBase.initAudioNotification()
        mediaRecorderSetup()

        // Chronometer
        chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset)
        chronometer.start()
        running = true
        sosButton.setText("stop")

        // Recorder
        try {
            mAudioRecorder?.prepare()
            mAudioRecorder?.start()
        } catch (ise: IllegalStateException) {
            Toast.makeText(context, "Error: (ISE) Retry again or contact the owner", Toast.LENGTH_LONG).show()

        } catch (ioe: IOException) {
            Toast.makeText(context, "Error: (ioe) Retry again or contact the owner", Toast.LENGTH_LONG).show()
        }
        playButton.isEnabled = false
        Toast.makeText(context, "Recording started", Toast.LENGTH_LONG).show()

        // Disable Navbar
        (activity as MainActivity).navigationToggle(false)
    }
}