plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.stylenow"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.stylenow"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // ✅ From your version catalog (libs. in libs.versions.toml)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // ✅ RecyclerView for lists
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // ✅ OkHttp for networking
    implementation("com.squareup.okhttp3:okhttp:4.10.0")

    // ✅ Gson for JSON parsing
    implementation("com.google.code.gson:gson:2.10.1")

    // ✅ Glide for image loading
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    // ✅ CardView (for product/item cards)
    implementation("androidx.cardview:cardview:1.0.0")

    // ✅ Material Design components
    implementation("com.google.android.material:material:1.11.0")
}
