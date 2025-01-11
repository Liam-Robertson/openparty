//File: iosApp/iosApp/iOSApp.swift

import SwiftUI
import Shared // or your framework name

@main
struct iOSApp: App {
    init() {
        // Optionally start Koin from iOS if you want a fresh start,
        // or rely on the existing injection if that suits your needs.

        // For example:
        // KoinKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
