package com.claire.carddiary.utils

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter

@BindingAdapter("android:imagePath")
fun setImage(view: ImageView, path: String) {
    view.setImageURI(path.toUri())
}