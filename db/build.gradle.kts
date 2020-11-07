import nu.studer.gradle.jooq.JooqEdition
import org.jooq.meta.jaxb.ForcedType
import org.jooq.meta.jaxb.Property


plugins {
    kotlin("jvm")
    id("org.flywaydb.flyway") version "7.1.1"
    id("nu.studer.jooq") version "5.2"
}

group = "org.practice.micronaut.bookshelf"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation(group = "org.jooq", name = "jooq-codegen", version = "3.13.5")
    implementation("mysql:mysql-connector-java:8.0.22")
    jooqGenerator("mysql:mysql-connector-java:8.0.22")
}


flyway {
    url = "jdbc:mysql://localhost:33006/micronaut?useSSL=false&allowPublicKeyRetrieval=true"
    user = "root"
    password = "secret"
}

jooq {
    version.set("3.13.5")
    edition.set(JooqEdition.OSS)

    configurations {
        create("main") {
            jooqConfiguration.apply {
                logging = org.jooq.meta.jaxb.Logging.WARN
                jdbc.apply {
                    driver = "com.mysql.cj.jdbc.Driver"
                    url = flyway.url
                    user = flyway.user
                    password = flyway.password
                    properties.add(Property().withKey("PAGE_SIZE").withValue("2048"))
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.mysql.MySQLDatabase"
                        inputSchema = "micronaut"
                        forcedTypes.addAll(arrayOf(
                                ForcedType()
                                        .withName("varchar")
                                        .withIncludeExpression(".*")
                                        .withIncludeTypes("JSONB?"),
                                ForcedType()
                                        .withName("varchar")
                                        .withIncludeExpression(".*")
                                        .withIncludeTypes("INET")
                        ).toList())
                    }
                    generate.apply {
                        isDeprecated = false
                        isRecords = false
                        isPojos = false
                        isDaos = true
                        isImmutablePojos = true
                        isFluentSetters = false
                        isPojosEqualsAndHashCode = true
                    }
                    target.apply {
                        packageName = "nu.studer.sample"
                        directory = "src/generated/jooq"
                    }
//                   strategy.name = "org.practice.micronaut.bookshelf.dbGen.SampleNamingGeneratorStrategy"
                }
            }
        }
    }
}