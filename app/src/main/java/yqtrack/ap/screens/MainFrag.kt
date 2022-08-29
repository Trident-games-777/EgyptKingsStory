package yqtrack.ap.screens

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import yqtrack.ap.R
import yqtrack.ap.anim.slideInLeftSequentially
import yqtrack.ap.anim.slideOutRightSequentially
import kotlin.random.Random

class MainFrag : Fragment(R.layout.frag_main) {
    private var credits = 0
    private var vibrationEnabled = false
    private val images = listOf(R.drawable.e1, R.drawable.e2, R.drawable.e3, R.drawable.e4)
    private val current = mutableListOf(R.drawable.e3, R.drawable.e3, R.drawable.e3)

    private lateinit var textView: TextView
    private lateinit var img1: ImageView
    private lateinit var img2: ImageView
    private lateinit var img3: ImageView
    private lateinit var buttonSpin: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        credits = requireArguments()[CREDITS] as Int
        vibrationEnabled = requireArguments()[VIBRATION_ENABLED] as Boolean

        textView = view.findViewById(R.id.tvCredits)
        img1 = view.findViewById(R.id.iv1)
        img2 = view.findViewById(R.id.iv2)
        img3 = view.findViewById(R.id.iv3)
        buttonSpin = view.findViewById(R.id.btnSpin)

        textView.text = getString(R.string.credits_left, credits.toString())

        buttonSpin.setOnClickListener {
            buttonSpin.isEnabled = false
            credits -= 20
            textView.text = getString(R.string.credits_left, credits.toString())
            slideOutRightSequentially(img1, img2, img3) {
                current.clear()
                current.add(images.shuffled()[Random.nextInt(0, 3)])
                img1.setImageResource(current[0])
                current.add(images.shuffled()[Random.nextInt(0, 3)])
                img2.setImageResource(current[1])
                current.add(images.shuffled()[Random.nextInt(0, 3)])
                img3.setImageResource(current[2])
                slideInLeftSequentially(img1, img2, img3) {
                    if (current.all { it == current.first() }) {
                        if (vibrationEnabled) {
                            vibrate()
                            credits += 100
                            textView.text = getString(R.string.credits_left, credits.toString())
                        }
                    }
                    if (credits >= 20) {
                        buttonSpin.isEnabled = true
                    }
                }
            }
        }
    }

    private fun vibrate() {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(200)
        }
    }

    companion object {
        const val CREDITS = "credits"
        const val VIBRATION_ENABLED = "vibration_enabled"
    }
}