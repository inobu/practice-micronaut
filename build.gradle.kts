import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    kotlin("kapt") version "1.4.10"
    kotlin("plugin.allopen") version "1.4.10"
    id("com.github.johnrengelman.shadow") version "6.0.0"
    id("org.jlleitschuh.gradle.ktlint") version "8.0.0"
    id("application")
}

version = "0.1"
group = "org.practice.micronaut.bookshelf"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
    maven("https://jcenter.bintray.com")
}

val developmentOnly by configurations.creating
configurations {
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
    testRuntimeClasspath {
        extendsFrom(developmentOnly)
    }
}

dependencies {
    val kotlinVersion: String by project
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

    val micronautVersion: String by project
    implementation(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    implementation("io.micronaut:micronaut-http-client")

    kapt("io.micronaut:micronaut-validation")

    kapt(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    kaptTest(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    testImplementation(platform("io.micronaut:micronaut-bom:$micronautVersion"))

    implementation("javax.annotation:javax.annotation-api")


    // apply Kotlin Runtime Support
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime:1.0.0.M2")
    implementation("io.micronaut.kotlin:micronaut-ktor:1.0.0.M2")

    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("io.micronaut.test:micronaut-test-junit5")
}

application {
    mainClassName = "org.practice.micronaut.bookshelf.ApplicationKt"
    applicationDefaultJvmArgs = listOf("-noverify", "-XX:TieredStopAtLevel=1", "-Dcom.sun.management.jmxremote")
}


allOpen {
    annotation("io.micronaut.aop.Around")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
            javaParameters = true
        }
    }
}

tasks.withType<ShadowJar> {
    mergeServiceFiles()
}