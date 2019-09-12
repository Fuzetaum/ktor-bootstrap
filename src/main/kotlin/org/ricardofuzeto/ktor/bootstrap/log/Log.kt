package org.ricardofuzeto.ktor.bootstrap.log

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Object that contains all message writing capabilities.
 */
object Log {
    private const val MESSAGE_TEMPLATE = "[%s]-[%s] %s"
    private const val PLACEHOLDER_INFO = "INFO"
    private const val PLACEHOLDER_WARN = "WARN"

    private fun getCurrentTimestamp() = DateTimeFormatter.ofPattern("uu/MM/dd HH:mm:ss.SSS")
        .withZone(ZoneId.of("UTC"))
        .format(Instant.now())

    /**
     * Writes a message in the application's console, with a INFO label. It also adds, at the very beginning, a
     *  timestamp of when each message was written.
     *
     * @param message the message to be written to console
     */
    fun info(message: String) { println(MESSAGE_TEMPLATE.format(getCurrentTimestamp(), PLACEHOLDER_INFO, message)) }

    /**
     * Writes a message in the application's console, with a WARN label. It also adds, at the very beginning, a
     *  timestamp of when each message was written.
     *
     * @param message the message to be written to console
     */
    fun warn(message: String) { println(MESSAGE_TEMPLATE.format(getCurrentTimestamp(), PLACEHOLDER_WARN, message)) }
}