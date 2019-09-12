package org.ricardofuzeto.ktor.bootstrap

import org.ricardofuzeto.ktor.bootstrap.environment.Environment
import org.ricardofuzeto.ktor.bootstrap.ktorServer.initKtorServer
import java.lang.IllegalArgumentException

/**
 * Object that contains all ktor-bootstrap essential methods. Current methods are:
 *
 * init(): initializes ktor-bootstrap server, running all automatic configuration to set it up according to the project
 *  source code.
 */
object KtorBootstrap {
    private val environment = Environment()

    /**
     * Initializes the Ktor server, handling all steps defined for self-configuration. This method also triggers
     *  annotation processing, searching for all ktor-bootstrap annotations and performing the necessary actions.
     *
     *  @throws IllegalArgumentException - environment variable "ktor.port" is not set
     */
    fun init() {
        initKtorServer(
            environment.get(Environment.ENVIRONMENT_PORT).toInt(),
            environment.get(Environment.ENVIRONMENT_CONTENT_TYPE))
    }
}