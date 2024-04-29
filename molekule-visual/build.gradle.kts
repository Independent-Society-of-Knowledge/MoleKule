import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalDistributionDsl

plugins {
    kotlin("multiplatform") version "1.9.21"
    id("maven-publish")
}

group = "org.isk"
version = "1.0.0"

repositories {
    mavenCentral()
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["kotlin"])
        }
    }
}

val koolVersion = "0.14.0"
val lwjglVersion = "3.3.3"
val physxJniVersion = "2.3.1"
val targetPlatforms = listOf("natives-windows", "natives-linux", "natives-macos")

kotlin {
    withSourcesJar(true)
    jvm {
        jvmToolchain(21)
    }
    js(IR) {
        binaries.executable()
        browser {
            @OptIn(ExperimentalDistributionDsl::class)
            distribution {
                outputDirectory.set(File("${rootDir}/dist/js"))
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":molekule-core"))

            implementation("de.fabmax.kool:kool-core:$koolVersion")
            implementation("de.fabmax.kool:kool-physics:$koolVersion")

            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
        }
        val jvmMain by getting {
            dependencies {
                // add additional jvm-specific dependencies here...

                // add required runtime libraries for lwjgl and physx-jni
                for (platform in targetPlatforms) {
                    // lwjgl runtime libs
                    runtimeOnly("org.lwjgl:lwjgl:$lwjglVersion:$platform")
                    listOf("glfw", "opengl", "jemalloc", "nfd", "stb", "vma", "shaderc").forEach { lib ->
                        runtimeOnly("org.lwjgl:lwjgl-$lib:$lwjglVersion:$platform")
                    }

                    // physx-jni runtime libs
                    runtimeOnly("de.fabmax:physx-jni:$physxJniVersion:$platform")
                }
            }
        }

        val jsMain by getting {
            dependencies {
                // add additional js-specific dependencies here...
            }
        }
    }
}
