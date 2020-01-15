import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

version = "1.0-SNAPSHOT"

plugins {
    idea
    application
    val kotlinVersion = "1.3.61"
    kotlin("jvm") version kotlinVersion
    id("org.jlleitschuh.gradle.ktlint") version "9.1.1"
}

repositories {
    maven("https://maven.aliyun.com/repository/jcenter")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    compileOnly("com.github.spotbugs:spotbugs:+")

    testImplementation("junit:junit:+")
    testImplementation("com.github.spotbugs:spotbugs:+")
    testImplementation("com.github.spotbugs:test-harness:+")
}

tasks {
    val beforeJar by creating {
        buildDir
                .resolve("tmp").apply { mkdirs() }
                .resolve("1.txt").apply { createNewFile() }
                .bufferedWriter().use { configurations.runtimeClasspath.get().forEach { s -> it.write("$s\n") } }
    }

    withType<Jar> {
        dependsOn(beforeJar)
        from(buildDir.resolve("tmp/1.txt").bufferedReader().readLines().map { zipTree(it) })
    }

    withType<Wrapper> {
        gradleVersion = "6.0"
        distributionType = Wrapper.DistributionType.ALL
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
