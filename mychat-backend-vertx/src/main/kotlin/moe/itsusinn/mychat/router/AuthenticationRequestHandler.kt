package moe.itsusinn.mychat.router

import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServerRequest

object AuthenticationRequestHandler:RequestHandlerBuilder {
   override fun create(vertx: Vertx): Handler<HttpServerRequest> {
      return Handler<HttpServerRequest>{
         println("aaa")
      }
   }

}