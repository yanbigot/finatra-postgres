package com.y.controller

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

class HelloController extends Controller {

  get("/hello") { request: Request =>
    "Fitman says hello"
  }

}
