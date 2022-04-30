import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.8-SNAPSHOT"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.31"
	kotlin("plugin.spring") version "1.5.31"
	kotlin("plugin.jpa") version "1.5.31"
	id("com.adarshr.test-logger") version "3.2.0"
}

group = "com.ktinsta"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("net.coobird:thumbnailator:0.4.16")
	implementation("io.jsonwebtoken:jjwt:0.9.1")
	implementation("io.springfox:springfox-boot-starter:3.0.0")
	implementation("commons-validator:commons-validator:1.7")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.assertj:assertj-core:3.22.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
}

testlogger {
	// pick a theme - mocha, standard, plain, mocha-parallel, standard-parallel or plain-parallel
	theme = com.adarshr.gradle.testlogger.theme.ThemeType.STANDARD

	// set to false to disable detailed failure logs
	showExceptions = true

	// set to false to hide stack traces
	showStackTraces = true

	// set to true to remove any filtering applied to stack traces
	showFullStackTraces = false

	// set to false to hide exception causes
	showCauses = true

	// set threshold in milliseconds to highlight slow tests
	slowThreshold = 2000

	// displays a breakdown of passes, failures and skips along with total duration
	showSummary = true

	// set to true to see simple class names
	showSimpleNames = false

	// set to false to hide passed tests
	showPassed = true

	// set to false to hide skipped tests
	showSkipped = true

	// set to false to hide failed tests
	showFailed = true

	// enable to see standard out and error streams inline with the test results
	showStandardStreams = false

	// set to false to hide passed standard out and error streams
	showPassedStandardStreams = false

	// set to false to hide skipped standard out and error streams
	showSkippedStandardStreams = false

	// set to false to hide failed standard out and error streams
	showFailedStandardStreams = false
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
