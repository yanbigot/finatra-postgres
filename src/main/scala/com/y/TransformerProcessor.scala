package com.y

import java.io

object TransformerProcessor {

  trait Condition

  case class Join(left: String, leftKey: String, operator: String, right: String, rightKey: String) extends Condition

  case class Value(left: String, leftKey: String, operator: String, values: String) extends Condition

  case class TParam(name: String, value: String)

  case class Transformation(position: String, name: String, params: Map[String, TParam])

  type Entities = Map[String, Seq[Map[String, Option[String]]]]
  type Entity = Map[String, Option[String]]

  val joinCondition = """([a-z]+).([a-z]+) (.*) ([a-z]+).([a-z]+)""".r
  val valueCondition = """([a-z]+).([a-z]+) (.*) (.*)""".r

  def cross(t: Transformation, entities: Entities): Unit = {
    val params = t.params
    val left = entities.get(params("left").value)
    val right = entities.get(params("right").value)

    (left, right) match {
      case (Some(l), Some(r)) =>
        for (l <- left;
             r <- right)
          l ++ r
      case _ =>
    }
  }

  def merge(t: Transformation, entities: Entities): Unit = {
    val params = t.params
    val left = entities.get(params("left").value)
    val leftKeys = params("leftKey").value.split(",")
    val right = entities.get(params("right").value)
    val rightKeys = params("rightKey").value.split(",")

    (left, right) match {
      case (Some(l), Some(r)) =>
        val rightDsByKey = r.groupBy(this.composeKey(_, rightKeys))
        l.map(lRow => lRow ++ rightDsByKey(this.composeKey(lRow, leftKeys)))
      case _ =>
    }
  }

//  def filter(t: Transformation, entities: Entities): Unit = {
//    val params = t.params
//    val left = entities.get(params("left").value)
//    val expression = params("expression").value
//    val conditions = expression.split("AND")
//      .map(condition => this.extractCondition(condition))
//      .filter(_.isLeft)
//      .filter(_.isLeft.isInstanceOf[Value])
//      .map(_.left.get)
//
//    left match {
//      case Some(rows) =>
//        rows.map(this.filterRow(_, conditions))
//      case None =>
//    }
//  }

  def filterRow(row: Entity, conditions: Seq[Condition]): Unit = {
    conditions.filter(_.isInstanceOf[Value])
      .map(v => {
        ???
      }
      )
  }

  def extractCondition(s: String): Either[Condition, Unit] = {
    s match {
      case joinCondition(left, leftKey, operator, right, rightKey) =>
        Left(Join(left, leftKey, operator, right, rightKey))

      case valueCondition(left, leftKey, operator, values) =>
        Left(Value(left, leftKey, operator, values))

      case _ => println("not found")
        Right("not found")
    }
  }

  def composeKey(row: Entity, keys: Seq[String]): Entity = {
    row.filter(kv => keys.contains(kv._1))
  }

  def rightJoin(left: Seq[Entity], right: Seq[Entity], condition: Join): Seq[Entity] = {
    leftJoin(right, left, condition)
  }

  def leftJoin(left: Seq[Entity], right: Seq[Entity], condition: Join): Seq[Entity] = {
    val rByKey= right.groupBy(_ (condition.rightKey)).map(kv => (kv._1.get, kv._2))
    left.map(l => {
      val lKeyValue = l(condition.leftKey).getOrElse("$")
      rByKey.get(lKeyValue) match {
        case r: Seq[Entity] =>
          r.foldLeft(l)((prev, cur) => prev ++ cur)
        case _ =>
          l
      }
    })
  }

  def innerJoin(left: Seq[Entity], right: Seq[Entity], condition: Join): Seq[Entity] = {
    val rByKey= right.groupBy(_ (condition.rightKey)).map(kv => (kv._1.get, kv._2))
    left.map(l => {
      val lKeyValue = l(condition.leftKey).getOrElse("$")
      rByKey.get(lKeyValue) match {
        case r: Seq[Entity] =>
         r.foldLeft(l)((prev, cur) => prev ++ cur)
      }
    })
  }


  def main(args: Array[String]): Unit = {
    extractCondition("a.b notin c.b") match {
      case Left(l) => println(l)
      case Right(s) => println(s)
    }
    extractCondition("x.y equals 1,2,3") match {
      case Left(l) => println(l)
      case Right(s) => println(s)
    }
    extractCondition("x.ddd") match {
      case Left(l) => println(l)
      case Right(s) => println(s)
    }

    val ls: Seq[Entity] = Seq(Map("key" -> Some("1"), "value" -> Some("v1")), Map("key" -> Some("2"), "value" -> Some("v2")))
    val rs: Seq[Entity] = Seq(Map("key" -> Some("1"), "value" -> Some("v1")), Map("key" -> Some("2"), "value" -> Some("v2")))

    println{
      innerJoin(ls, rs, Join("","key","=", "", "key"))
    }

  }
}
