import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    idea
    id("drip.geek.kotlin-library-conventions")
    kotlin("plugin.serialization") version "1.6.10"
    id("java-library")
    id("org.web3j") version "4.9.0"
}

val ktorVersion = "1.6.8"
val web3jVersion = "5.0.0"
val sl4jApiVersion = "2.0.0-alpha7"
val junitJupiterVersion = "5.7.0"
val junitPlatformVersion = "1.3.2"
val assertJVersion = "3.22.0"

dependencies {
    // Http Client
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")

    // Logging
    implementation("org.slf4j:slf4j-simple:$sl4jApiVersion")
    implementation("org.slf4j:slf4j-api:$sl4jApiVersion")

    // Web3
    implementation("org.web3j:core:$web3jVersion")
    implementation("org.web3j:parity:$web3jVersion")
    implementation("org.web3j:abi:$web3jVersion")
    implementation("org.web3j:codegen:$web3jVersion")

    // Tests
    testImplementation("org.junit.platform:junit-platform-surefire-provider:$junitPlatformVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
    testImplementation("org.assertj:assertj-core:$assertJVersion")

}

web3j {
    generatedPackageName = "drip.geek"
    generatedFilesBaseDir = "src"
    excludedContracts = listOf("Ownable")
    useNativeJavaTypes = false
}

val compileKotlin: KotlinCompile by tasks
val compileTestKotlin: KotlinCompile by tasks

compileKotlin.kotlinOptions {
    jvmTarget = "13"
}
compileTestKotlin.kotlinOptions {
    jvmTarget = "13"
}