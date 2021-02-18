package com.claire.carddiary

import android.app.Application
import kotlin.properties.Delegates

class CardApplication : Application() {

    companion object {
        var instance: CardApplication by Delegates.notNull()
        var isSingleRaw = false

        val rvListType: Int
            get() = if (isSingleRaw) 1 else 2
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}