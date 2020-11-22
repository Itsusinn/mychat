package moe.itsusinn.mychat.router

import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServerRequest

interface RequestHandlerBuilder {
   fun create(vertx: Vertx): Handler<HttpServerRequest>
}