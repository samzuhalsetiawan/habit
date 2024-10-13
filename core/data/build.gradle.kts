plugins {
    alias(libs.plugins.habit.android.library)
    alias(libs.plugins.habit.dagger.hilt)
    alias(libs.plugins.habit.android.room)
    alias(libs.plugins.habit.kotlin.serialization)
    alias(libs.plugins.habit.testing)
}

android {
    namespace = "com.wahyusembiring.data"
}

dependencies {
    implementation(project(":core:common"))

//    Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)

    implementation(libs.androidx.compose.ui.graphics)

    implementation(libs.datastore.preferences)
}