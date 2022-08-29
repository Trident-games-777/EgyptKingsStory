package yqtrack.ap.screens

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

class EKSSplash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            delay(500)
            val intent =
                Intent(
                    this@EKSSplash,
                    if (load()) EKSActivity::class.java else EKSGame::class.java
                )
            startActivity(intent)
            finish()
        }
    }

    private fun load(): Boolean {
        fun isNotADB(): Boolean =
            Settings.Global.getString(contentResolver, Settings.Global.ADB_ENABLED) != "1"

        fun isNotRoot(): Boolean = try {
            val seq = sequenceOf(
                "/sbin/su", "/system/bin/su",
                "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su",
                "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/su"
            ).map { File(it).exists() }
            seq.all { !it }
        } catch (e: SecurityException) {
            true
        }

        return isNotADB() && isNotRoot()
    }
}