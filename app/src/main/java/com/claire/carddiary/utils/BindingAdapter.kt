package com.claire.carddiary.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.claire.carddiary.card.CardViewModel
import com.claire.carddiary.edit.EditViewModel
import com.google.android.material.appbar.AppBarLayout


@BindingAdapter("android:isShadowEnable")
fun appBarShadowEnable(appBarLayout: AppBarLayout, isEnable: Boolean) {
    if (isEnable.not()) appBarLayout.outlineProvider = null
}

@BindingAdapter("android:imagePath")
fun setImage(view: ImageView, path: String?) {
    path?.let { view.load(it) }
}

@BindingAdapter("android:roundImagePath")
fun setRoundImage(view: ImageView, path: String?) {
    path?.let {
        view.load(it) {
            transformations(CircleCropTransformation())
        }
    }
}

@BindingAdapter("android:addTitleChangedListener")
fun bindTitleTextWatcher(editText: EditText, vm: EditViewModel) {
    editText.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            vm.setTitle(p0.toString())
        }
    })
}

@BindingAdapter("android:addContentChangedListener")
fun bindContentTextWatcher(editText: EditText, vm: EditViewModel) {
    editText.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            vm.setContent(p0.toString())
        }
    })
}

@BindingAdapter("android:searchTextChangedListener")
fun bindSearchTextWatcher(editText: EditText, vm: CardViewModel) {
    editText.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            vm.searchQuery(p0.toString())
        }
    })
}