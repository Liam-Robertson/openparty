//File: composeApp/src/androidMain/kotlin/com/openparty/app/OpenPartyApp.kt

package com.openparty.app

import android.app.Application
import android.util.Log
import com.openparty.app.di.androidModule
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext
import com.google.firebase.FirebaseApp
import org.koin.core.module.Module
import com.openparty.app.BuildConfig

class OpenPartyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Koin
        startKoin {
            androidContext(this@OpenPartyApp)
            modules(listOf(androidModule))
        }

        // Logs using Android Log (instead of Timber)
        if (BuildConfig.DEBUG) {
            Log.d("OpenPartyApp", "Application is running in DEBUG mode")
        } else {
            Log.d("OpenPartyApp", "Application is running in RELEASE mode")
        }

        // Initialize Firebase (Android-specific if needed)
        FirebaseApp.initializeApp(this)
        Log.d("OpenPartyApp", "Firebase initialized")
    }
}
