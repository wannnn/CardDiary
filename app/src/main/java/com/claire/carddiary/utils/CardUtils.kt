package com.claire.carddiary.utils

import android.app.Activity
import android.content.res.Resources
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