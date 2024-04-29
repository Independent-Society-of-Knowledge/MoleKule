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


kotlin {
    withSourcesJar()
    jvm()
    js()
    jvmToolchain(21)

}
