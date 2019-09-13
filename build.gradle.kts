import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.1.8.RELEASE"
	id("io.spring.dependency-management") version "1.0.8.RELEASE"
	kotlin("jvm") version "1.3.50"
	kotlin("plugin.spring") version "1.3.50"
	`java-library`
	`maven-publish`
}

group = "com.example"
version = "1.0.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
	//maven {
	//	url = uri("http://localhost/nexus/content/groups/public")
	//}
}

dependencyManagement {
	dependencies {
		imports {
			mavenBom("org.springframework.cloud:spring-cloud-dependencies:Greenwich.RELEASE")
		}
		dependencySet("io.swagger:1.5.12") {
			entry("swagger-annotations")
			entry("swagger-jaxrs")
		}
		dependencySet("org.elasticsearch:6.2.1") {
			entry("elasticsearch")
			entry("transport")
			entry("elasticsearch-rest-client")
			entry("elasticsearch-rest-high-level-client")
		}
		dependency("javax.ws.rs:javax.ws.rs:2.1")
		dependency("javax:javaee-api:8.0.1")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

tasks.bootJar {
	enabled = false
}

publishing {
	publications {
		create<MavenPublication>("myBom") {
		}
	}

	repositories {
		maven {
			name = "myRepo"
			url = uri("file://${buildDir}/repo")
		}
	}
}
