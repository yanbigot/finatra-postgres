package com.y

object App {

  type Row = Map[String, AnyRef]
  type Rows = Seq[Row]
  type Child = Map[String, Rows]
  type Children = Map[String, Child]
  type ResultSet = Child

  /**
    * "display": "training",
    * "source": "TRAINING_TYPE",
    * "keys": "trainingId",
    * "parentKeys": "trainingId",
    * "functions": null,
    * "children": null

    */
  case class Conf(name: String, right: String, left: String, rKey: String, lKey: String, children: Option[Seq[Conf]])
  case class NestFunction(name: String)
  case class Nest(
                   display: String,
                   source: String,
                   keys: String,
                   parentKeys: String,
                   lKey: String,
                   children: Option[Seq[Nest]],
                   functions: Option[Seq[NestFunction]])

  def getChildren(parentRow: Row, parentConf: Conf, rs: ResultSet): Row = {
    println(s"""doing ${parentConf.name} -> $parentRow""")
    parentConf
      .children match {
      //some children: processing
      case Some(childrenConf) =>
        val childrenRows = childrenConf
          //the given child defined in configuration exists in the result set
          .filter(childConf => rs.keySet.contains(childConf.right))
          //ensuring the parentRow contains the leftKey property
          .filter(childConf => parentRow.keySet.contains(childConf.lKey))
          //fetch the child rows
          .map(childConf => (childConf, rs.get(childConf.right).get))
          //ensuring the child rows is not empty
          .filterNot(childConfAndRows => childConfAndRows._2.isEmpty)
          //find matching child rows (the join/nest)
          .map(childConfAndRows => this.matchingChildRows(parentRow, childConfAndRows))
          //iterate on each child row and recurse with grand children (add if exists)
          .map(childConfAndRows => this.getGrandChildren(rs, childConfAndRows))
          //excluding children with empty rows
          .filter(childConfAndRows => childConfAndRows._2.nonEmpty)
          .map(childConfAndRows => childConfAndRows._1.name -> childConfAndRows._2).toMap

          childrenRows.foldLeft(parentRow)((prev, cur) => this.rowPlusChild(prev, cur))//adding each recursed child to parent
      //no children, returning the parent for it not to disappear
      case None => parentRow
        //whatever happens at least the parent row is returned

    }
  }

  def getGrandChildren(rs: ResultSet, childConfAndRows: (Conf, Seq[Row])) = {
    val childConf = childConfAndRows._1
    val childRows =
      childConfAndRows._2
      .map(childRow => this.getChildren(childRow, childConf, rs))
    (childConf, childRows)
  }

  def rowPlusChild(prev: Row, cur: (String, Rows)): Row = {
    prev ++ Map(cur._1 -> cur._2)
  }

  private def matchingChildRows(parentRow: Row, childConfAndRows: (Conf, Rows)) = {
    val childConf = childConfAndRows._1
    val lKeyValue = parentRow(childConf.lKey)
    val childRows =
      childConfAndRows._2
      .filter(childRow => childRow.keySet.contains(childConf.rKey))
      .filter(childRow => childRow(childConf.rKey) == lKeyValue)
    (childConf, childRows)
  }

  def testGetChildren(): Unit = {

    val notExistChild = Conf("sessionPart", right = "sessionPart", left = "followup", rKey = "sessionId", lKey = "sessionId", children = None)
    val sessionPart = Conf("sessionPart", right = "sessionPart", left = "followup", rKey = "sessionId", lKey = "sessionId", children = Some(Seq(notExistChild)))
    val session = Conf("session", right = "session", left = "followup", rKey = "trainingId", lKey = "trainingId", children = Some(Seq(sessionPart)))
    val training = Conf("training", right = "training", left = "followup", rKey = "trainingId", lKey = "trainingId", children = None)
    val flw = Conf("followups", right = "followup", left = "employee", rKey = "igg", lKey = "igg", children = Some(Seq(session, training)))
    val emp = Conf("emp", right = "", left = "employee", rKey = "", lKey = "igg", children = Some(Seq(flw)))

    val rs: Map[String, Seq[Map[String, String]]] =
      Map(
        "session" -> Seq(
          Map("trainingId" -> "t1", "sessionId" -> "s1"),
          Map("trainingId" -> "t1", "sessionId" -> "s2"),
          Map("x" -> "XXXXXXXXXXXXXXXXXXXXXXXXXXxxxxx", "y" -> "ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ")
        )
        ,
        "followup" -> Seq(
          Map("trainingId" -> "t1", "igg" -> "igg1"))
        ,
        "employee" -> Seq(
          Map("name" -> "Mark", "igg" -> "igg1")
        )
        ,
        "training" -> Seq(
          Map("trainingId" -> "t1", "title" -> "title1")
        )
        ,
        "sessionPart" -> Seq(
          Map("name" -> "part", "igg" -> "sessionId")
        )
      )

    val resultOk = emp.children.get
      .flatMap(rootConf => rs(rootConf.left).asInstanceOf[Rows])
      .map(rootRow => this.getChildren(rootRow, emp, rs))
    println(resultOk)

  }

  def main(args: Array[String]): Unit = {
    //    val root: Map[String, Any] = Map("parentIds" -> "5")
    //    //    val m    = Map("a" ->List(), "b" -> Seq())
    //    val children: Map[String, Any]
    //    = Map("emptyChild" -> List()
    //      , "child1" -> List(Map("childRow1" -> "5"))
    //      , "child2" -> List(Map("childRow2" -> "5"))
    //      , "child3" -> List(Map("childRow3" -> "5"))
    //    )
    //    val emptyChildren: Map[String, Any]
    //    = Map("a" -> List()
    //      , "b" -> List()
    //      , "c" -> List()
    //      , "d" -> Seq()
    //
    //    )
    //    println(root ++ children.filter(child => child._2.asInstanceOf[Seq[Any]].nonEmpty))
    //    println(root ++ emptyChildren.filter(child => child._2.asInstanceOf[Seq[Any]].nonEmpty))
    //
    //
    //    println(
    //      children
    //        .filter(child => child._2 match {
    //          case l: List[_] if l.nonEmpty => true
    //          case _ => false
    //        })
    //        .foldLeft(root)(( prev, cur ) => prev + cur)
    //    )
    //    println(
    //      emptyChildren
    //        .filter(child => child._2 match {
    //          case l: List[_] if l.nonEmpty => true
    //          case _ => false
    //        })
    //        .foldLeft(root)(( prev, cur ) => prev + cur))


    testGetChildren
  }
}
