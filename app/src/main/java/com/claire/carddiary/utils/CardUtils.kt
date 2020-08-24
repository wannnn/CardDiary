package com.claire.carddiary.utils

import android.content.res.Resources
import java.text.SimpleDateFormat
import java.util.*

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Date.toSimpleDateFormat: String
    get() = SimpleDateFormat("yyyy / MM / dd", Locale.TAIWAN).format(this)