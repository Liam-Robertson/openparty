package com.openparty.app.core.di

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

fun doInitKoin() {
    stopKoin()
    startKoin {
        modules(sharedModule)
    }
}
