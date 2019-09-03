package org.ricardofuzeto.ktor.bootstrap

import org.ricardofuzeto.ktor.bootstrap.environment.Environment
import org.ricardofuzeto.ktor.bootstrap.ktorServer.initKtorServer

class KtorBootstrap private constructor() {
    companion object {
        private val environment = Environment()

        fun init() {
            initKtorServer(
                environment.get(Environment.ENVIRONMENT_PORT).toInt(),
                environment.get(Environment.ENVIRONMENT_CONTENT_TYPE))
        }
    }
}