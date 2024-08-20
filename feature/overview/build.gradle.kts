plugins {
    alias(libs.plugins.habit.android.feature)
}

android {
    namespace = "com.wahyusembiring.overview"
}

dependencies {
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)
}