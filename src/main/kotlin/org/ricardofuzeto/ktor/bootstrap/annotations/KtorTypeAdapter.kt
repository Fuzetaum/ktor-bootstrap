package org.ricardofuzeto.ktor.bootstrap.annotations

import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeElementsScanner
import org.reflections.scanners.TypeAnnotationsScanner
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import org.ricardofuzeto.ktor.bootstrap.ktorServer.applyTypeAdapter

/**
 * Marks a given class as a type adapter, so GSON is able to use it for a given type. Only adding this annotation to
 *  a TypeAdapter class is enough: ktor-bootstrap will automatically detect the adapter's type and map it into GSON
 *  configuration.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class KtorTypeAdapter

private val reflections = Reflections(
    ConfigurationBuilder()
        .setUrls(ClasspathHelper.forPackage(""))
        .setScanners(TypeElementsScanner(), SubTypesScanner(), TypeAnnotationsScanner())
)

internal fun scanTypeAdapterAnnotations() {
    val adapters = reflections.getTypesAnnotatedWith(KtorTypeAdapter::class.java)
    for(adapter in adapters) {
        applyTypeAdapter(adapter.kotlin)
    }
}