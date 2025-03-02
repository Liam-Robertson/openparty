//File: composeApp/src/commonMain/kotlin/com/openroots/app/core/firebase/feature_firebase_storage/di/FirebaseStorageModule.kt
package com.openroots.app.core.firebase.feature_firebase_storage.di

import com.openroots.app.core.firebase.feature_firebase_storage.data.repository.FirebaseStorageRepositoryImpl
import com.openroots.app.core.firebase.feature_firebase_storage.domain.repository.FirebaseStorageRepository
import com.openroots.app.core.firebase.feature_firebase_storage.domain.usecase.ResolveUrlUseCase
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.storage.FirebaseStorage
import org.koin.dsl.module
import dev.gitlive.firebase.storage.storage

val firebaseStorageModule = module {
    single<FirebaseStorage> { Firebase.storage }
    single<FirebaseStorageRepository> { FirebaseStorageRepositoryImpl(get()) }
    single { ResolveUrlUseCase(get()) }
}
