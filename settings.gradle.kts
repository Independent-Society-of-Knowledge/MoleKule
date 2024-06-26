pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
//    plugins {
//        id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
//    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "molekule"
include("molekule-core")
include("molekule-dynamic")
include("molekule-bind")
include("molekule-visual")
