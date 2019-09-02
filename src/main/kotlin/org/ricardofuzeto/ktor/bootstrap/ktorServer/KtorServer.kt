package org.ricardofuzeto.ktor.bootstrap.ktorServer

import io.ktor.application.ApplicationCall
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.routing.*
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.pipeline.PipelineContext
import org.ricardofuzeto.ktor.bootstrap.environment.Environment
import org.ricardofuzeto.ktor.bootstrap.log.Log

private lateinit var server: ApplicationEngine

internal fun initKtorServer(port: Int, contentType: String) {
    server = embeddedServer(Netty, port) {
        when(contentType) {
            Environment.ENVIRONMENT_CONTENT_TYPE_JSON -> {
                Log.info("Configured requests' body type to \"${Environment.ENVIRONMENT_CONTENT_TYPE_JSON}\"")
                install(ContentNegotiation) {
                    gson {
                        setPrettyPrinting()
                    }
                }
            }
            else -> {
                Log.info("Configuration \"${Environment.ENVIRONMENT_CONTENT_TYPE}\" not found. Expected values are: \"${Environment.ENVIRONMENT_CONTENT_TYPE_JSON}\"")
                Log.info("Using default value \"${Environment.ENVIRONMENT_CONTENT_TYPE_JSON}\"")
                install(ContentNegotiation) {
                    gson {
                        setPrettyPrinting()
                    }
                }
            }
        }
    }
    server.start(wait = false)
    Log.info("Ktor server started successfully with properties: port=$port")
}

internal fun applyDeleteRoute(path: String, body: suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit) {
    server.application.routing { delete(path, body) }
}

internal fun applyHeadRoute(path: String, body: suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit) {
    server.application.routing { head(path, body) }
}

internal fun applyGetRoute(path: String, body: suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit) {
    server.application.routing { get(path, body) }
}

internal fun applyOptionsRoute(path: String, body: suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit) {
    server.application.routing { options(path, body) }
}

internal fun applyPatchRoute(path: String, body: suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit) {
    server.application.routing { patch(path, body) }
}

internal fun applyPostRoute(path: String, body: suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit) {
    server.application.routing { post(path, body) }
}

internal fun applyPutRoute(path: String, body: suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit) {
    server.application.routing { put(path, body) }
}
