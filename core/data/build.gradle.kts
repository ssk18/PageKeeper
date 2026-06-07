plugins {
    id("pagekeeper.android.library")
    id("pagekeeper.hilt")
    alias(libs.plugins.room)
}

android {
    namespace = "com.ssk.pagekeeper.core.data"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

// Room schemas are checked into git. Each version bump dumps a new JSON here;
// reviewers diff the JSON to verify the migration matches the schema change.
room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    implementation(project(":core:domain"))
    implementation(libs.kotlinx.coroutines.core)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    androidTestImplementation(libs.androidx.room.testing)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.junit)
}
