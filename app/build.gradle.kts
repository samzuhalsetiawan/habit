plugins {
    alias(libs.plugins.habit.android.application)
    alias(libs.plugins.habit.android.compose)
    alias(libs.plugins.habit.testing)
    alias(libs.plugins.habit.dagger.hilt)
    alias(libs.plugins.habit.android.room)
    alias(libs.plugins.habit.android.lifecycle)
    alias(libs.plugins.habit.android.navigation)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.wahyusembiring.habit"

    defaultConfig {
        applicationId = "com.wahyusembiring.habit"
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:ui"))
    implementation(project(":core:data"))

    implementation(libs.androidx.activity.compose)

    implementation(project(":feature:homework"))
    implementation(project(":feature:subject"))
    implementation(project(":feature:overview"))
    implementation(project(":feature:exam"))
    implementation(project(":feature:reminder"))
    implementation(project(":feature:kanban"))
    implementation(project(":feature:calendar"))
    implementation(project(":feature:onboarding"))


//   Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    coreLibraryDesugaring(libs.desugar.jdk.libs)

    implementation(libs.androidx.core.splashscreen)

}