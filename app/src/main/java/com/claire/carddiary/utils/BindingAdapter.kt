package com.claire.carddiary.utils

import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter


@BindingAdapter("android:imagePath")
fun setImage(view: ImageView, path: String) {
    view.setImageURI(path.toUri())
}

@BindingAdapter("android:addTextChangedListener")
fun bindTextWatcher(editText: EditText, textWatcher: TextWatcher) {
    editText.addTextChangedListener(textWatcher)
}