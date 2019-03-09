package com.y.hierarchy

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

object Hierarchy {

  def main(args: Array[String]): Unit = {
    val uos: Seq[Map[String, String]] = Seq(
      Map("id" -> "1", "localid" -> "OG1", "parentid" -> "", "localparentid" -> "", "cond" -> "ok", "name" -> "presidence"),
      Map("id" -> "2", "localid" -> "OG1", "parentid" -> "", "localparentid" -> "", "cond" -> "ok", "name" -> "presidence"),
      Map("id" -> "3", "localid" -> "OG2", "parentid" -> "1", "localparentid" -> "OG1", "cond" -> "ok", "name" -> "france"),
      Map("id" -> "4", "localid" -> "OG3", "parentid" -> "1", "localparentid" -> "OG1", "cond" -> "ok", "name" -> "lux"),
      Map("id" -> "99999", "localid" -> "OG3", "parentid" -> "1", "localparentid" -> "OG1", "cond" -> "ok", "name" -> "lux"),
      Map("id" -> "5", "localid" -> "OG4", "parentid" -> "1", "localparentid" -> "OG1", "cond" -> "ok", "name" -> "lux"),

      Map("id" -> "6", "localid" -> "OG5", "parentid" -> "1", "localparentid" -> "OG1", "cond" -> "ko", "name" -> "lux"),
      Map("id" -> "7", "localid" -> "OG6", "parentid" -> "1", "localparentid" -> "OG5", "cond" -> "ko", "name" -> "lux"),
      Map("id" -> "8", "localid" -> "OG7", "parentid" -> "1", "localparentid" -> "OG6", "cond" -> "ok", "name" -> "lux"),

      Map("id" -> "9", "localid" -> "OG8", "parentid" -> "1", "localparentid" -> "OG1", "cond" -> "ko", "name" -> "lux"),
      Map("id" -> "10", "localid" -> "OG9", "parentid" -> "1", "localparentid" -> "OG8", "cond" -> "ko", "name" -> "lux"),
      Map("id" -> "11", "localid" -> "OG10", "parentid" -> "1", "localparentid" -> "OG10", "cond" -> "ko", "name" -> "lux")


    )

    val tree = buildTree(uos.tail, MTree(uos.head))
    println(tree)

  }

  def prune(uos: Seq[Map[String, Any]]): Seq[Map[String, Any]] = {
    val parentIds = uos.map(_ ("parentId")).distinct
    val childIds = uos.map(_ ("id")).distinct

    if (parentIds.size == childIds.size)
      uos
    else {
      prune(uos.filter(uo => (childIds.contains(uo("parentId")) && parentIds.contains(uo("id")))))
    }
  }

  /**
    * choose parentlocalid
    * choose shortname
    * remove duplicates
    * remove orphans
    * prune
    */

  def removeSelfReference(l: Seq[Map[String, Any]]): Seq[Map[String, Any]] = {
    l.filterNot(ele => ele("localid") == ele("localparentid"))
  }

  def removeOrphans(l: Seq[Map[String, Any]], localParentIds: Seq[Map[String, Any]]): Seq[Map[String, Any]] = {
    l.filter(ele => localParentIds contains ele("localid"))
  }

  case class MTree[T](value: T, children: ArrayBuffer[MTree[T]]) {
//    def this(value: T) = this(value, ArrayBuffer())
    def find[V](search: T, functionOnValue: T => V, functionOnTree: T => V): Option[MTree[T]] = {
      val fv = functionOnValue(search)
      val ft = functionOnTree(value)
      if (functionOnValue(search) == functionOnTree(value))
        Some(this)
      else
        children.map(_.find(search, functionOnValue, functionOnTree)) match {
          case ArrayBuffer() => None
          case v@ArrayBuffer(_) => v.head
        }
    }
    def addChild(child: T): Unit = {
      children += MTree(child)
    }
    def addChild(child: MTree[T]): Unit = {
      children += child
    }
    override def toString = "M(" + value.toString + " {" + children.map(_.toString).mkString(",") + "})"
  }

  object MTree {
    def apply[T](value: T) = new MTree[T](value, ArrayBuffer())
//    def apply[T](value: T, children: ArrayBuffer[MTree[T]]) = new MTree(value, children)
  }

  def equalSelf[T <: Map[String, String]](m: T) = m("localid").hashCode

  def buildTree(l: Seq[Map[String, String]], tree: MTree[Map[String, String]]): MTree[Map[String, String]] = {
    l match {
      case Nil => tree
      case head :: tail =>
        //looking for self
        tree.find[Int](head, equalSelf, equalSelf) match {
          case Some(value) =>
            buildTree(tail, tree)
          case None =>
            //should insert
            tree.find[String](head, _ ("parentid"), _ ("localid")) match {
              case Some(parent) =>
                parent.addChild(head)
                buildTree(tail, tree)
              //new root
              case None =>
                tree.find[String](head, _ ("localid"), _ ("parentid")) match {
                  case Some(child) =>
                    val root = MTree(head)
                    root.addChild(child)
                    buildTree(tail, root)
                  case _ =>
                    buildTree(tail, tree)
                }
            }
        }

    }
  }

}
