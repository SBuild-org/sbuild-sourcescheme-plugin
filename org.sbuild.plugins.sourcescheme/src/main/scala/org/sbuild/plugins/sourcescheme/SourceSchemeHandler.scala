package org.sbuild.plugins.sourcescheme

import org.sbuild._
import org.sbuild.internal.I18n

/**
 * Caclulates the local path of a given target name based on it's mappings and configuration.
 *
 * @param mappings Sequence of known [[SourceSchemeMapping]]s used to determine the final local path of the corresponding sources.
 * @param if `true` also determine the localPath of the input path and apply the mapping onto it.
 */
class SourceSchemeHandler(mappings: Seq[SourceSchemeMapping], mapTargetFiles: Boolean)(implicit project: Project) extends SchemeHandler {

  override def localPath(schemeCtx: SchemeHandler.SchemeContext): String = {
    val path = schemeCtx.path

    val lazyEvalMappings = mappings.toIterator
    lazyEvalMappings.flatMap { m =>
      val mapped = Seq(m.map(path))
      val extra = if (mapTargetFiles)
        Seq(project.uniqueTargetFile(TargetRef(path)) match {
          case UniqueTargetFile(file, false, _) => m.map(file.getPath())
          case _ => None
        })
      else Seq()
      mapped ++ extra
    }

    val candidates = lazyEvalMappings.map(m => m.map(path))
    candidates.find(m => m.isDefined) match {
      case Some(Some(m)) => m
      case _ =>
        val i18n = I18n[SourceSchemeHandler]
        val msg = i18n.preparetr("Cannot find a mapping for target: \"{0}\".", schemeCtx)
        throw new TargetNotFoundException(msg.notr, null, msg.tr)
    }
  }

}
