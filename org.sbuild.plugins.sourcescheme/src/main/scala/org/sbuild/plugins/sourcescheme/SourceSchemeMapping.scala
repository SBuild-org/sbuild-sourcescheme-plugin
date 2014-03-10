package org.sbuild.plugins.sourcescheme

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
