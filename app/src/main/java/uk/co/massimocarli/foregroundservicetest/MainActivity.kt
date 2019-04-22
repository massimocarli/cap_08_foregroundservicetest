package uk.co.massimocarli.foregroundservicetest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  lateinit var serviceIntent: Intent

  val updateStateReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
      val newState = intent?.getBooleanExtra(EXTRA_STATE, false) ?: false
      updateButtonState(newState)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    // We create the Notification channel
    createNotificationChannel()
    serviceIntent = Intent(this, ForegroundService::class.java)
  }

  override fun onStart() {
    super.onStart()
    LocalBroadcastManager.getInstance(this)
      .registerReceiver(
        updateStateReceiver,
        IntentFilter().apply {
          addAction(ACTION_UPDATE_STATE)
        })
    updateButtonState(isForegroundServiceStarted())
  }

  override fun onStop() {
    super.onStop()
    LocalBroadcastManager.getInstance(this)
      .unregisterReceiver(updateStateReceiver)
  }

  fun startCounter(view: View) {
    startForegroundService(serviceIntent)
  }

  fun stopCounter(view: View) {
    stopService(serviceIntent)
  }

  private fun updateButtonState(serviceState: Boolean) {
    startButton.isEnabled = !serviceState
    stopButton.isEnabled = serviceState
  }
}
