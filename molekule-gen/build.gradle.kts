plugins {
    kotlin("jvm") version "1.9.21"
    id("maven-publish")
}

group = "org.isk"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    api("org.apache.commons:commons-lang3:3.14.0")
    api("org.apache.velocity:velocity-engine-core:2.3")
    implementation("org.slf4j:slf4j-simple:2.0.9")
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.6.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}