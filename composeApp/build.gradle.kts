import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.0" // or your Kotlin version
    id("com.google.gms.google-services")
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation("androidx.security:security-crypto:1.0.0")
            implementation("io.insert-koin:koin-android:3.5.6")
            implementation("androidx.security:security-crypto:1.0.0")
            implementation("com.google.android.gms:play-services-location:21.0.1")

            // Audio Player
            implementation("androidx.media:media:1.5.1")
            implementation("androidx.media3:media3-exoplayer:1.5.1")
            implementation("androidx.media3:media3-session:1.5.1")
            implementation("androidx.media3:media3-ui:1.5.1")
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
//            implementation("androidx.compose.material3:material3:1.3.1")
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.8.0-alpha11")
            implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")
//            implementation(compose.material3)
            implementation("com.mixpanel.android:mixpanel-android:8.0.1")
            implementation("org.jetbrains.compose.material3:material3:1.7.3")
            implementation("org.jetbrains.compose.material:material-icons-extended:1.7.3")
            implementation("dev.gitlive:firebase-auth:2.1.0")
            implementation("dev.gitlive:firebase-firestore:2.1.0")
            implementation("dev.gitlive:firebase-storage:2.1.0")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
            implementation("io.insert-koin:koin-core:4.0.2")
            implementation("io.insert-koin:koin-compose:4.0.2")
            implementation("io.insert-koin:koin-compose-viewmodel:4.0.2")
            implementation("media.kamel:kamel-image:1.0.3")
            implementation("co.touchlab:kermit:2.0.5")
            implementation("app.cash.paging:paging-compose-common:3.3.0-alpha02-0.5.1")
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")


//            implementation(libs.androidx.material.icons.core)
//            implementation(libs.androidx.material.icons.extended)
        }
    }
}

android {
    namespace = "com.openparty.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.openparty.app"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

