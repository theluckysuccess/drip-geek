import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("drip.geek.kotlin-application-conventions")
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

val authyVersion = "1.5.0"
val googleApiClientVersion = "1.33.2"
val googleAuthClientVersion = "1.33.1"
val googleSheetsApiServicesVersion = "v4-rev612-1.25.0"

dependencies {
    implementation(project(":lib"))
}

application {
    project.setProperty("mainClassName", "drip.geek.LauncherKt")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("drip-geek")
        archiveClassifier.set("")
        archiveVersion.set("")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "drip.geek.LauncherKt"))
        }
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}