package com.claire.carddiary.utils

import android.view.View

fun <T : View> T.click(block: (T: View) -> Unit) = setOnClickListener { block(it) }