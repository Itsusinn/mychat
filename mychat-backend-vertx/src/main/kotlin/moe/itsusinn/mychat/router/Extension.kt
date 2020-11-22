package moe.itsusinn.mychat.router

import io.vertx.core.http.HttpServerResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun HttpServerResponse.html() : HttpServerResponse {
   return this.putHeader("content-type","text/html")
}

fun HttpServerResponse.json() : HttpServerResponse {
   return this.putHeader("content-type","application/json; charset=utf-8")
}
fun HttpServerResponse.text() : HttpServerResponse {
   return this.putHeader("content-type","text/plain; charset=utf-8")
}

fun launch(
   context: CoroutineContext = EmptyCoroutineContext,
   start: CoroutineStart = CoroutineStart.DEFAULT,
   block: suspend CoroutineScope.() -> Unit
): Job {
   return GlobalScope.launch(context, start, block)
}
fun <T> async(
   context: CoroutineContext = EmptyCoroutineContext,
   start: CoroutineStart = CoroutineStart.DEFAULT,
   block: suspend CoroutineScope.() -> T
): Deferred<T> {
   return GlobalScope.async(context, start, block)
}
