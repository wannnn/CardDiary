package com.claire.carddiary.utils

import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load


@BindingAdapter("android:imagePath")
fun setImage(view: ImageView, path: String?) {
    path?.let { view.load(it) }
}

@BindingAdapter("android:addTextChangedListener")
fun bindTextWatcher(editText: EditText, textWatcher: TextWatcher) {
    editText.addTextChangedListener(textWatcher)
}