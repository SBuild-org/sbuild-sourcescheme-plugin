package org.sbuild.plugins.sourcescheme

import org.scalatest.FreeSpec
import org.sbuild.test.TestSupport
import org.sbuild.SchemeHandler
import org.sbuild.TargetNotFoundException

class Tests extends FreeSpec {

  "test for mvn with partial function" in {
    implicit val p = TestSupport.createMainProject
    val m1: PartialFunction[String, String] = { case t if t.startsWith("mvn:") => s"${t};classifier=sources" }
    val handler = new SourceSchemeHandler(Seq(m1))
    assert(handler.localPath(SchemeHandler.SchemeContext("source", "mvn:test")) === "mvn:test;classifier=sources")
    intercept[TargetNotFoundException] {
      handler.localPath(SchemeHandler.SchemeContext("source", "test"))
    }
  }

  "test for mvn with function" in {
    implicit val p = TestSupport.createMainProject
    val m1 = { t: String =>
      t match {
        case t if t.startsWith("mvn:") => Some(s"${t};classifier=sources")
        case _ => None
      }
    }
    val handler = new SourceSchemeHandler(Seq(m1))
    assert(handler.localPath(SchemeHandler.SchemeContext("source", "mvn:test")) === "mvn:test;classifier=sources")
    intercept[TargetNotFoundException] {
      handler.localPath(SchemeHandler.SchemeContext("source", "test"))
    }
  }

}