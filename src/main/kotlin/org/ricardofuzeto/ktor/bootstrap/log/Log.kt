package org.ricardofuzeto.ktor.bootstrap.log

class Log {
    companion object {
        private const val MESSAGE_TEMPLATE = "[TIMESTAMP]-[%s] %s"
        private const val PLACEHOLDER_INFO = "INFO"

        fun info(message: String) { println(MESSAGE_TEMPLATE.format(PLACEHOLDER_INFO, message)) }
    }
}