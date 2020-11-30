package com.claire.carddiary.base

import android.app.Activity
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ActivityBindingProvider<out T : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) : ReadOnlyProperty<Activity, T> {

    private var binding : T? = null

    override operator fun getValue(thisRef: Activity, property: KProperty<*>): T {
        return binding ?: createBinding(thisRef).also { binding = it }
    }

    private fun createBinding(activity: Activity): T {
        return DataBindingUtil.setContentView(activity, layoutRes)
    }
}