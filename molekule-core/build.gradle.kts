plugins {
    kotlin("multiplatform") version "1.9.21"
    id("maven-publish")
}

group = "org.isk"
version = "1.0.0"
val kmath_version = "0.3.1"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/mipt-npm/p/sci/maven")
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["kotlin"])
        }
    }
}


kotlin {
    withSourcesJar(true)
    jvm {
        jvmToolchain(21)
    }
//    js {
//        nodejs()
//        js()
//    }
//    jvmToolchain(21)
    sourceSets {
        commonMain {
            dependencies {
                api("space.kscience:kmath-core:${kmath_version}")
            }
        }
    }

}
