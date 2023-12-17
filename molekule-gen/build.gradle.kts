plugins {
    kotlin("jvm") version "1.9.21"
}

group = "org.isk"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.6.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}