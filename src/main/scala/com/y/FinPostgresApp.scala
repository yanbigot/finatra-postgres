package com.y

import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter
import com.y.controller.HelloController

object FinPostgresApp extends FinPostgresServer

class FinPostgresServer extends HttpServer {
  override protected def configureHttp(router: HttpRouter): Unit = {
    router.add[HelloController]
  }
}
