package yqtrack.ap.hilt

import android.app.Application
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp
import yqtrack.ap.utils.const.Id

@HiltAndroidApp
class EksApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        OneSignal.initWithContext(this)
        OneSignal.setAppId(Id.ONE_SIGNAL)
    }
}