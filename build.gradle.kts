import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

plugins {
    kotlin("jvm") version "1.2.71"
}

group = "com.github.jntakpe"
version = "1.0-SNAPSHOT"

dependencies {
    compile(kotlin("stdlib-jdk8", "1.2.71"))
    compile("org.apache.kafka:kafka-clients:1.0.0-cp1")
    compile("ch.qos.logback:logback-classic:1.2.3")
    compile("org.slf4j:slf4j-api:1.7.25")
    compile("io.projectreactor.kafka:reactor-kafka:1.1.0.RELEASE")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

repositories {
    maven {
        url = URI("http://packages.confluent.io/maven/")
    }
    mavenCentral()
}