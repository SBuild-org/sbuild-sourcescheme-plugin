package org.sbuild.plugins.sourcescheme

/**
 * Configuration for the `[[SourceSchemePlugin]]`
 *
 * Based on this configuration, the plugin will register a `[[SourceSchemeHandler]]` under the scheme given with `[[SourceScheme#schemeName]]`.
 * This SchemeHandler will resolve to source JARs, if it knows a mapping, or will fail.
 *
 * @param schemeName The name, under which the `[[SourceSchemeHandler]]` will be registered.
 * @param mappings A collection of mappings from a target name to some target name, which will resolve to the source jar.
 */
case class SourceScheme(
    schemeName: String,
    mappings: Seq[SourceSchemeMapping] = Seq()) {

  /**
   * Convenience method to add some mappings.
   */
  def addMapping(mappings: SourceSchemeMapping*) = copy(mappings = this.mappings ++ mappings)

  override def toString(): String =
    s"${getClass().getSimpleName()}(schemeName=${schemeName},mappings=${mappings})"

}



