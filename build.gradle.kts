plugins {
    id(Plugins.androidApplication) version Versions.Plugins.androidApplication apply false
    id(Plugins.androidLibrary) version Versions.Plugins.androidLibrary apply false
    id(Plugins.kotlinAndroid) version Versions.Plugins.kotlinAndroid apply false
    id(Plugins.detekt) version Versions.Plugins.detekt
    id(Plugins.hilt) version Versions.Plugins.hilt apply false
}

subprojects {
    apply {
        plugin(Plugins.detekt)
    }

    detekt {
        config = rootProject.files("config/detekt/detekt.yml")
    }

    dependencies {
        detektPlugins(Dependencies.ThirdParty.detektFormatting)
    }
}