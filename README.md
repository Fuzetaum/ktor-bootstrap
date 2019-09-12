# About

Wrapper library to provide capabilities for a minimalistic and factorized Ktor web service setup.

# Setup

This library is published via [Bintray JCenter](https://bintray.com/bintray/jcenter) OSS program. In order to be able to download `ktor-bootstrap` successfully, add JCenter to your repositories list:

```
Gradle
repositories {  
   jcenter()  
}

Maven
<repositories>
    <repository>
        <id>jcenter</id>
        <url>https://jcenter.bintray.com</url>
    </repository>
</repositories>
```

After that, just add this library as a dependency and you're all set up!

```
Gradle
dependencies {
    implementation 'org.ricardofuzeto.ktor-bootstrap:<ktor-bootstrap.version>'
}

Maven
<dependency>
    <groupId>org.ricardofuzeto</groupId>
    <artifactId>ktor-bootstrap</artifactId>
    <version>${ktor-bootstrap.version}</version>
</dependency>
```

# Creating your Ktor web server

## Creating and starting the web server

`Ktor-bootstrap` uses Netty as its server type. So, the only needed pre-run configuration is to set up which port you want your application to listen to. To do so, add to your environment the variable *ktor.port*, and the desired port.

After this, initialize the server in your source code, and sit back and watch the magic being done! :)

```
import org.ricardofuzeto.ktor.bootstrap.KtorBootstrap

fun main() {
    KtorBootstrap.init()
}
```

Note the importance of running the method `init()` in the `main` of your application: while `init` is not called, your ktor server will not be created and started. Thus, it is recommended that you call this method as early as possible (the library also does some preprocessing in the back, and this might take a while to complete).

## Adding routes

To map routes into `ktor-bootstrap` managed server, two steps are necessary for each route:

First, you must write the function that will handle requests received in each mapped resource. `Ktor-bootstrap` simplifies this, by allowing you to write these functions as if they were simple method calls, without having to deal with any `ktor` specific properties and methods.

Let's say you want to map a route `/posts` in a blog web server, that returns all posts from your blog on a GET request. Its handling function would look like this:

```
fun getAllPosts() = database.getAllPosts()
```

Here comes the second step of mapping a route to `ktor-bootstrap`: annotating your handling methods with the appropriate route annotation. These are basically annotations that explicitly tell which HTTP method is mapped into this function, for a given resource. Since we're just GETting a list of posts, the GET annotation is what we want:

```
import org.ricardofuzeto.ktor.bootstrap.annotations.KtorBootstrapGetRoute

@KtorBootstrapGetRoute("/posts")
fun getAllPosts() = database.getAllPosts()
```

Note that the mapped resource (URL to be mapped by Ktor) is given inside the parentheses. This means that each mapped resource in your application is annotated by one of the `ktor-bootstrap` annotations, explicitly telling which HTTP method is being mapped along with its URL as well. This is what makes your source code to be more readable regarding mapped resources.

These are all available mapping annotations from `ktor-bootstrap` (all of them are used in the same way as described above):

```
@KtorBootstrapDeleteRoute
@KtorBootstrapGetRoute
@KtorBootstrapPatchRoute
@KtorBootstrapPostRoute
@KtorBootstrapPutRoute
```

## Define type adapters

This library uses [GSON](https://github.com/google/gson) as the default body parser, which implies in it only accepting and producing JSON-formatted bodies. However, by default GSON expects that all properties of a given type to appear on a request's body, forcing values to be sent as `null`. Also, GSON doesn't easily recognize default values, attempting to set them to `null` as well.

GSON provides a mean to configure personalized processing of values from JSON documents, by using custom [type adapters](https://www.tutorialspoint.com/gson/gson_custom_adapters.htm). This capability is also supported by ktor, and `ktor-bootstrap` also provides a way to configure this in a minimalistic, verbose fashion.