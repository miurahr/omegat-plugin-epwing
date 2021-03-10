plugins {
    java
    groovy
    checkstyle
    jacoco
    distribution
    maven
    id("org.omegat.gradle") version "1.4.2"
    id("com.sarhanm.versioner") version "4.0.2"
    id("com.github.spotbugs") version "4.5.1"
    id("com.diffplug.gradle.spotless") version "3.27.1"
    id("com.github.kt3k.coveralls") version "2.10.2"
}

versioner{
    snapshot = false
    omitBranchMetadata = true
    disableHotfixVersioning = true
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

omegat {
    version = "5.4.1"
    pluginClass = "tokyo.northside.omegat.epwing.OmegatEpwingDictionary"
}

repositories {
    maven {
        url = uri("https://jitpack.io")
    }
}

dependencies {
    packIntoJar("io.github.eb4j:eb4j:2.0.0")
    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("commons-io:commons-io:2.7")
    implementation("commons-lang:commons-lang:2.6")
    implementation("org.slf4j:slf4j-nop:1.7.25")
    testImplementation("commons-io:commons-io:2.5")
    testImplementation("commons-lang:commons-lang:2.6")
    testImplementation("org.codehaus.groovy:groovy-all:3.0.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.0.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.0.0")
}

jacoco {
    toolVersion="0.8.6"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true  // coveralls plugin depends on xml format report
        html.isEnabled = true
    }
}

coveralls {
    jacocoReportPath = "build/reports/jacoco/test/jacocoTestReport.xml"
}

tasks.distTar {
    compression = Compression.BZIP2
}

distributions {
    main {
        contents {
            from(tasks["jar"], "README.md", "CHANGELOG.md", "COPYING")
        }
    }
}
