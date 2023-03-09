package com.example.calcnumeric.presenter

import android.app.Application
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp
import com.example.calcnumeric.BuildConfig
import timber.log.Timber
import timber.log.Timber.DebugTree

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(timberTree)
        Timber.d(" ")

        DynamicColors.applyToActivitiesIfAvailable(this)
    }

    private val timberTree: Timber.Tree
        get() = object : DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String =
                super.createStackElementTag(element)?.let { "$it::${element.methodName}" } ?: ""
        }
}
