package yqtrack.ap.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.ChipGroup
import yqtrack.ap.R
import yqtrack.ap.anim.slideFromLeft
import yqtrack.ap.screens.MainFrag.Companion.CREDITS
import yqtrack.ap.screens.MainFrag.Companion.VIBRATION_ENABLED

class SettingsFrag : Fragment(R.layout.frag_settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        slideFromLeft(view.findViewById(R.id.chipGroup))
        view.findViewById<Button>(R.id.buttonStart).setOnClickListener {
            val credits = when (view.findViewById<ChipGroup>(R.id.chipGroup).checkedChipId) {
                R.id.chip100 -> 100
                R.id.chip500 -> 500
                R.id.chip1000 -> 1000
                else -> -1
            }
            val vibrationEnabled = view.findViewById<CheckBox>(R.id.checkVibration).isChecked
            val bundle = bundleOf(
                CREDITS to credits,
                VIBRATION_ENABLED to vibrationEnabled
            )
            findNavController().navigate(R.id.action_settingsFrag_to_mainFrag, bundle)
        }
    }
}