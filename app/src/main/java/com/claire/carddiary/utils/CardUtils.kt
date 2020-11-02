package com.claire.carddiary.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Date.toSimpleDateFormat: String
    get() = SimpleDateFormat("yyyy / MM / dd", Locale.TAIWAN).format(this)

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}


fun View?.expandFab() {
    this?.let {
        it.visible()
        it.alpha = 0f
        it.translationY = 0f
        it.animate()
            .setDuration(200)
            .translationY(it.height.toFloat())
            .setListener(object : AnimatorListenerAdapter() { })
            .alpha(1f)
            .start()
    }
}

fun View?.collapseFab() {
    this?.let {
        it.visible()
        it.alpha = 1f
        it.translationY = it.height.toFloat()
        it.animate()
            .setDuration(200)
            .translationY(0f)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    gone()
                    super.onAnimationEnd(animation)
                }
            }).alpha(0f)
            .start()
    }
}