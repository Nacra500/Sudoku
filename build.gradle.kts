import org.gradle.internal.impldep.org.fusesource.jansi.AnsiRenderer.test
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("org.jetbrains.dokka") version "1.8.20"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
                implementation("androidx.navigation:navigation-compose:2.5.3")
                implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.4.30")
            }
        }
        val jvmTest by getting{
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("org.junit.jupiter:junit-jupiter:5.8.1")
                implementation("org.mockito:mockito-core:3.1.0")
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Sudoku"
            packageVersion = "1.0.0"
        }
    }
}

