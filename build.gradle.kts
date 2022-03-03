import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

import org.gradle.api.tasks.testing.logging.TestExceptionFormat.*
import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
	id("org.springframework.boot") version "2.6.3"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("com.netflix.dgs.codegen") version "5.1.17"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	//spring系
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-web")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	//dgs
	implementation(platform("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:latest.release"))
	implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter")
	implementation("com.netflix.graphql.dgs:graphql-dgs-extended-scalars")
	implementation("com.netflix.graphql.dgs:graphql-dgs-subscriptions-websockets-autoconfigure:latest.release")


	//coroutines
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt")
	//httpRequest
	implementation("com.squareup.okhttp3:okhttp:4.9.3")
	//htmlパーサー
	implementation("org.jsoup:jsoup:1.14.3")
	//形態素解析
	implementation("com.atilika.kuromoji:kuromoji-ipadic:0.9.0")

	//test系
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework:spring-webflux")

	//kotest
	testImplementation("io.kotest:kotest-runner-junit5-jvm:5.1.0")
	testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.0")
}

tasks.withType<com.netflix.graphql.dgs.codegen.gradle.GenerateJavaTask> {
	generateClient = true
	packageName = "com.example.springgraphqlapp.generated"
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
	testLogging {
		events(FAILED, STANDARD_ERROR, SKIPPED)
		exceptionFormat = FULL
		showExceptions = true
		showCauses = true
		showStackTraces = true
	}
}
