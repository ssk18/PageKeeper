plugins {
    id("pagekeeper.android.feature")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.ssk.pagekeeper.feature.library"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:designsystem"))

    // Image loading for book covers
    implementation(libs.coil.compose)
}
