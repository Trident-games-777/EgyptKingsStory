package yqtrack.ap.anim

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.BounceInterpolator
import androidx.core.animation.doOnEnd

fun slideFromLeft(view: View) {
    ObjectAnimator.ofFloat(view, View.TRANSLATION_X, -1000f, 0f).apply {
        interpolator = BounceInterpolator()
        duration = 500
        start()
    }
}

fun slideOutRightSequentially(v1: View, v2: View, v3: View, onEnd: () -> Unit = {}) {
    val first = ObjectAnimator.ofFloat(v3, View.TRANSLATION_X, 0f, 800f)
    val second = ObjectAnimator.ofFloat(v2, View.TRANSLATION_X, 0f, 1000f)
    val third = ObjectAnimator.ofFloat(v1, View.TRANSLATION_X, 0f, 1200f)
    AnimatorSet().apply {
        playSequentially(first, second, third)
        duration = 200
        doOnEnd { onEnd() }
        start()
    }
}

fun slideInLeftSequentially(v1: View, v2: View, v3: View, onEnd: () -> Unit = {}) {
    val first = ObjectAnimator.ofFloat(v3, View.TRANSLATION_X, -800f, 0f)
    val second = ObjectAnimator.ofFloat(v2, View.TRANSLATION_X, -1000f, 0f)
    val third = ObjectAnimator.ofFloat(v1, View.TRANSLATION_X, -1200f, 0f)
    AnimatorSet().apply {
        playSequentially(first, second, third)
        duration = 200
        doOnEnd { onEnd() }
        start()
    }
}