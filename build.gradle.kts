plugins {
    kotlin("jvm") version "2.2.20"
    kotlin("plugin.spring") version "2.2.20"
    id("org.springframework.boot") version "3.5.6"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

// デプロイ用は実行可能な bootJar のみ生成する
tasks.jar {
    enabled = false
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// DEBUG_PORT が設定されていれば bootRun をリモートデバッグ可能にする (Dockerfile.dev 用)
tasks.bootRun {
    System.getenv("DEBUG_PORT")?.let { port ->
        jvmArgs("-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:$port")
    }
}
