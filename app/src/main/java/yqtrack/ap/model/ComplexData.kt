package yqtrack.ap.model

data class ComplexData(
    val deepLink: String?,
    val appsFlyer: MutableMap<String, Any>?,
    val googleId: String,
    val uid: String?
)
