import com.ssk.pagekeeper.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.google.devtools.ksp")

            // Apply Hilt only once an Android plugin is on the project so its
            // configuration has the right Android context. Works for both
            // application and library modules via com.android.base.
            pluginManager.withPlugin("com.android.base") {
                pluginManager.apply("com.google.dagger.hilt.android")

                val kotlinVersion = libs.findVersion("kotlin").get().requiredVersion
                dependencies {
                    add("implementation", libs.findLibrary("hilt-android").get())
                    add("ksp", libs.findLibrary("hilt-compiler").get())
                    // Hilt 2.59.x ships an older kotlin-metadata-jvm that can't read
                    // metadata stamped by Kotlin 2.4+. Override it on the KSP classpath.
                    add("ksp", "org.jetbrains.kotlin:kotlin-metadata-jvm:$kotlinVersion")
                }
            }
        }
    }
}
