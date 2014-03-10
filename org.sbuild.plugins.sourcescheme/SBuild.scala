import de.tototec.sbuild._

@version("0.7.1")
@classpath("mvn:org.sbuild:org.sbuild.plugins.sbuildplugin:0.3.0")
class SBuild(implicit _project: Project) {

  val namespace = "org.sbuild.plugins.sourcescheme"
  val version = "0.0.9000"

  val sbuildJar = Prop("sbuild.jar", "../../sbuild/org.sbuild/target/org.sbuild-0.7.2.9000.jar")

  import org.sbuild.plugins.sbuildplugin._
  Plugin[SBuildPlugin] configure (_.copy(
    // the version of SBuild this plugin is compatible to
    sbuildVersion = new SBuildVersion with SBuildVersion.Scala_2_10_3 with SBuildVersion.ScalaTest_2_0 {
      override protected def project = _project
      override val version: String = "0.7.2.9000"
      override val sbuildClasspath: TargetRefs = sbuildJar
    },
    pluginClass = s"${namespace}.SourceScheme",
    pluginVersion = version
  ))

}