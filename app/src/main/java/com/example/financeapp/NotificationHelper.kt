package com.example.financeapp
// sound with notofication
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat

object NotificationHelper {
    private const val CHANNEL_ID = "transaction_channel"
    private const val CHANNEL_NAME = "Transaction Notifications"
    private const val CHANNEL_DESCRIPTION = "Notifications for transactions"
    private var notificationId = 0

    fun createNotificationChannel(context: Context) {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
            description = CHANNEL_DESCRIPTION
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            vibrationPattern = longArrayOf(100, 200, 300, 400, 500)
            setShowBadge(true)
            lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            setBypassDnd(true) // Bypass Do Not Disturb
        }
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun showTransactionNotification(context: Context, type: String, amount: Double) {
        val intent = Intent(context, Dashboard::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val message = when (type.lowercase()) {
            "income" -> "Income of Rs. $amount added successfully"
            "expense" -> "Expense of Rs. $amount recorded successfully"
            else -> "Transaction of Rs. $amount recorded"
        }

        // Create custom layout for notification
        val notificationLayout = RemoteViews(context.packageName, R.layout.notification_transaction)
        notificationLayout.setTextViewText(R.id.notification_title, "Transaction Added")
        notificationLayout.setTextViewText(R.id.notification_text, message)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setCustomContentView(notificationLayout)
            .setCustomBigContentView(notificationLayout)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM) // Changed to ALARM for higher priority
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setVibrate(longArrayOf(100, 200, 300, 400, 500))
            .setLights(Color.RED, 1000, 300)
            .setFullScreenIntent(pendingIntent, true)
            .setContentIntent(pendingIntent)

        try {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // Cancel any existing notifications
            notificationManager.cancelAll()

            // Show the new notification
            notificationManager.notify(notificationId++, builder.build())
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    fun showCurrentBudgetNotification(context: Context, amount: Double) {
        val intent = Intent(context, Dashboard::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val message = "Your current monthly budget is Rs. $amount"

        val notificationLayout = RemoteViews(context.packageName, R.layout.notification_transaction)
        notificationLayout.setTextViewText(R.id.notification_title, "Current Budget")
        notificationLayout.setTextViewText(R.id.notification_text, message)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setCustomContentView(notificationLayout)
            .setCustomBigContentView(notificationLayout)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setVibrate(longArrayOf(100, 200, 300, 400, 500))
            .setLights(Color.RED, 1000, 300)
            .setFullScreenIntent(pendingIntent, true)
            .setContentIntent(pendingIntent)

        try {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancelAll()
            notificationManager.notify(notificationId++, builder.build())
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}