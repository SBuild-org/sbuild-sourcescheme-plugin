package org.sbuild.plugins.sourcescheme

import org.sbuild._
import org.sbuild.internal.I18n

class SourceSchemeHandler(mappings: Seq[SourceSchemeMapping])(implicit project: Project) extends SchemeHandler {

  override def localPath(schemeCtx: SchemeHandler.SchemeContext): String = {
    val path = schemeCtx.path
    mappings.toIterator.map(m => m.map(path)).find(m => m.isDefined) match {
      case Some(Some(m)) => m
      case _ =>
        val i18n = I18n[SourceSchemeHandler]
        val msg = i18n.preparetr("Cannot find a mapping for target: \"{0}\".", schemeCtx)
        throw new TargetNotFoundException(msg.notr, null, msg.tr)
    }
  }

}
