package com.nammahasiru.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.nammahasiru.R

// this worker runs in background after 90 days and sends notification to user
// used Worker instead of CoroutineWorker because simpler
class ReminderWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {

        val plantName = inputData.getString("plant_name") ?: "your plant"

        // create notification channel first (required for android 8+)
        createChannel()

        val notifManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        val notification = NotificationCompat.Builder(applicationContext, "checkup_channel")
            .setSmallIcon(R.drawable.ic_leaf_notif)
            .setContentTitle("Time to check on your plant!")
            .setContentText("It's been 90 days since you planted $plantName. Is it still alive?")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        // using random id for notification so multiple plants don't override each other
        val notifId = (Math.random() * 10000).toInt()
        notifManager.notify(notifId, notification)

        return Result.success()
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "checkup_channel",
                "Plant Check-up Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "Notifies when a plant needs a 90 day status update"

            val manager = applicationContext.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}
