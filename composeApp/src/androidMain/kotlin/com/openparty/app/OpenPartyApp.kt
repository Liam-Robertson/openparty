//File: composeApp/src/androidMain/kotlin/com/openparty/app/OpenPartyApp.kt

package com.openparty.app

import android.app.Application
import android.util.Log
import com.openparty.app.di.AndroidKoinInitializer
import com.google.firebase.FirebaseApp

class OpenPartyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        AndroidKoinInitializer.initializeKoin(this)
        Log.d("OpenPartyApp", "Firebase initialized")


//        if (BuildConfig.DEBUG) {
//            Log.d("OpenPartyApp", "Application is running in DEBUG mode")
//        } else {
//            Log.d("OpenPartyApp", "Application is running in RELEASE mode")
//        }

    }
}
