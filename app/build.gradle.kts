import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
}

android {
    namespace = AppConfig.namespace
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = AppConfig.testInstrumentationRunner
        vectorDrawables {
            useSupportLibrary = true
        }

        val gitHubToken: String = gradleLocalProperties(rootDir).getProperty("GITHUB_TOKEN")
        buildConfigField("String", "GITHUB_TOKEN", gitHubToken)
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = AppConfig.jvmTarget
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = AppConfig.kotlinCompilerExtensionVersion
    }
    packagingOptions {
        resources {
            resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(Dependencies.Android.coreKtx)
    implementation(Dependencies.Android.lifecycleRuntimeKtx)
    implementation(Dependencies.Android.lifecycleViewModelCompose)
    implementation(Dependencies.Android.activityCompose)
    implementation(Dependencies.Android.composeUi)
    implementation(Dependencies.Android.composePreview)
    implementation(Dependencies.Android.material)
    implementation(Dependencies.ThirdParty.retrofit)
    implementation(Dependencies.ThirdParty.retrofitGson)
    implementation(Dependencies.ThirdParty.okhttp3LoggingInterceptor)
    implementation(Dependencies.Android.room)
    implementation(Dependencies.Android.roomKtx)
    kapt(Dependencies.Android.roomCompiler)
    implementation(Dependencies.Android.hilt)
    kapt(Dependencies.Android.hiltCompiler)
    implementation(Dependencies.ThirdParty.coilCompose)
    testImplementation(Dependencies.Test.junit)
    androidTestImplementation(Dependencies.AndroidTest.androidJunitExt)
    androidTestImplementation(Dependencies.AndroidTest.espresso)
    androidTestImplementation(Dependencies.AndroidTest.composeJunit)
    debugImplementation(Dependencies.Debug.composeUiTooling)
    debugImplementation(Dependencies.Debug.composeUiTestManifest)
}