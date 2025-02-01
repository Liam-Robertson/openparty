package com.openparty.app.core.shared.domain

import co.touchlab.kermit.Logger

object GlobalLogger {
    val logger: Logger = Logger.withTag("GlobalLogger")
}
