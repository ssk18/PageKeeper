import com.ssk.pagekeeper.buildlogic.configureSpotlessForRootProject
import org.gradle.api.Plugin
import org.gradle.api.Project

class RootConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        require(target.path == ":") { "pagekeeper.root must only be applied to the root project." }
        target.configureSpotlessForRootProject()
    }
}
