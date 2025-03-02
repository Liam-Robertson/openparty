// File: composeApp/src/iosMain/kotlin/com/openroots/app/MainViewController.kt
package com.openroots.app

import platform.UIKit.UIViewController
import androidx.compose.ui.window.ComposeUIViewController
import com.openroots.app.navigation.AppNavigation
import com.openroots.app.di.IOSKoinInitializer

fun MainViewController(): UIViewController {
    IOSKoinInitializer.initializeKoin()
    return ComposeUIViewController { AppNavigation() }
}