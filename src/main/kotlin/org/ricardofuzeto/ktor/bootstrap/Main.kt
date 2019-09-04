package org.ricardofuzeto.ktor.bootstrap

import io.ktor.http.Parameters
import org.ricardofuzeto.ktor.bootstrap.annotations.KtorBootstrapGetRoute
import org.ricardofuzeto.ktor.bootstrap.annotations.KtorBootstrapPostRoute

data class TestClass(val value: String)

internal fun main() {
    KtorBootstrap.init()
}

@KtorBootstrapGetRoute("/{test}")
fun getTestParameter(parameters: Parameters) = TestClass(value = parameters["test"] ?: "")

@KtorBootstrapGetRoute("/test")
fun getTest() = "Test success"

@KtorBootstrapPostRoute("/test")
fun postTest(value: TestClass): TestClass {
    return value
}