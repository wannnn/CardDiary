package com.claire.carddiary.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.res.Resources
import android.view.View
import java.text.SimpleDateFormat
import java.util.*

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Date.toSimpleDateFormat: String
    get() = SimpleDateFormat("yyyy / MM / dd", Locale.TAIWAN).format(this)

val Activity.getStatusBarHeight: Int
    get() {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
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