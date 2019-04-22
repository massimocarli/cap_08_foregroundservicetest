package uk.co.massimocarli.foregroundservicetest

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity

const val CHANNEL_ID = "ForegroundServiceChannel"

fun Activity.createNotificationChannel() {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    // Create the NotificationChannel
    val name = getString(R.string.notification_channel_name)
    val descriptionText = getString(R.string.notification_channel_description)
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
    mChannel.description = descriptionText
    notificationManager().createNotificationChannel(mChannel)
  }
}

fun Context.notificationManager() =
  getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
