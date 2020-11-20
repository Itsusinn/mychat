package moe.itsusinn.mychat

import io.vertx.core.Vertx

class Main {
   companion object{
      @JvmStatic fun main(args: Array<String>){
         val vertx = Vertx.vertx()
         vertx.deployVerticle(MainVerticle())
      }
   }
}