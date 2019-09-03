package org.ricardofuzeto.ktor.bootstrap.annotations

import org.reflections.Reflections
import org.reflections.scanners.MethodAnnotationsScanner
import org.reflections.scanners.TypeAnnotationsScanner
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import org.ricardofuzeto.ktor.bootstrap.ktorServer.*
import org.ricardofuzeto.ktor.bootstrap.ktorServer.mapDeleteRoute
import org.ricardofuzeto.ktor.bootstrap.ktorServer.mapGetRoute
import org.ricardofuzeto.ktor.bootstrap.ktorServer.mapPatchRoute
import org.ricardofuzeto.ktor.bootstrap.ktorServer.mapPostRoute

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class KtorBootstrapDeleteRoute(val path: String)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class KtorBootstrapGetRoute(val path: String)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class KtorBootstrapPatchRoute(val path: String)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class KtorBootstrapPostRoute(val path: String)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class KtorBootstrapPutRoute(val path: String)

private val reflections = Reflections(ConfigurationBuilder()
    .setUrls(ClasspathHelper.forPackage(""))
    .setScanners(MethodAnnotationsScanner(), TypeAnnotationsScanner())
)

private fun scanDeleteRouteAnnotations() {
    val routes = reflections.getMethodsAnnotatedWith(KtorBootstrapDeleteRoute::class.java)
    for(route in routes) {
        mapDeleteRoute(route.getAnnotation(KtorBootstrapDeleteRoute::class.java).path, route, route.parameterTypes.size)
    }
}

private fun scanGetRouteAnnotations() {
    val routes = reflections.getMethodsAnnotatedWith(KtorBootstrapGetRoute::class.java)
    for(route in routes) {
        mapGetRoute(route.getAnnotation(KtorBootstrapGetRoute::class.java).path, route, route.parameterTypes.size)
    }
}

private fun scanPatchRouteAnnotations() {
    val routes = reflections.getMethodsAnnotatedWith(KtorBootstrapPatchRoute::class.java)
    for(route in routes) {
        mapPatchRoute(route.getAnnotation(KtorBootstrapPatchRoute::class.java).path, route, route.parameterTypes.size)
    }
}

private fun scanPostRouteAnnotations() {
    val routes = reflections.getMethodsAnnotatedWith(KtorBootstrapPostRoute::class.java)
    for(route in routes) {
        mapPostRoute(route.getAnnotation(KtorBootstrapPostRoute::class.java).path, route, route.parameterTypes.size)
    }
}

private fun scanPutRouteAnnotations() {
    val routes = reflections.getMethodsAnnotatedWith(KtorBootstrapPutRoute::class.java)
    for(route in routes) {
        mapPutRoute(route.getAnnotation(KtorBootstrapPutRoute::class.java).path, route, route.parameterTypes.size)
    }
}

internal fun scanRouteAnnotations() {
    scanDeleteRouteAnnotations()
    scanGetRouteAnnotations()
    scanPatchRouteAnnotations()
    scanPostRouteAnnotations()
    scanPutRouteAnnotations()
}