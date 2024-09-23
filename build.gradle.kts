plugins {
	java
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("jvm") version "1.8.0" // Nếu bạn dùng Kotlin
	kotlin("plugin.spring") version "1.8.0" // Nếu bạn dùng Kotlin
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
	// jcenter() có thể không cần thiết, nhưng bạn có thể để lại nếu cần
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")

	// Spring Data JPA
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("mysql:mysql-connector-java:8.0.33")

	// Bảo mật
	implementation("io.jsonwebtoken:jjwt:0.9.1") // JWT để xác thực

	// Xử lý JSON
	implementation("com.fasterxml.jackson.core:jackson-databind")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// Streaming nhạc (HLS/DASH)
	implementation("org.springframework.boot:spring-boot-starter-websocket")

	// Lưu trữ file nhạc (Amazon S3)
	implementation("software.amazon.awssdk:s3:2.20.0") // Sử dụng phiên bản cụ thể của AWS SDK

	// Xác thực và kiểm tra dữ liệu
	implementation("org.springframework.boot:spring-boot-starter-validation")

	// Ghi log và giám sát
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-logging")

	// Bộ nhớ đệm (Caching)
	implementation("org.springframework.boot:spring-boot-starter-cache")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")

	// Test và kiểm thử
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")

	// Triển khai Heroku
	implementation("com.heroku.sdk:heroku-deploy:3.0.4") // Thay đổi phiên bản nếu cần

	// Docker, triển khai container
	// Nếu bạn cần tích hợp Docker, bạn có thể sử dụng command line để xây dựng Docker image
}

tasks.withType<Test> {
	useJUnitPlatform()
}
