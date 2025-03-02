// File: composeApp/src/androidMain/kotlin/com/openroots/app/main/MainActivity.kt

package com.openroots.app.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.openroots.app.navigation.AppNavigation
import com.openroots.app.ui.theme.OpenRootsTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OpenRootsTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
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
