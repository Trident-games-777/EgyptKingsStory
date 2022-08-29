package yqtrack.ap.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import yqtrack.ap.R

class EKSGame : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_eks)
        window.statusBarColor = resources.getColor(R.color.black, theme)
    }
}