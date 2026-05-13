plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.rai.accuratepeople.core.database"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
}
