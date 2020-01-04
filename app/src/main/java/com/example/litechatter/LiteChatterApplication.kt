package com.example.litechatter

import android.app.Application
import timber.log.Timber

class LiteChatterApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        Timber.i("onCreate called")
    }
}