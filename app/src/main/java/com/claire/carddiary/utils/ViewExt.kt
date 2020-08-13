package com.claire.carddiary.utils

import android.view.View

fun View.visible() {
    if (visibility != View.VISIBLE) { visibility = View.VISIBLE }
}

fun View.invisible() {
    if (visibility != View.INVISIBLE) { visibility = View.INVISIBLE }
}

fun View.gone() {
    if (visibility != View.GONE) { visibility = View.GONE }
}

fun <T : View> T.click(block: (T: View) -> Unit) = setOnClickListener { block(it) }