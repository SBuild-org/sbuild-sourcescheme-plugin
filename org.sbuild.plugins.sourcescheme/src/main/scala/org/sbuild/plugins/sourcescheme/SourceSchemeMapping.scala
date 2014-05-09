package org.sbuild.plugins.sourcescheme

/**
 * A mapping from target name to some optional other target name.
 * The mapping should return a `[[Some[String]]]` if this mapping knows the target which is able to
 * resolve the source jar for the given target name.
 * If this mapping cannot handle the given target name, `[[None]]` should be returned.
 */
sealed trait SourceSchemeMapping {
  def map(path: String): Option[String]
}

object SourceSchemeMapping {
  def apply(mapping: String => Option[String]): SourceSchemeMapping = new SourceSchemeMapping {
    override def map(path: String): Option[String] = mapping(path)
  }
  def apply(mapping: PartialFunction[String, String]): SourceSchemeMapping = new SourceSchemeMapping {
    override def map(path: String): Option[String] = if (mapping.isDefinedAt(path)) Some(mapping(path)) else None
  }

  implicit def fromFunction(mapping: String => Option[String]): SourceSchemeMapping = apply(mapping)
  implicit def fromPartialFunction(mapping: PartialFunction[String, String]): SourceSchemeMapping = apply(mapping)
}
