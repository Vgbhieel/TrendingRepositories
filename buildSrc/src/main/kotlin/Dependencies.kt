object Dependencies {

    object Android {
        const val coreKtx = "androidx.core:core-ktx:${Versions.Android.coreKtx}"
        const val lifecycleRuntimeKtx =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.Android.lifecycleRuntimeKtx}"
        const val activityCompose =
            "androidx.activity:activity-compose:${Versions.Android.activityCompose}"
        const val composeUi = "androidx.compose.ui:ui:${Versions.Android.compose}"
        const val composePreview =
            "androidx.compose.ui:ui-tooling-preview:${Versions.Android.compose}"
        const val material = "androidx.compose.material3:material3:${Versions.Android.material}"
    }

    object Test {
        const val junit = "junit:junit:${Versions.Test.junit}"
    }

    object AndroidTest {
        const val androidJunitExt =
            "androidx.test.ext:junit:${Versions.AndroidTest.androidJunitExt}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.AndroidTest.espresso}"
        const val composeJunit = "androidx.compose.ui:ui-test-junit4:${Versions.Android.compose}"
    }

    object Debug {
        const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.Android.compose}"
        const val composeUiTestManifest =
            "androidx.compose.ui:ui-test-manifest:${Versions.Android.compose}"
    }

    object ThirdParty {
       const val detektFormatting = "io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.Plugins.detekt}"
    }
}