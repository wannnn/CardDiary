package com.claire.carddiary.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter


@BindingAdapter("android:imagePath")
fun setImage(view: ImageView, path: String) {
    if (path.contains("content://")) {
        view.setImageURI(path.toUri())
    }
}

@BindingAdapter("android:addTextChangedListener")
fun bindTextWatcher(editText: EditText, textWatcher: TextWatcher) {
    editText.addTextChangedListener(textWatcher)
}

//@BindingAdapter("android:expandAnim")
//fun expandFab(view: View) {
//    view.apply {
//        visible()
//        alpha = 0f
//        translationY = height.toFloat()
//        animate()
//            .setDuration(200)
//            .translationY(0f)
//            .setListener(object : AnimatorListenerAdapter() { })
//            .alpha(1f)
//            .start()
//    }
//}
//
//@BindingAdapter("android:collapseAnim")
//fun collapseFab(view: View) {
//    view.apply {
//        visible()
//        alpha = 1f
//        translationY = 0f
//        animate()
//            .setDuration(200)
//            .translationY(height.toFloat())
//            .setListener(object : AnimatorListenerAdapter() {
//                override fun onAnimationEnd(animation: Animator) {
//                    gone()
//                    super.onAnimationEnd(animation)
//                }
//            }).alpha(0f)
//            .start()
//    }
//}