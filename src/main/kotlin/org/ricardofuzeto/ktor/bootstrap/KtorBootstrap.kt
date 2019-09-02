package org.ricardofuzeto.ktor.bootstrap

import io.ktor.application.ApplicationCall
import io.ktor.util.pipeline.PipelineContext
import org.ricardofuzeto.ktor.bootstrap.environment.Environment
import org.ricardofuzeto.ktor.bootstrap.ktorServer.*
import org.ricardofuzeto.ktor.bootstrap.ktorServer.applyDeleteRoute
import org.ricardofuzeto.ktor.bootstrap.ktorServer.applyGetRoute
import org.ricardofuzeto.ktor.bootstrap.ktorServer.applyHeadRoute
import org.ricardofuzeto.ktor.bootstrap.ktorServer.initKtorServer

enum class KtorRouteType {
    DELETE,
    GET,
    HEAD,
    OPTIONS,
    PATCH,
    POST,
    PUT
}

class KtorBootstrap private constructor() {
    companion object {
        private val environment = Environment()

        fun init() {
            initKtorServer(
                environment.get(Environment.ENVIRONMENT_PORT).toInt(),
                environment.get(Environment.ENVIRONMENT_CONTENT_TYPE))
        }

        fun addRoute(method: KtorRouteType, path: String, body: suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit) {
            when(method) {
                KtorRouteType.DELETE -> applyDeleteRoute(path, body)
                KtorRouteType.GET -> applyGetRoute(path, body)
                KtorRouteType.HEAD -> applyHeadRoute(path, body)
                KtorRouteType.OPTIONS -> applyOptionsRoute(path, body)
                KtorRouteType.PATCH -> applyPatchRoute(path, body)
                KtorRouteType.POST -> applyPostRoute(path, body)
                KtorRouteType.PUT -> applyPutRoute(path, body)
            }
        }
    }
}