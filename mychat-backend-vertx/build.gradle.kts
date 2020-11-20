import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  java
  kotlin ("jvm") version "1.4.10"
  application
  id("com.github.johnrengelman.shadow") version "5.2.0"
}

group = "moe.itsusinn"
version = "1.0.0-SNAPSHOT"

repositories {
  mavenCentral()
  jcenter()
}

val kotlinVersion = "1.4.10"
val vertxVersion = "3.9.4"
val junitJupiterVersion = "5.6.0"
val coroutineVersion = "1.4.1"

val mainClass = "moe.itsusinn.mychat.Main"

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = "1.8"

application {
  mainClassName = mainClass
}

dependencies {
  implementation("io.vertx:vertx-auth-jwt:$vertxVersion")
  implementation("io.vertx:vertx-web:$vertxVersion")
  implementation("io.vertx:vertx-mysql-client:$vertxVersion")
  implementation("io.vertx:vertx-lang-kotlin-coroutines:$vertxVersion")
  implementation("io.vertx:vertx-lang-kotlin:$vertxVersion")
  implementation("io.vertx:vertx-redis-client:$vertxVersion")

  implementation(kotlin("stdlib-jdk8"))
  implementation("org.jetbrains.kotlinx","kotlinx-coroutines-core",coroutineVersion)

  testImplementation("io.vertx:vertx-junit5:$vertxVersion")
  testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")
}