package com.claire.carddiary.utils

import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.transform.CircleCropTransformation


@BindingAdapter("android:imagePath")
fun setImage(view: ImageView, path: String?) {
    path?.let { view.load(it) }
}

@BindingAdapter("android:roundImagePath")
fun setRoundImage(view: ImageView, path: String?) {
    path?.let { view.load(it) {
        transformations(CircleCropTransformation())
    }}
}

@BindingAdapter("android:addTextChangedListener")
fun bindTextWatcher(editText: EditText, textWatcher: TextWatcher) {
    editText.addTextChangedListener(textWatcher)
}