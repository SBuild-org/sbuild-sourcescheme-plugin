package org.sbuild.plugins.sourcescheme

case class SourceScheme(
    schemeName: String,
    mappings: Seq[SourceSchemeMapping] = Seq()) {
  def addMapping(mappings: SourceSchemeMapping*) = copy(mappings = this.mappings ++ mappings)
  override def toString(): String =
    s"${getClass().getSimpleName()}(schemeName=${schemeName},mappings=${mappings})"

}



