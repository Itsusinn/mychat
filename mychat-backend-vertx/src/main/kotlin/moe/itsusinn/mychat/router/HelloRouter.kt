package moe.itsusinn.mychat.router

import io.vertx.core.Vertx
import io.vertx.ext.web.Router

object HelloRouter:RouteBuilder{
   override suspend fun create(vertx: Vertx):Router{
      val router = Router.router(vertx)

      router.route("/hello").handler {
         println("hello")
         it.response().html().end("hello world")
      }


      return router
   }
}
