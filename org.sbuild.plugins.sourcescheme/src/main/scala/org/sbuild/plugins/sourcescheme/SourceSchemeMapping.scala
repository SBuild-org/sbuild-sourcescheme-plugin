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
  /**
   * Creates a [[SourceSchemeMapping]] from a 2-tuple.
   */
  def apply(mapping: (String, String)): SourceSchemeMapping = new SourceSchemeMapping {
    override def map(path: String): Option[String] = if (mapping._1 == path) Some(mapping._2) else None
  }
  //  /**
  //   * Creates a [[SourceSchemeMapping]] from multiple 2-tuples.
  //   */
  //  def apply(mapping: (String, String)*): SourceSchemeMapping = new SourceSchemeMapping {
  //    override def map(path: String): Option[String] = mapping.find(_._1 == path).map(_._2)
  //  }
  /**
   * Creates a [[SourceSchemeMapping]] from a function `String => Option[String]`.
   */
  def apply(mapping: String => Option[String]): SourceSchemeMapping = new SourceSchemeMapping {
    override def map(path: String): Option[String] = mapping(path)
  }
  /**
   * Creates a [[SourceSchemeMapping]] from a partial function.
   */
  def apply(mapping: PartialFunction[String, String]): SourceSchemeMapping = new SourceSchemeMapping {
    override def map(path: String): Option[String] = if (mapping.isDefinedAt(path)) Some(mapping(path)) else None
  }

  implicit def fromFunction(function: String => Option[String]): SourceSchemeMapping = apply(function)
  implicit def fromPartialFunction(partialFunction: PartialFunction[String, String]): SourceSchemeMapping = apply(partialFunction)
  implicit def fromTuple2(tuple: (String, String)): SourceSchemeMapping = apply(tuple)
}
