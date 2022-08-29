package yqtrack.ap.utils.helpers

import com.onesignal.OneSignal
import yqtrack.ap.model.ComplexData

fun sendOneSignalTag(data: ComplexData) {
    OneSignal.setExternalUserId(data.googleId)

    val campaign = data.appsFlyer?.get("campaign").toString()

    when {
        campaign == "null" && data.deepLink == null -> {
            OneSignal.sendTag("key2", "organic")
        }
        data.deepLink != null -> {
            OneSignal.sendTag(
                "key2",
                data.deepLink.replace("myapp://", "").substringBefore("/")
            )
        }
        campaign != "null" -> {
            OneSignal.sendTag(
                "key2",
                data.appsFlyer?.get("campaign").toString().substringBefore("_")
            )
        }
    }
}