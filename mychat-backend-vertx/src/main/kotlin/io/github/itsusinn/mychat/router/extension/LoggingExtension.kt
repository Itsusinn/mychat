package io.github.itsusinn.mychat.router.extension

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


private val loggerCache = mutableMapOf<Any, Logger>()

val Any.logger:Logger
   get() =
      loggerCache.getOrPut(this){ LoggerFactory.getLogger(this.javaClass) }