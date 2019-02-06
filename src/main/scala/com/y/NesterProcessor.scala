package com.y

import net.liftweb.json._
import net.liftweb.json.Serialization.write

class NesterProcessor {

  type Row = Map[String, AnyRef]
  type Rows = Seq[Row]
  type Child = Map[String, Rows]
  type Children = Map[String, Child]
  type ResultSet = Child

  case class ChildConfAndRows(conf: Nest, rows: Rows)
  case class NestFunction(name: String, expression: String)
  case class Nest( display: String,
                   source: String,
                   keys: String,
                   parentKeys: String,
                   children: Option[Seq[Nest]],
                   functions: Option[Seq[NestFunction]])

  def nestChildren(parentRow: Row, parentConf: Nest, rs: ResultSet): Row = {
    println(s"""doing parent ${parentConf.display} -> $parentRow""")

    parentConf
      .children match {
      //some children-> processing
      case Some(childrenConf) =>
        println(s""" ++++++ ${childrenConf.map(pc => pc.display).mkString(", ")}""")
        val childrenRows = childrenConf
          //the given child defined in configuration exists in the result set
          .filter(childConf => rs.keySet.contains(childConf.source))
          //ensuring the parentRow contains the leftKey property
          .filter(childConf => parentRow.keySet.contains(childConf.parentKeys))
          //fetch the child rows
          .map(childConf => ChildConfAndRows(childConf, rs.get(childConf.source).get))
          //ensuring the child rows is not empty
          .filterNot(childConfAndRows => childConfAndRows.rows.isEmpty)
          //find matching child rows (the join/nest)
          .map(childConfAndRows => this.matchingChildRows(parentRow, childConfAndRows))
          //iterate on each child row and recurse with grand children (add if exists)
          .map(childConfAndRows => this.nestGrandChildren(rs, childConfAndRows))
          //excluding children with empty rows
          .filter(childConfAndRows => childConfAndRows.rows.nonEmpty)
          .map(childConfAndRows => this.buildChild(childConfAndRows))//.toMap

          childrenRows.foldLeft(parentRow)((prev, cur) => this.nestChildRowsToParentRow(prev, cur))//adding each recursed child to parent

      //no children, returning the parent for it not to be discarded
      case None => parentRow
      //whatever happens at least the parent row is returned
    }
  }

  def buildChild(childConfAndRows: ChildConfAndRows) = {
    childConfAndRows.conf.functions match {
      case Some(functions) =>
        childConfAndRows.conf.display -> childConfAndRows.rows //TODO here implements display function, filter on auth fields, conditionnal display
      case None =>
        childConfAndRows.conf.display -> childConfAndRows.rows
    }
  }

  def nestGrandChildren(rs: ResultSet, childConfAndRows: ChildConfAndRows) = {
    val childConf = childConfAndRows.conf
    val childRows =
      childConfAndRows.rows
      .map(childRow => this.nestChildren(childRow, childConf, rs))
    ChildConfAndRows(childConf, childRows)
  }

  def nestChildRowsToParentRow(prev: Row, cur: (String, Rows)): Row = {
    prev ++ Map(cur._1 -> cur._2)
  }

  def matchingChildRows(parentRow: Row, childConfAndRows: ChildConfAndRows) = {
    val childConf = childConfAndRows.conf
    val lKeyValue = parentRow(childConf.parentKeys)
    println(s"""    matching in: ${parentRow} with child ${childConf.source} on ${childConf.keys} == ${childConf.parentKeys}""")
    val childRows = childConfAndRows.rows
      .filter(childRow => childRow.keySet.contains(childConf.keys))
      .filter(childRow => childRow(childConf.keys) == lKeyValue)
    println(s"""    matching out: ${childRows}""")
    ChildConfAndRows(childConf, childRows)
  }

