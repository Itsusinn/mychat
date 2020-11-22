package io.github.itsusinn.mychat.router

import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.github.itsusinn.mychat.router.extension.authorized
import io.github.itsusinn.mychat.router.extension.endWithHtml
import io.github.itsusinn.mychat.router.extension.endWithJson
import io.github.itsusinn.mychat.router.extension.logger

object HelloRouter: RouteBuilder {
   override suspend fun create(vertx: Vertx):Router{
      val router = Router.router(vertx)

      router.route("/hello").handler {

         it.request().authorized()

         it.response().endWithHtml("""
            <h1>hello</h1>
            <h2>world</h2>
         """.trimIndent())
      }

      router.route("/json").handler {
         logger.info("/json invoked")
         it.response().endWithJson(mapOf("1" to "hello","2" to "hello"))
      }

      return router
   }
}
