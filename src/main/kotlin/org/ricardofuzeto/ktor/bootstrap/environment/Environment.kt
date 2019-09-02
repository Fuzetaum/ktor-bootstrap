package org.ricardofuzeto.ktor.bootstrap.environment

import java.lang.IllegalArgumentException

internal class Environment {
    companion object {
        const val ENVIRONMENT_PORT: String = "ktor.port"
        const val ENVIRONMENT_CONTENT_TYPE: String = "ktor.content-type"

        const val ENVIRONMENT_DEFAULT_PORT: String = "8080"
        const val ENVIRONMENT_CONTENT_TYPE_JSON: String = "json"
    }

    private val variables: HashMap<String, String?> = HashMap()

    init {
        variables[ENVIRONMENT_PORT] = System.getenv(ENVIRONMENT_PORT)
        variables[ENVIRONMENT_CONTENT_TYPE] = System.getenv(ENVIRONMENT_CONTENT_TYPE)
    }

    fun get(variable: String): String = when(variable) {
        ENVIRONMENT_PORT -> variables[ENVIRONMENT_PORT] ?: ENVIRONMENT_DEFAULT_PORT
        ENVIRONMENT_CONTENT_TYPE -> variables[ENVIRONMENT_CONTENT_TYPE] ?: ENVIRONMENT_CONTENT_TYPE_JSON
        else -> throw IllegalArgumentException("Environment variable \"$variable\" was not registered")
    }
}