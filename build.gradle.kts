import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

version = "1.0-SNAPSHOT"

plugins {
    idea
    application
    val kotlinVersion = "1.3.72"
    kotlin("jvm") version kotlinVersion
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
}

repositories {
    maven("https://mirrors.huaweicloud.com/repository/maven")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    compileOnly("com.github.spotbugs:spotbugs:+")

    testImplementation("org.junit.jupiter:junit-jupiter:+")

    testImplementation("com.github.spotbugs:spotbugs:+")
    testImplementation("com.github.spotbugs:test-harness-jupiter:+")
}

tasks {
    withType<Jar> {
        from(configurations.runtimeClasspath.get().map { zipTree(it) })
    }

    withType<Wrapper> {
        gradleVersion = "6.3"
        distributionType = Wrapper.DistributionType.ALL
    }

    withType<Test> {
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "12"
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=enable")
        }
    }

    withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
        options.isFork = true
        options.isIncremental = true
        sourceCompatibility = JavaVersion.VERSION_12.toString()
        targetCompatibility = JavaVersion.VERSION_12.toString()
    }
}
