package com.y.database

import com.google.inject.Inject
import com.twitter.finagle.Postgres
import com.twitter.finagle.postgres.PostgresClientImpl
import com.twitter.util.Await

class PostgresClient @Inject()(client: PostgresClientImpl) {
  def run( sql: String ) = {
    client.select(sql) {
      row =>
        println("helloRow")
        println(row.get[String]("hello"))
    }
  }
}
object PostgresClient{
  val client: PostgresClientImpl = Postgres.Client()
    .withCredentials("postgres", Some("postgres"))
    .database("postgres")
    .withSessionPool.maxSize(3) //optional; default is unbounded
    .withBinaryResults(true)
    .withBinaryParams(true)
    .newRichClient("localhost:5432")
  def main( args: Array[String] ): Unit = {
    //println(
      Await.result(
      client.select(
        """SELECT
          | *
          |FROM
          | information_schema.COLUMNS
          |WHERE
          | TABLE_NAME in ('employee', 'contract', 'test_table')
          | AND TABLE_SCHEMA = 'api'""".stripMargin
      ){r => r.getOption[String]("column_name")}
    )
    //)
    println("x")
    val x = client.select("select * from api.employee"){ r => r.get[String]("data_map")}
    println("y")
    val y = client.select("select * from api.contract"){r => r.get[String]("data_map")}
    println("z")
    val z = client.select("select * from api.test_table"){r => r.get[String]("hello")}
    println("a")
    val a = client.select("select * from api.employee"){r => r.get[String]("data_map")}
    println("!b")
    val b = client.select("select * from api.test_table"){r => r.get[String]("hello")}

    println("a")
    a onSuccess{
      res => println(s" *** a finished employee ${res.mkString}")
    }onFailure { exc =>
      println("failed employee :-(")
    }

    println("b")
    b onSuccess{
      res => println(s" *** b finished test_table ${res.mkString}")
    }onFailure { exc =>
      println("failed employee :-(")
    }

    println("x")
    x onSuccess{
      res => println(s" *** x finished employee ${res.mkString}")
    }onFailure { exc =>
      println("failed employee :-(")
    }

    println("y")
    y onSuccess{
      res => println(s" *** y finished contract ${res.mkString}")
    }onFailure { exc =>
      println("failed contract :-(")
    }

    println("z")
    z onSuccess{
      res => println(s" *** z finished test_table  ${res.mkString}")
    }onFailure { exc =>
      println("failed test_table :-(")
    }
  }
}
