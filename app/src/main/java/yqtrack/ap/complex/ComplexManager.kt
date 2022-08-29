package yqtrack.ap.complex

import android.content.Context
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.facebook.applinks.AppLinkData
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yqtrack.ap.model.ComplexData
import yqtrack.ap.utils.const.Id
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ComplexManager(
    private val activityContext: Context
) {
    private suspend fun takeAppsFlyer(): MutableMap<String, Any>? = suspendCoroutine { cont ->
        AppsFlyerLib.getInstance()
            .init(Id.APPS_FLYER, ConversionListener { cont.resume(it) }, activityContext)
        AppsFlyerLib.getInstance().start(activityContext)
    }

    private suspend fun takeDeepLink(): String? = suspendCoroutine { cont ->
        AppLinkData.fetchDeferredAppLinkData(activityContext) { appLinkData ->
            cont.resume(appLinkData?.targetUri?.toString())
        }
    }

    private suspend fun takeGoogleId(): String = withContext(Dispatchers.IO) {
        AdvertisingIdClient.getAdvertisingIdInfo(activityContext).id.toString()
    }

    suspend fun getComplexData(): ComplexData {
        val deepLink = takeDeepLink()
        return ComplexData(
            deepLink = deepLink,
            appsFlyer = if (deepLink != null) null else takeAppsFlyer(),
            googleId = takeGoogleId(),
            uid = if (deepLink != null) null else AppsFlyerLib.getInstance()
                .getAppsFlyerUID(activityContext)
        )
    }

    private class ConversionListener(
        private val onReceive: (MutableMap<String, Any>?) -> Unit
    ) : AppsFlyerConversionListener {
        override fun onConversionDataSuccess(p0: MutableMap<String, Any>?) = onReceive(p0)
        override fun onConversionDataFail(p0: String?) = onReceive(null)
        override fun onAppOpenAttribution(p0: MutableMap<String, String>?) = Unit
        override fun onAttributionFailure(p0: String?) = Unit
    }
}