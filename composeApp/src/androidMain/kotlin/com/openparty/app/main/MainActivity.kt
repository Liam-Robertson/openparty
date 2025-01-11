//File: composeApp/src/androidMain/kotlin/com/openparty/app/main/MainActivity.kt

package com.openparty.app.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.openparty.app.navigation.AppNavigation
import com.openparty.app.ui.theme.OpenPartyTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    // If you need the MainViewModel from Koin
    private val mainViewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OpenPartyTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    // Launch our commonMain navigation
                    AppNavigation()
                }
            }
        }
        mainViewModel.trackAppOpenedAndIdentifyUser()
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.trackAppOpenedAndIdentifyUser()
    }
}
