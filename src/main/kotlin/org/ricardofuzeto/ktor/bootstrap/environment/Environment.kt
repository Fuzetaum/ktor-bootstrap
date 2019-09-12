package org.ricardofuzeto.ktor.bootstrap.environment

import org.ricardofuzeto.ktor.bootstrap.log.Log
import java.lang.IllegalArgumentException

internal class Environment {
    companion object {
        const val ENVIRONMENT_PORT: String = "ktor.port"
        const val ENVIRONMENT_CONTENT_TYPE: String = "ktor.content-type"

        const val ENVIRONMENT_CONTENT_TYPE_JSON: String = "json"
    }

    private val variables: HashMap<String, String?> = HashMap()

    init {
        variables[ENVIRONMENT_PORT] = System.getenv(ENVIRONMENT_PORT)
        if (variables[ENVIRONMENT_PORT] == null) {
            throw IllegalArgumentException("Environment variable \"$ENVIRONMENT_PORT\" was not set")
        }
        variables[ENVIRONMENT_CONTENT_TYPE] = System.getenv(ENVIRONMENT_CONTENT_TYPE)
        if (variables[ENVIRONMENT_CONTENT_TYPE] == null) {
            Log.warn("Environment variable \"$ENVIRONMENT_CONTENT_TYPE\" not set. Using default value \"$ENVIRONMENT_CONTENT_TYPE_JSON\"")
        }
    }

    fun get(variable: String): String = when(variable) {
        ENVIRONMENT_PORT -> variables[ENVIRONMENT_PORT]!!
        ENVIRONMENT_CONTENT_TYPE -> variables[ENVIRONMENT_CONTENT_TYPE] ?: ENVIRONMENT_CONTENT_TYPE_JSON
        else -> ""
    }
}