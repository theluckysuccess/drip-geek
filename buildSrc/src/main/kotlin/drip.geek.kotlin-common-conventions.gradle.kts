group = "drip.geek"
version = "1.0"
plugins {
    id("org.jetbrains.kotlin.jvm")
    kotlin("kapt")
}

repositories {
    mavenCentral()
}

val kotlinLoggingVersion = "2.1.21"
val dotenvVersion = "6.2.2"
val mockkVersion = "1.12.3"
val fernetVersion = "1.4.2"
val junitPlatformVersion = "1.0.2"

dependencies {
    constraints {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("commons-cli:commons-cli:1.4")
    }
    // CLI options
    implementation("commons-cli:commons-cli")

    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // KLogging!
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")

    // Environment Variables
    implementation("io.github.cdimascio:dotenv-kotlin:$dotenvVersion")

    // Secret Management
    implementation("com.macasaet.fernet:fernet-java8:$fernetVersion")

    testImplementation("org.junit.platform:junit-platform-surefire-provider:$junitPlatformVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
}
