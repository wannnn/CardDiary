package com.claire.carddiary.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.claire.carddiary.CardApplication
import java.text.SimpleDateFormat
import java.util.*

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Float.dp: Float
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this,
       CardApplication.instance.resources.displayMetrics
    )

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Long.toSimpleDateFormat: String
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