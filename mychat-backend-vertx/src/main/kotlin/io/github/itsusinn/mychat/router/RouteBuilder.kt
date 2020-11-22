package io.github.itsusinn.mychat.router

import io.vertx.core.Vertx
import io.vertx.ext.web.Router

interface RouteBuilder { suspend fun create(vertx: Vertx): Router }