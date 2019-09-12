package org.ricardofuzeto.ktor.bootstrap

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import io.ktor.http.Parameters
import org.ricardofuzeto.ktor.bootstrap.annotations.KtorBootstrapGetRoute
import org.ricardofuzeto.ktor.bootstrap.annotations.KtorBootstrapPostRoute
import org.ricardofuzeto.ktor.bootstrap.annotations.KtorTypeAdapter

internal data class TestClass(val value: String)

internal fun main() {
    KtorBootstrap.init()
}

@KtorBootstrapGetRoute("/test/{test}")
internal fun getTestParameter(parameters: Parameters) = TestClass(value = parameters["test"] ?: "")

@KtorBootstrapGetRoute("/test")
internal fun getTest() = "Test success"

@KtorBootstrapPostRoute("/test")
internal fun postTest(value: TestClass): TestClass {
    return value
}

@KtorTypeAdapter
internal class TestClassTypeAdapter: TypeAdapter<TestClass>() {
    override fun read(reader: JsonReader?): TestClass {
        var value: String? = null

        reader?.beginObject()
        while(reader?.hasNext() == true) {
            val key = reader.nextName()
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull()
                continue
            }
            when (key) {
                "value" -> value = reader.nextString()
            }
        }
        reader?.endObject()

        return when(value == null) {
            true -> TestClass(value = "")
            false -> TestClass(value = value)
        }
    }

    override fun write(out: JsonWriter?, value: TestClass?) {
        out?.apply {
            beginObject()
            value?.let {
                name("value").value(it.value)
            }
            endObject()
        }
    }
}