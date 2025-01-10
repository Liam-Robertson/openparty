package com.openparty.app

import android.app.Application
import com.google.firebase.FirebaseApp
import com.openparty.app.core.di.doInitKoin

class OpenPartyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        doInitKoin()
        FirebaseApp.initializeApp(this)
    }
}
