plugins {
    alias(libs.plugins.kotlin.jvm)
    idea
    application
}

group = "net.ocotosystems.idea"
version = "1.0.0"

repositories {
    mavenCentral()
}

idea.module {
    isDownloadJavadoc = true
    isDownloadJavadoc = true
}

application {
    mainClass.set("net.octosystems.office.vakation.MainKt")
}
dependencies {
    implementation(libs.bundles.implementation)
    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.bundles.testRuntime)
}

tasks {
    val fatJar = register<Jar>("fatJar") {
        dependsOn(listOf("compileJava", "compileKotlin", "processResources"))
        archiveClassifier.set("standalone")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest { attributes(mapOf("Main-Class" to application.mainClass)) }
        from(
            configurations.runtimeClasspath.get()
                .map { if (it.isDirectory) it else zipTree(it) }
                    + sourceSets.main.get().output)
    }
    build {
        dependsOn(fatJar)
    }
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
    compilerOptions {
        freeCompilerArgs.add("-Xemit-jvm-type-annotations")
        freeCompilerArgs.add("-Xjsr305=strict")
        javaParameters.set(true)
    }
}
