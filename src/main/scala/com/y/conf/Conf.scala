package com.y.conf

import com.typesafe.config._
//import simplelib._

class Conf extends App {
  val conf = ConfigFactory.load()
  println("The answer is: " + conf.getString("simple-app.answer"))
}
