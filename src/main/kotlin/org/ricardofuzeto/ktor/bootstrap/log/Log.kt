package org.ricardofuzeto.ktor.bootstrap.log

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class Log {
    companion object {
        private const val MESSAGE_TEMPLATE = "[%s]-[%s] %s"
        private const val PLACEHOLDER_INFO = "INFO"
        private const val PLACEHOLDER_WARN = "WARN"

        private fun getCurrentTimestamp() = DateTimeFormatter.ofPattern("uu/MM/dd HH:mm:ss.SSS")
            .withZone(ZoneId.of("UTC"))
            .format(Instant.now())

        fun info(message: String) { println(MESSAGE_TEMPLATE.format(getCurrentTimestamp(), PLACEHOLDER_INFO, message)) }
        fun warn(message: String) { println(MESSAGE_TEMPLATE.format(getCurrentTimestamp(), PLACEHOLDER_WARN, message)) }
    }
}