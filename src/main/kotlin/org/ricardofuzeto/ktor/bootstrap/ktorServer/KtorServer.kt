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

internal fun mapDeleteRoute(path: String, body: Method) {
    server.application.routing {
        delete(path) {
            val bodyType = if(body.parameterTypes.isNotEmpty()) body.parameterTypes[0].kotlin else String::class
            call.respond(when(body.parameterTypes.size) {
                1 -> body.invoke(body.declaringClass, call.receive(bodyType))
                2 -> body.invoke(body.declaringClass, call.receive(bodyType), call.parameters)
                else -> body.invoke(body.declaringClass)
            })
        }
    }
    Log.info("Route mapped: DELETE \"$path\"")
}

internal fun mapGetRoute(path: String, body: Method) {
    server.application.routing {
        get(path) {
            call.respond(when(body.parameterTypes.size) {
                1 -> body.invoke(body.declaringClass, call.parameters)
                else -> body.invoke(body.declaringClass)
            })
        }
    }
    Log.info("Route mapped: GET \"$path\"")
}

internal fun mapPatchRoute(path: String, body: Method) {
    server.application.routing {
        patch(path) {
            val bodyType = if(body.parameterTypes.isNotEmpty()) body.parameterTypes[0].kotlin else String::class
            call.respond(when(body.parameterTypes.size) {
                1 -> body.invoke(body.declaringClass, call.receive(bodyType))
                2 -> body.invoke(body.declaringClass, call.receive(bodyType), call.parameters)
                else -> body.invoke(body.declaringClass)
            })
        }
    }
    Log.info("Route mapped: PATCH \"$path\"")
}

internal fun mapPostRoute(path: String, body: Method) {
    server.application.routing {
        post(path) {
            val bodyType = if(body.parameterTypes.isNotEmpty()) body.parameterTypes[0].kotlin else String::class
            call.respond(when(body.parameterTypes.size) {
                1 -> body.invoke(body.declaringClass, call.receive(bodyType))
                2 -> body.invoke(body.declaringClass, call.receive(bodyType), call.parameters)
                else -> body.invoke(body.declaringClass)
            })
        }
    }
    Log.info("Route mapped: POST \"$path\"")
}

internal fun mapPutRoute(path: String, body: Method) {
    server.application.routing {
        put(path) {
            val bodyType = if(body.parameterTypes.isNotEmpty()) body.parameterTypes[0].kotlin else String::class
            call.respond(when(body.parameterTypes.size) {
                1 -> body.invoke(body.declaringClass, call.receive(bodyType))
                2 -> body.invoke(body.declaringClass, call.receive(bodyType), call.parameters)
                else -> body.invoke(body.declaringClass)
            })
        }
    }
    Log.info("Route mapped: PUT \"$path\"")
}