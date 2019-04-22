package uk.co.massimocarli.foregroundservicetest

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager

const val PREFIX = "uk.co.massimocarli.foregroundservicetest"
const val ACTION_UPDATE_STATE = "$PREFIX.action.ACTION_UPDATE_STATE"
const val EXTRA_STATE = "$PREFIX.extra.EXTRA_STATE"

class App : Application() {

  var isForegroundServiceStarted = false

}


fun Context.isForegroundServiceStarted() =
  (this.applicationContext as App).isForegroundServiceStarted

fun Context.foregroundServiceStarted() {
  (this.applicationContext as App).isForegroundServiceStarted = true
  updateServiceState(true)
}

fun Context.foregroundServiceStopped() {
  (this.applicationContext as App).isForegroundServiceStarted = false
  updateServiceState(false)
}

fun Context.updateServiceState(newState: Boolean) {
  val intent = Intent().apply {
    action = ACTION_UPDATE_STATE
    putExtra(EXTRA_STATE, newState)
  }
  LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
}



