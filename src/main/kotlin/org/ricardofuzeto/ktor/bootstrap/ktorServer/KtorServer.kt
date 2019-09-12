package org.ricardofuzeto.ktor.bootstrap.ktorServer

import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import io.ktor.application.call
import io.ktor.application.feature
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
import org.ricardofuzeto.ktor.bootstrap.annotations.scanTypeAdapterAnnotations
import org.ricardofuzeto.ktor.bootstrap.environment.Environment
import org.ricardofuzeto.ktor.bootstrap.log.Log
import java.lang.reflect.Method
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

private lateinit var server: ApplicationEngine

private var gsonBuilder = GsonBuilder()

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
    scanTypeAdapterAnnotations()
}

internal fun mapDeleteRoute(path: String, body: Method) {
    server.application.routing {
        delete(path) {
            Log.info("Request received: DELETE \"$path\"${if (call.parameters.isEmpty()) "" else ", parameters=${call.parameters}"}\"")
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
            Log.info("Request received: GET \"$path\"${if (call.parameters.isEmpty()) "" else ", parameters=${call.parameters}"}")
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
            Log.info("Request received: PATCH \"$path\"${if (call.parameters.isEmpty()) "" else ", parameters=${call.parameters}"}\"")
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
            Log.info("Request received: POST \"$path\"${if (call.parameters.isEmpty()) "" else ", parameters=${call.parameters}"}\"")
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
            Log.info("Request received: PUT \"$path\"${if (call.parameters.isEmpty()) "" else ", parameters=${call.parameters}"}\"")
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

internal fun <T: Any> applyTypeAdapter(adapter: KClass<T>) {
    gsonBuilder.apply { registerTypeAdapter(adapter.supertypes[0].arguments[0].type!!::class.java, adapter.createInstance()) }
}

internal fun installGson() {
    server.application.install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
}