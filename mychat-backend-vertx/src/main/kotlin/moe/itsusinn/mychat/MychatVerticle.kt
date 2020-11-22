package moe.itsusinn.mychat

import io.vertx.kotlin.coroutines.CoroutineVerticle
import moe.itsusinn.mychat.router.AuthenticationRequestHandler
import moe.itsusinn.mychat.router.HelloRouter

class MychatVerticle : CoroutineVerticle() {

   override suspend fun start() {
      vertx
         .createHttpServer()
         .requestHandler(HelloRouter.create(vertx))
         //.requestHandler(AuthenticationRequestHandler.create(vertx))
         .listen(7433)
   }
}
