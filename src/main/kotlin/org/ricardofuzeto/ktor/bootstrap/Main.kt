package org.ricardofuzeto.ktor.bootstrap

import io.ktor.http.Parameters
import org.ricardofuzeto.ktor.bootstrap.annotations.KtorBootstrapGetRoute
import org.ricardofuzeto.ktor.bootstrap.annotations.KtorBootstrapPostRoute

data class TestClass(val value: Int)

internal fun main() {
    KtorBootstrap.init()
}

@KtorBootstrapGetRoute("/{test}")
fun getRonaldo(parameters: Parameters) = parameters["test"]

@KtorBootstrapGetRoute("/test")
fun getJoilson() = "Joilson"

@KtorBootstrapPostRoute("/test")
fun postRonaldo(value: Any): Any {
    return value
}