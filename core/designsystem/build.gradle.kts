plugins {
    id("pagekeeper.android.library.compose")
}

android {
    namespace = "com.ssk.pagekeeper.core.designsystem"
}

dependencies {
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.google.fonts)
    implementation(libs.androidx.activity.compose)
}
