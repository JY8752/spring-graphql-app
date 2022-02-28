import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.0-M1"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
	//spring系
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.graphql-java-kickstart:graphql-spring-boot-starter:12.0.0")
	implementation("com.graphql-java-kickstart:graphql-java-tools:12.0.0")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

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
	testImplementation("org.springframework.graphql:spring-graphql-test")
	testImplementation("org.springframework.security:spring-security-test")

	//kotest
	testImplementation("io.kotest:kotest-runner-junit5-jvm:5.1.0")
	testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.0")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
