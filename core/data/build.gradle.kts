plugins {
    id("pagekeeper.android.library")
    id("pagekeeper.hilt")
}

android {
    namespace = "com.ssk.pagekeeper.core.data"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(libs.kotlinx.coroutines.core)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
}
