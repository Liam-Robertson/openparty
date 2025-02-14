// File: composeApp/src/iosMain/kotlin/com/openparty/app/MainViewController.kt
package com.openparty.app

import platform.UIKit.UIViewController
import androidx.compose.ui.window.ComposeUIViewController
import com.openparty.app.navigation.AppNavigation
import com.openparty.app.di.IOSKoinInitializer

fun MainViewController(): UIViewController {
    IOSKoinInitializer.initializeKoin()
    return ComposeUIViewController { AppNavigation() }
}
