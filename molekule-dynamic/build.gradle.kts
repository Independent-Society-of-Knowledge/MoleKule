plugins {
    kotlin("jvm") version "1.9.21"
}

group = "me.nort3x"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    api(project(":molekule-core"))
    api(project(":molekule-bind"))
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}