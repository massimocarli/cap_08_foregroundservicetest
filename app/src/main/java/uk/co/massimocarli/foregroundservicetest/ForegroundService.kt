package uk.co.massimocarli.foregroundservicetest

import android.app.IntentService
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat

class ForegroundService : IntentService("ForegroundService") {
  companion object {
    const val FOREGROUND_NOTIFICATION_ID = 1
    const val TAG = "ForegroundService"
  }

  lateinit var notificationBuilder: NotificationCompat.Builder

  override fun onCreate() {
    super.onCreate()
    notificationBuilder = createNotificationBuilder()
    foregroundServiceStarted()
    log("onCreate")
  }

  override fun onHandleIntent(intent: Intent?) {
    for (i in (1 until 1000)) {
      if (!isForegroundServiceStarted()) {
        break
      }
      Thread.sleep(1000)
      if (!isForegroundServiceStarted()) {
        break
      }
      updateNotification(i)
      log("Count $i")
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    stopForeground(true)
    foregroundServiceStopped()
    log("onDestroy")
  }

  private fun createNotificationBuilder(): NotificationCompat.Builder {
    val intent = Intent(this, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
    return NotificationCompat.Builder(this, CHANNEL_ID)
      .setSmallIcon(R.drawable.ic_stat_face)
      .setContentTitle("Counter in Foreground")
      .setPriority(NotificationCompat.PRIORITY_DEFAULT)
      .setContentIntent(pendingIntent)
  }

  private fun updateNotification(count: Int) {
    notificationBuilder.setContentText("Counter: $count")
    startForeground(FOREGROUND_NOTIFICATION_ID, notificationBuilder.build())
  }

  private fun log(msg: String) {
    Log.d(TAG, "\t->   $msg")
  }
}