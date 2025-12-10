plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.minestom:minestom:2025.10.31-1.21.10")
    implementation("org.slf4j:slf4j-simple:2.0.17")
}
