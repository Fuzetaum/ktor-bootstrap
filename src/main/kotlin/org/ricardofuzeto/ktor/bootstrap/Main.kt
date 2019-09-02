package org.ricardofuzeto.ktor.bootstrap

import io.ktor.application.call
import io.ktor.response.respond

internal fun main() {
    KtorBootstrap.init()
    KtorBootstrap.addRoute(KtorRouteType.GET, "/") { call.respond("Ronaldo") }
    KtorBootstrap.addRoute(KtorRouteType.GET, "/test") { call.respond("Joilson") }
}
