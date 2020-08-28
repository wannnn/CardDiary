package com.claire.carddiary

import android.app.Application
import kotlin.properties.Delegates

class CardApplication : Application() {

    companion object {
        var instance: CardApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}