package com.y

import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write

object FromUniqueFlatResultSetNester {

  case class Nest(name: String, parentKey: String, displayName: String, child: Option[Nest])
  case class GroupByConf(name: String, fields: Seq[String], key: String)

  def buildConf(): Unit ={

  }

  def grouper(fields: Seq[String], repo: Seq[Map[String, String]], key: String): Map[String, Seq[Map[String, String]]]= {
    repo.map(m => this.selectFields(m, fields)).distinct.groupBy(_ (key))
  }

  def process(): Unit ={
    //read conf
    val nestChildren =
      Nest("followupByIgg", "employee.igg", "followups",
        Some(Nest("trainingByTrainingId", "flw.trainingId", "trainings",
          Some(Nest("subjectTitlesByTrainingId", "training.trainingId", "subjects", None))
        )))
    //build query with conditions

    //get resultSet
    val resultSet = Seq(
      Map("employee.igg" -> "i_1","flw.igg" -> "i_1", "flw.trainingId" -> "t_1", "training.trainingId" -> "t_1", "training.desc" -> "A", "subject.titleFr" -> "tFr_1", "subject.trainingId" -> "t_1"),
      Map("employee.igg" -> "i_1","flw.igg" -> "i_1", "flw.trainingId" -> "t_1", "training.trainingId" -> "t_1", "training.desc" -> "A", "subject.titleFr" -> "tFr_2", "subject.trainingId" -> "t_1"),
      Map("employee.igg" -> "i_1","flw.igg" -> "i_1", "flw.trainingId" -> "t_2", "training.trainingId" -> "t_2", "training.desc" -> "A", "subject.titleFr" -> "tFr_1", "subject.trainingId" -> "t_2")
    )

    //prepare join with group by key
    val groupByConfs = Seq(
      GroupByConf("employeeByIgg", Seq("employee.igg"), "employee.igg"),
      GroupByConf("followupByIgg", Seq("flw.igg", "flw.trainingId"), "flw.igg"),
      GroupByConf("trainingByTrainingId", Seq("training.trainingId", "training.desc"), "training.trainingId"),
      GroupByConf("subjectTitlesByTrainingId", Seq("subject.trainingId", "subject.titleFr"), "subject.trainingId")
    )
                          //Name         grpKey     Rows
    val resultSetByJoinKey: Map[String, Map[String, Seq[Map[String, String]]]] = groupByConfs.map(
      c => {
        val r = this.grouper(c.fields, resultSet, c.key)
        c.name -> r
      }
    ).toMap
    //nest
    val rootName = groupByConfs(0).name
    val result =
      resultSetByJoinKey(rootName)
      .flatMap(_._2)
      .map(x => this.nestByConf(x, resultSetByJoinKey, nestChildren))

    //done
    implicit val formats = DefaultFormats
    println{ write(result)}
  }

  def main(args: Array[String]): Unit = {
     process()
  }

  def nestByConf(parent: Map[String, Any], childrenSet: Map[String, Map[String, Seq[Map[String, String]]]], n: Nest): Map[String, Any] = {
    val child = childrenSet(n.name)

    if (child.isEmpty || parent.isEmpty)
      parent
    else {
      val kVal: String = parent(n.parentKey).toString
      val children: Seq[Map[String, String]] = child(kVal)
      n.child match {
        case Some(c) => parent ++ Map(n.displayName -> children.map(x => this.nestByConf(x, childrenSet, c)))
        case None => parent ++ Map(n.displayName -> children)
      }
    }
  }

  def selectFields(m: Map[String, String], fields: Seq[String]): Map[String, String] = {
    m.filter(kv => fields contains kv._1)
  }

}
