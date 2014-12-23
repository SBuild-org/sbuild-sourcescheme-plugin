package org.sbuild.plugins.sourcescheme

import org.scalatest.FreeSpec
import org.sbuild.test.TestSupport
import org.sbuild.SchemeHandler
import org.sbuild.TargetNotFoundException

class Tests extends FreeSpec {

  "test for mvn with partial function" in {
    implicit val p = TestSupport.createMainProject
    val m1: PartialFunction[String, String] = { case t if t.startsWith("mvn:") => s"${t};classifier=sources" }
    val handler = new SourceSchemeHandler(Seq(m1), false)
    assert(handler.localPath(SchemeHandler.SchemeContext("source", "mvn:test")) === "mvn:test;classifier=sources")
  }

  "test for mvn with partial function fail" in {
    implicit val p = TestSupport.createMainProject
    val m1: PartialFunction[String, String] = { case t if t.startsWith("mvn:") => s"${t};classifier=sources" }
    val handler = new SourceSchemeHandler(Seq(m1), false)
    val ex = intercept[TargetNotFoundException] {
      handler.localPath(SchemeHandler.SchemeContext("source", "test"))
    }
    assert(ex.getMessage === """Cannot find a mapping for target: "SchemeContext(source,test)".""")
  }

  "test for mvn with function" in {
    implicit val p = TestSupport.createMainProject
    val m1 = { t: String =>
      t match {
        case t if t.startsWith("mvn:") => Some(s"${t};classifier=sources")
        case _ => None
      }
    }
    val handler = new SourceSchemeHandler(Seq(m1), false)
    assert(handler.localPath(SchemeHandler.SchemeContext("source", "mvn:test")) === "mvn:test;classifier=sources")
  }

  "test for mvn with function fail" in {
    implicit val p = TestSupport.createMainProject
    val m1 = { t: String =>
      t match {
        case t if t.startsWith("mvn:") => Some(s"${t};classifier=sources")
        case _ => None
      }
    }
    val handler = new SourceSchemeHandler(Seq(m1), false)
    val ex = intercept[TargetNotFoundException] {
      handler.localPath(SchemeHandler.SchemeContext("source", "test"))
    }
    assert(ex.getMessage === """Cannot find a mapping for target: "SchemeContext(source,test)".""")
  }

  "test for mvn with tuple" in {
    implicit val p = TestSupport.createMainProject
    val handler = new SourceSchemeHandler(Seq("mvn:test" -> "mvn:test;classifier=sources"), false)
    assert(handler.localPath(SchemeHandler.SchemeContext("source", "mvn:test")) === "mvn:test;classifier=sources")
  }

  "test for mvn with tuple fail" in {
    implicit val p = TestSupport.createMainProject
    val handler = new SourceSchemeHandler(Seq("mvn:test" -> "mvn:test;classifier=sources"), false)
    val ex = intercept[TargetNotFoundException] {
      handler.localPath(SchemeHandler.SchemeContext("source", "test"))
    }
    assert(ex.getMessage === """Cannot find a mapping for target: "SchemeContext(source,test)".""")
  }

  //  "test recusive with localPath resolution enabled and handler registered" in {
  //    implicit val p = TestSupport.createMainProject
  //    val handler = new SourceSchemeHandler(Seq("mvn:test" -> "mvn:test;classifier=sources"), true)
  //    SchemeHandler("source", handler)
  //    assert(handler.localPath(SchemeHandler.SchemeContext("source", "source:mvn:test")) === "mvn:test;classifier=sources")
  //  }
  //
  //  "test recusive with localPath resolution enabled but handler not registered" in {
  //    implicit val p = TestSupport.createMainProject
  //    val handler = new SourceSchemeHandler(Seq("mvn:test" -> "mvn:test;classifier=sources"), true)
  //    val ex = intercept[TargetNotFoundException] {
  //      handler.localPath(SchemeHandler.SchemeContext("source", "source:mvn:test"))
  //    }
  //    assert(ex.getMessage === """Cannot find a mapping for target: "SchemeContext(source,source:mvn:test)".""")
  //  }
  //
  //  "test recusive with localPath resolution disabled and handler registered" in {
  //    implicit val p = TestSupport.createMainProject
  //    val handler = new SourceSchemeHandler(Seq("mvn:test" -> "mvn:test;classifier=sources"), false)
  //    SchemeHandler("source", handler)
  //    val ex = intercept[TargetNotFoundException] {
  //      handler.localPath(SchemeHandler.SchemeContext("source", "source:mvn:test"))
  //    }
  //    assert(ex.getMessage === """Cannot find a mapping for target: "SchemeContext(source,source:mvn:test)".""")
  //  }

}