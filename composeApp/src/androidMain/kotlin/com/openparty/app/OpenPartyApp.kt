//File: composeApp/src/androidMain/kotlin/com/openparty/app/OpenPartyApp.kt
package com.openparty.app

import android.app.Application
import android.util.Log
import com.openparty.app.di.AndroidKoinInitializer
import com.google.firebase.FirebaseApp
import com.openparty.app.core.shared.globalAppContext

class OpenPartyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        globalAppContext = this
        FirebaseApp.initializeApp(this)
        AndroidKoinInitializer.initializeKoin(this)
        Log.d("OpenPartyApp", "Firebase initialized and global context set")
    }
}
