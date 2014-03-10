package org.sbuild.plugins.sourcescheme

import org.sbuild._

class SourceSchemePlugin(implicit project: Project) extends Plugin[SourceScheme] {
  override def create(name: String): SourceScheme = name match {
    case "" => create("source")
    case name => new SourceScheme(name)
  }

  override def applyToProject(instances: Seq[(String, SourceScheme)]): Unit = instances foreach {
    case (name, sourceScheme) =>
      SchemeHandler(sourceScheme.schemeName, new SourceSchemeHandler(sourceScheme.mappings))
  }
}
