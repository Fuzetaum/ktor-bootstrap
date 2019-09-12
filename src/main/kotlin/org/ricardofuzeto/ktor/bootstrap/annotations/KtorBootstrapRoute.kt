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

/**
 * Marks the annotated method as the body of a DELETE request, in the given "path" resource. Using this annotation will
 *  tell ktor-bootstrap to map a new endpoint in your server at the "path" location, and to run the annotated method
 *  in order to resolve each request.
 *
 * @param path resource path that this method will be bound to
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class KtorBootstrapDeleteRoute(val path: String)

/**
 * Marks the annotated method as the body of a GET request, in the given "path" resource. Using this annotation will
 *  tell ktor-bootstrap to map a new endpoint in your server at the "path" location, and to run the annotated method
 *  in order to resolve each request.
 *
 * @param path resource path that this method will be bound to
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class KtorBootstrapGetRoute(val path: String)

/**
 * Marks the annotated method as the body of a PATCH request, in the given "path" resource. Using this annotation will
 *  tell ktor-bootstrap to map a new endpoint in your server at the "path" location, and to run the annotated method
 *  in order to resolve each request.
 *
 * @param path resource path that this method will be bound to
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class KtorBootstrapPatchRoute(val path: String)

/**
 * Marks the annotated method as the body of a POST request, in the given "path" resource. Using this annotation will
 *  tell ktor-bootstrap to map a new endpoint in your server at the "path" location, and to run the annotated method
 *  in order to resolve each request.
 *
 * @param path resource path that this method will be bound to
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class KtorBootstrapPostRoute(val path: String)

/**
 * Marks the annotated method as the body of a PUT request, in the given "path" resource. Using this annotation will
 *  tell ktor-bootstrap to map a new endpoint in your server at the "path" location, and to run the annotated method
 *  in order to resolve each request.
 *
 * @param path resource path that this method will be bound to
 */
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
        mapDeleteRoute(route.getAnnotation(KtorBootstrapDeleteRoute::class.java).path, route)
    }
}

private fun scanGetRouteAnnotations() {
    val routes = reflections.getMethodsAnnotatedWith(KtorBootstrapGetRoute::class.java)
    for(route in routes) {
        mapGetRoute(route.getAnnotation(KtorBootstrapGetRoute::class.java).path, route)
    }
}

private fun scanPatchRouteAnnotations() {
    val routes = reflections.getMethodsAnnotatedWith(KtorBootstrapPatchRoute::class.java)
    for(route in routes) {
        mapPatchRoute(route.getAnnotation(KtorBootstrapPatchRoute::class.java).path, route)
    }
}

private fun scanPostRouteAnnotations() {
    val routes = reflections.getMethodsAnnotatedWith(KtorBootstrapPostRoute::class.java)
    for(route in routes) {
        mapPostRoute(route.getAnnotation(KtorBootstrapPostRoute::class.java).path, route)
    }
}

private fun scanPutRouteAnnotations() {
    val routes = reflections.getMethodsAnnotatedWith(KtorBootstrapPutRoute::class.java)
    for(route in routes) {
        mapPutRoute(route.getAnnotation(KtorBootstrapPutRoute::class.java).path, route)
    }
}

internal fun scanRouteAnnotations() {
    scanDeleteRouteAnnotations()
    scanGetRouteAnnotations()
    scanPatchRouteAnnotations()
    scanPostRouteAnnotations()
    scanPutRouteAnnotations()
}