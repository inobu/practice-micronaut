import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    kotlin("kapt") version "1.4.10"
    kotlin("plugin.allopen") version "1.4.10"
    id("com.github.johnrengelman.shadow") version "6.0.0"
//    id("org.jlleitschuh.gradle.ktlint") version "8.0.0"
    id("io.micronaut.application") version "1.0.3"
}

version = "0.1"
group = "org.practice.micronaut.bookshelf"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    maven("https://jcenter.bintray.com")
}


dependencies {
    val kotlinVersion: String by project
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation(project("db"))

    val micronautVersion: String by project
    implementation(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-http-server-netty")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.sql:micronaut-jooq:3.1.1")
    implementation("io.micronaut:micronaut-inject:${micronautVersion}")
    implementation("mysql:mysql-connector-java:8.0.22")
    runtimeOnly("io.micronaut.sql:micronaut-jdbc-hikari")

    kapt("io.micronaut:micronaut-inject-java:${micronautVersion}")
    compileOnly("javax.inject:javax.inject:1")

    kapt(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    kaptTest(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    testImplementation(platform("io.micronaut:micronaut-bom:$micronautVersion"))

    runtimeOnly("ch.qos.logback:logback-classic:1.2.3")

    implementation("javax.annotation:javax.annotation-api")

    // apply Kotlin Runtime Support
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")

    val arrowVersion: String by project
    implementation("io.arrow-kt:arrow-core:${arrowVersion}")
    implementation("io.arrow-kt:arrow-core-data:${arrowVersion}")
    implementation("io.arrow-kt:arrow-mtl-data:${arrowVersion}")
    implementation("io.arrow-kt:arrow-mtl:${arrowVersion}")
    implementation("io.arrow-kt:arrow-optics-mtl:${arrowVersion}")
    implementation("io.arrow-kt:arrow-recursion-data:${arrowVersion}")
    implementation("io.arrow-kt:arrow-recursion:${arrowVersion}")
    implementation("io.arrow-kt:arrow-free-data:${arrowVersion}")
    implementation("io.arrow-kt:arrow-free:${arrowVersion}")
    implementation("io.arrow-kt:arrow-annotations:${arrowVersion}")
    implementation("io.arrow-kt:arrow-syntax:${arrowVersion}")

    kaptTest("io.micronaut:micronaut-inject-java:${micronautVersion}")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("io.micronaut.test:micronaut-test-junit5:2.2.1")
    testImplementation("com.ninja-squad:DbSetup:2.1.0")
    testImplementation("com.ninja-squad:DbSetup-kotlin:2.1.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

application {
    mainClass.set("org.practice.micronaut.bookshelf.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-noverify", "-XX:TieredStopAtLevel=1", "-Dcom.sun.management.jmxremote")
}


allOpen {
    annotation("io.micronaut.aop.Around")
}


tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
        javaParameters = true
    }
}

tasks.withType<ShadowJar> {
    mergeServiceFiles()
}