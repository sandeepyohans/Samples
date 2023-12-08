package com.github.sandeepyohans.demoforegroundservice.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.github.sandeepyohans.demoforegroundservice.MainActivity
import com.github.sandeepyohans.demoforegroundservice.R

class ForegroundService: Service() {

    private lateinit var notificationManager: NotificationManager

    // onStartCommand can be called multiple times, so we keep track of "started" state manually
    private var isStarted = false

    override fun onCreate() {
        super.onCreate()
        // initialize dependencies here (e.g. perform dependency injection)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onDestroy() {
        super.onDestroy()
        isStarted = false
    }

    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException() // bound Service is a different story
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(!isStarted)
            makeForeground()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun makeForeground() {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        // before calling startForeground, we must create a notification and a corresponding
        // notification channel
        createServiceNotificationChannel()
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Sandeep Foreground Service")
            .setContentText("Sandeep Foreground Service Demo")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(ONGOING_NOTIFICATION_ID, notification)
    }

    private fun createServiceNotificationChannel() {
        if (Build.VERSION.SDK_INT < 26) {
            return
        }

        val notificationChannel = NotificationChannel(
            CHANNEL_ID, "Foreground Service channel", NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        notificationManager.createNotificationChannel(notificationChannel)
    }

    companion object {
        private const val TAG = "ForegroundService"
        private const val ONGOING_NOTIFICATION_ID = 101
        private const val CHANNEL_ID = "1001"
        private const val EXTRA_DEMO = "EXTRA_DEMO"

        fun startService(context: Context, demoString: String) {
            val intent = Intent(context, ForegroundService::class.java).apply {
                putExtra(EXTRA_DEMO, demoString)
            }

            if(Build.VERSION.SDK_INT <26) {
                context.startService(intent)
            } else {
                context.startForegroundService(intent)
            }
        }

        fun stopService(context: Context) {
            Log.d(TAG, "stopService: called")
            val intent: Intent = Intent(context, ForegroundService::class.java)
            context.stopService(intent)
        }
    }
}