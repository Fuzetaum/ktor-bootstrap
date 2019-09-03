package org.ricardofuzeto.ktor.bootstrap.ktorServer

import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.ricardofuzeto.ktor.bootstrap.annotations.scanRouteAnnotations
import org.ricardofuzeto.ktor.bootstrap.environment.Environment
import org.ricardofuzeto.ktor.bootstrap.log.Log
import java.lang.reflect.Method

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
        }
    }
    server.start(wait = false)
    Log.info("Ktor server created successfully with properties: port=$port")
    scanRouteAnnotations()
}

internal fun mapDeleteRoute(path: String, body: Method, numberOfParameters: Int) {
    server.application.routing {
        delete(path) {
            call.respond(when(numberOfParameters) {
                1 -> body.invoke(Any::class, call.receive())
                2 -> body.invoke(Any::class, call.receive(), call.parameters)
                else -> body.invoke(Any::class)
            })
        }
    }
    Log.info("Route mapped: DELETE \"$path\"")
}

internal fun mapGetRoute(path: String, body: Method, numberOfParameters: Int) {
    server.application.routing {
        get(path) {
            call.respond(when(numberOfParameters) {
                1 -> body.invoke(Any::class, call.parameters)
                else -> body.invoke(Any::class)
            })
        }
    }
    Log.info("Route mapped: GET \"$path\"")
}

internal fun mapPatchRoute(path: String, body: Method, numberOfParameters: Int) {
    server.application.routing { patch(path) {
        call.respond(when(numberOfParameters) {
            1 -> body.invoke(Any::class, call.receive())
            2 -> body.invoke(Any::class, call.receive(), call.parameters)
            else -> body.invoke(Any::class)
        })
    }
    }
    Log.info("Route mapped: PATCH \"$path\"")
}

internal fun mapPostRoute(path: String, body: Method, numberOfParameters: Int) {
    server.application.routing { post(path) {
        call.respond(when(numberOfParameters) {
            1 -> body.invoke(Any::class, call.receive())
            2 -> body.invoke(Any::class, call.receive(), call.parameters)
            else -> body.invoke(Any::class)
        })
    }
    }
    Log.info("Route mapped: POST \"$path\"")
}

internal fun mapPutRoute(path: String, body: Method, numberOfParameters: Int) {
    server.application.routing { put(path) {
        call.respond(when(numberOfParameters) {
            1 -> body.invoke(Any::class, call.receive())
            2 -> body.invoke(Any::class, call.receive(), call.parameters)
            else -> body.invoke(Any::class)
        })
    }
    }
    Log.info("Route mapped: PUT \"$path\"")
}