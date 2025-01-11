// File: shared/src/commonMain/kotlin/com/openparty/app/di/KoinInit.kt
package com.openparty.app.di

import org.koin.core.context.startKoin
import com.openparty.app.di.commonModule

fun doInitKoin() {
    startKoin {
        modules(commonModule)
    }
}
