plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.rai.accuratepeople.core.network"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        buildConfigField("String", "BASE_URL", "\"https://661f555f16358961cd940b83.mockapi.io/api/v2/accurate/\"")
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)
}
