package yqtrack.ap.utils.helpers

import android.net.Uri
import androidx.core.net.toUri
import yqtrack.ap.model.ComplexData
import yqtrack.ap.utils.const.Link
import yqtrack.ap.utils.const.Params.Companion.AD_GROUP_KEY
import yqtrack.ap.utils.const.Params.Companion.AD_SET_ID_KEY
import yqtrack.ap.utils.const.Params.Companion.AD_SET_KEY
import yqtrack.ap.utils.const.Params.Companion.AF_ID_KEY
import yqtrack.ap.utils.const.Params.Companion.AF_SITE_ID_KEY
import yqtrack.ap.utils.const.Params.Companion.APP_CAMPAIGN_KEY
import yqtrack.ap.utils.const.Params.Companion.CAMPAIGN_ID_KEY
import yqtrack.ap.utils.const.Params.Companion.DEEPLINK_KEY
import yqtrack.ap.utils.const.Params.Companion.DEV_TMZ_KEY
import yqtrack.ap.utils.const.Params.Companion.GADID_KEY
import yqtrack.ap.utils.const.Params.Companion.ORIG_COST_KEY
import yqtrack.ap.utils.const.Params.Companion.SECURE_GET_PARAMETR
import yqtrack.ap.utils.const.Params.Companion.SECURE_KEY
import yqtrack.ap.utils.const.Params.Companion.SOURCE_KEY
import java.util.*

fun makeLink(data: ComplexData): String {
    val timeZone: String = TimeZone.getDefault().id
    val mediaSource: String =
        if (data.deepLink != null) "deeplink" else data.appsFlyer?.get("media_source").toString()
    val uid: String = data.uid ?: "null"

    val builder: Uri.Builder = "https://${Link.INITIAL}".toUri().buildUpon()
    builder.appendQueryParameter(SECURE_GET_PARAMETR, SECURE_KEY)
    builder.appendQueryParameter(DEV_TMZ_KEY, timeZone)
    builder.appendQueryParameter(GADID_KEY, data.googleId)
    builder.appendQueryParameter(DEEPLINK_KEY, data.deepLink.toString())
    builder.appendQueryParameter(SOURCE_KEY, mediaSource)
    builder.appendQueryParameter(AF_ID_KEY, uid)
    builder.appendQueryParameter(AD_SET_ID_KEY, data.appsFlyer?.get("adset_id").toString())
    builder.appendQueryParameter(CAMPAIGN_ID_KEY, data.appsFlyer?.get("campaign_id").toString())
    builder.appendQueryParameter(APP_CAMPAIGN_KEY, data.appsFlyer?.get("campaign").toString())
    builder.appendQueryParameter(AD_SET_KEY, data.appsFlyer?.get("adset").toString())
    builder.appendQueryParameter(AD_GROUP_KEY, data.appsFlyer?.get("adgroup").toString())
    builder.appendQueryParameter(ORIG_COST_KEY, data.appsFlyer?.get("orig_cost").toString())
    builder.appendQueryParameter(AF_SITE_ID_KEY, data.appsFlyer?.get("af_siteid").toString())

    return builder.toString()
}