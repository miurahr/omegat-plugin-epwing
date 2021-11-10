plugins {
    java
    groovy
    checkstyle
    jacoco
    distribution
    maven
    id("maven-publish")
    id("org.omegat.gradle") version "1.5.3"
    id("com.github.spotbugs") version "4.7.9"
    id("com.diffplug.spotless") version "6.0.0"
    id("com.github.kt3k.coveralls") version "2.12.0"
    id("com.palantir.git-version") version "0.12.3" apply false
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

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
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
    packIntoJar("io.github.eb4j:eb4j:2.3.0")
    // these are bundled lib in omegat, should keep versions
    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("commons-io:commons-io:2.9.0")
    implementation("commons-lang:commons-lang:2.6")
    // should not bundle
    testImplementation("commons-io:commons-io:2.9.0")
    testImplementation("commons-lang:commons-lang:2.6")
    testImplementation("org.codehaus.groovy:groovy-all:3.0.9")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    testImplementation("com.github.spotbugs:spotbugs-annotations:4.4.2")
}

jacoco {
    toolVersion="0.8.6"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)  // coveralls plugin depends on xml format report
        html.required.set(true)
    }
}

coveralls {
    jacocoReportPath = "build/reports/jacoco/test/jacocoTestReport.xml"
}

distributions {
    main {
        contents {
            from(tasks["jar"], "README.md", "CHANGELOG.md", "COPYING")
        }
    }
}
