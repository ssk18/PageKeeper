import com.android.build.api.dsl.ApplicationExtension
import com.ssk.pagekeeper.buildlogic.configureSpotlessForAndroid
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // AGP 9.0+ has built-in Kotlin support; applying the standalone
            // org.jetbrains.kotlin.android plugin would fail.
            pluginManager.apply("com.android.application")

            extensions.configure<ApplicationExtension> {
                compileSdk = 37
                defaultConfig {
                    minSdk = 32
                    targetSdk = 37
                }
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }
            }

            extensions.configure<KotlinAndroidProjectExtension> {
                jvmToolchain(17)
            }

            configureSpotlessForAndroid()
        }
    }
}
