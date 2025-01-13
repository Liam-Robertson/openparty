//File: composeApp/src/androidMain/kotlin/com/openparty/app/OpenPartyApp.kt

package com.openparty.app

import android.app.Application
import android.util.Log
import com.openparty.app.di.AndroidKoinInitializer
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize

class OpenPartyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidKoinInitializer.initializeKoin(this)
//        FirebaseApp.initializeApp(this)
        Firebase.initialize(context = this)
        Log.d("OpenPartyApp", "Firebase initialized")


//        if (BuildConfig.DEBUG) {
//            Log.d("OpenPartyApp", "Application is running in DEBUG mode")
//        } else {
//            Log.d("OpenPartyApp", "Application is running in RELEASE mode")
//        }

    }
}
