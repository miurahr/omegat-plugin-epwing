plugins {
    java
    groovy
    checkstyle
    jacoco
    distribution
    id("maven-publish")
    id("org.omegat.gradle") version "1.5.7"
    id("com.github.spotbugs") version "5.0.6"
    id("com.diffplug.spotless") version "6.5.1"
    id("com.github.kt3k.coveralls") version "2.12.0"
    id("com.palantir.git-version") version "0.13.0" apply false
}

// we handle cases without .git directory
val dotgit = project.file(".git")
if (dotgit.exists()) {
    // calculate version string from git tag, hash and commit distance
    apply(plugin = "com.palantir.git-version")
    val versionDetails: groovy.lang.Closure<com.palantir.gradle.gitversion.VersionDetails> by extra
    val details = versionDetails()
    val baseVersion = details.lastTag.substring(1)
    if (details.isCleanTag) {  // release version
        version = baseVersion
    } else {  // snapshot version
        version = baseVersion + "-" + details.commitDistance + "-" + details.gitHash + "-SNAPSHOT"
    }
} else {
    println("Read version property from gradle.properties.")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withSourcesJar()
    withJavadocJar()
}

omegat {
    version = "5.5.0"
    pluginClass = "tokyo.northside.omegat.epwing.OmegatEpwingDictionary"
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    packIntoJar("io.github.eb4j:eb4j:2.3.1")
    implementation("commons-io:commons-io:2.11.0")
    implementation("commons-lang:commons-lang:2.6")
    // should not bundle
    testImplementation("commons-io:commons-io:2.11.0")
    testImplementation("commons-lang:commons-lang:2.6")
    testImplementation("org.codehaus.groovy:groovy-all:3.0.10")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("com.github.spotbugs:spotbugs-annotations:4.7.0")
}

jacoco {
    toolVersion="0.8.6"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
    reports {
        xml.required.set(true)  // coveralls plugin depends on xml format report
        html.required.set(true)
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

coveralls {
    jacocoReportPath = "build/reports/jacoco/test/jacocoTestReport.xml"
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-Xlint:deprecation")
    options.compilerArgs.add("-Xlint:unchecked")
}

distributions {
    main {
        contents {
            from(tasks["jar"], "README.md", "CHANGELOG.md", "COPYING")
        }
    }
}