  def testGetChildren() = {

    val notExistChild = Nest(display = "NE", source = "SESSION_PARTS", keys = "sessionId", parentKeys = "sessionId", children = None, functions = None)
    val sessionPart = Nest(display = "sessionPartS", source = "SESSION_PARTS", keys = "sessionId", parentKeys = "sessionId", children = Some(Seq(notExistChild))
      , functions = None)
    val session = Nest(display = "sessionS", source = "SESSIONS", keys = "trainingId", parentKeys = "trainingId", children = Some(Seq(sessionPart)), functions = None)
    val training = Nest(display = "trainingS", source = "TRAININGS", keys = "trainingId", parentKeys = "trainingId", children = None, functions = None)
    val trainingIds = Nest(display = "trainingIdS", source = "CURRICULUMS", keys = "curriculumId", parentKeys = "trainingId", children = None, functions = None)
    val flw = Nest(display = "followupS", source = "FOLLOWUPS",  keys = "igg", parentKeys = "igg", children = Some(Seq(training, session, trainingIds)), functions = None)
    val emp = Nest(display = "employeeS", source = "EMPLOYEES", keys = "igg",  parentKeys = null, children = Some(Seq(flw)), functions = None)

    val rs: Map[String, Seq[Map[String, String]]] =
      Map(
        "SESSIONS" -> Seq(
          Map("trainingId" -> "t1", "sessionId" -> "s1", "ent" -> "SESSIONS"),
          Map("trainingId" -> "t1", "sessionId" -> "s2", "ent" -> "SESSIONS"),
          Map("trainingId" -> "tNESession", "sessionId" -> "BAD", "ent" -> "SESSIONS"),//bad
          Map("x" -> "XXXXXXXXXXXXXXXXXXXXXXXXXXxxxxx", "y" -> "ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ")
        )
        ,
        "FOLLOWUPS" -> Seq(
          Map("trainingId" -> "t1", "igg" -> "igg1", "ent" -> "FOLLOWUPS")
          ,
          Map("trainingId" -> "curriculum_1", "igg" -> "igg1", "ent" -> "FOLLOWUPS")
          ,
          Map("trainingId" -> "t_cur_child", "igg" -> "igg1", "ent" -> "FOLLOWUPS")
          ,
          Map("trainingId" -> "t9999NE", "igg" -> "igg1", "ent" -> "FOLLOWUPS")
        )
        ,
        "EMPLOYEES" -> Seq(
          Map("name" -> "Mark", "igg" -> "igg1", "ent" -> "EMPLOYEES")
            ,
          Map("name" -> "NE", "igg" -> "iggZ", "ent" -> "EMPLOYEES")
        )
        ,
        "TRAININGS" -> Seq(
          Map("trainingId" -> "t1", "title" -> "title1", "ent" -> "TRAININGS")
            ,
          Map("trainingId" -> "curriculum_1", "title" -> "title1", "ent" -> "TRAININGS")
            ,
          Map("trainingId" -> "t_cur_child", "title" -> "title1", "ent" -> "TRAININGS")
            ,
          Map("trainingId" -> "tNE00", "title" -> "title1", "ent" -> "TRAININGS")
        )
        ,
        "SESSION_PARTS" -> Seq(
          Map("name" -> "part", "sessionId" -> "s1", "ent" -> "SESSION_PARTS")
            ,
          Map("name" -> "part", "sessionId" -> "s1", "ent" -> "SESSION_PARTS")
        )
        ,
        "CURRICULUMS" -> Seq(
          Map("curriculumId" -> "curriculum_1", "trainingId" -> "t_cur_child", "ent" -> "CURRICULUMS")
            ,
          Map("curriculumId" -> "", "trainingId" -> "curriculum_1", "ent" -> "CURRICULUMS")
        )
      )

    val resultOk = Seq(emp)
      .flatMap(rootConf => rs(rootConf.source).asInstanceOf[Rows])
      .map(rootRow => this.nestChildren(rootRow, emp, rs))
    println(resultOk)
    resultOk
  }

  def go(): Unit ={
    implicit val formats = DefaultFormats
    println{ write(testGetChildren)}
  }

  def main(args: Array[String]): Unit = {
    go
  }
}
