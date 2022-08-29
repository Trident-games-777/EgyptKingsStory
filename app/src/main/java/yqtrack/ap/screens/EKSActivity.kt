package yqtrack.ap.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import yqtrack.ap.R
import yqtrack.ap.complex.ComplexManager
import yqtrack.ap.screens.EKSWebActivity.Companion.LINK_EXTRA
import yqtrack.ap.view_model.EKSViewModel
import javax.inject.Inject

@AndroidEntryPoint
class EKSActivity : AppCompatActivity() {
    private val eksViewModel: EKSViewModel by viewModels()

    @Inject
    lateinit var complexManager: ComplexManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eks)

        eksViewModel.takeLink(complexManager)

        eksViewModel.link.observe(this) {
            val intent = Intent(this, EKSWebActivity::class.java)
            intent.putExtra(LINK_EXTRA, it)
            startActivity(intent)
            finish()
        }
    }
}