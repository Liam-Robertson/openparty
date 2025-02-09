package com.openparty.app.core.util

import dev.gitlive.firebase.firestore.Timestamp
import kotlinx.datetime.Instant

fun Timestamp.toInstant(): Instant =
    Instant.fromEpochSeconds(this.seconds, this.nanoseconds)
