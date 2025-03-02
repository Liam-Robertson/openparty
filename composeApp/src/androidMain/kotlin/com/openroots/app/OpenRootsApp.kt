//File: composeApp/src/androidMain/kotlin/com/openroots/app/OpenRootsApp.kt
package com.openroots.app

import android.app.Application
import android.util.Log
import com.openroots.app.di.AndroidKoinInitializer
import com.google.firebase.FirebaseApp
import com.openroots.app.core.shared.globalAppContext

class OpenRootsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        globalAppContext = this
        FirebaseApp.initializeApp(this)
        AndroidKoinInitializer.initializeKoin(this)
        Log.d("OpenRootsApp", "Firebase initialized and global context set")
    }
}
