object Dependencies {

    object Android {
        const val coreKtx = "androidx.core:core-ktx:${Versions.Android.coreKtx}"
        const val lifecycleRuntimeKtx =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.Android.lifecycle}"
        const val lifecycleViewModelCompose =
            "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.Android.lifecycle}"
        const val activityCompose =
            "androidx.activity:activity-compose:${Versions.Android.activityCompose}"
        const val composeUi = "androidx.compose.ui:ui:${Versions.Android.compose}"
        const val composePreview =
            "androidx.compose.ui:ui-tooling-preview:${Versions.Android.compose}"
        const val material = "androidx.compose.material3:material3:${Versions.Android.material}"
        const val room = "androidx.room:room-runtime:${Versions.Android.room}"
        const val roomKtx = "androidx.room:room-ktx:${Versions.Android.room}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.Android.room}"
        const val hilt = "com.google.dagger:hilt-android:${Versions.Android.hilt}"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.Android.hilt}"
    }

    object Test {
        const val junit = "junit:junit:${Versions.Test.junit}"
        const val mockk = "io.mockk:mockk:${Versions.Test.mockk}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Test.coroutines}"
    }

    object AndroidTest {
        const val androidJunitExt =
            "androidx.test.ext:junit:${Versions.AndroidTest.androidJunitExt}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.AndroidTest.espresso}"
        const val composeJunit = "androidx.compose.ui:ui-test-junit4:${Versions.Android.compose}"
        const val mockk = "io.mockk:mockk-android:${Versions.Test.mockk}"
    }

    object Debug {
        const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.Android.compose}"
        const val composeUiTestManifest =
            "androidx.compose.ui:ui-test-manifest:${Versions.Android.compose}"
    }

    object ThirdParty {
        const val detektFormatting =
            "io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.Plugins.detekt}"
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.ThirdParty.retrofit}"
        const val retrofitGson =
            "com.squareup.retrofit2:converter-gson:${Versions.ThirdParty.retrofit}"
        const val okhttp3LoggingInterceptor =
            "com.squareup.okhttp3:logging-interceptor:${Versions.ThirdParty.okhttp3LoggingInterceptor}"
        const val coilCompose = "io.coil-kt:coil-compose:${Versions.ThirdParty.coilCompose}"
    }
}