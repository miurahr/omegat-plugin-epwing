plugins {
    java
    groovy
    checkstyle
    jacoco
    distribution
    maven
    id("maven-publish")
    id("org.omegat.gradle") version "1.5.0"
    id("com.github.spotbugs") version "4.7.1"
    id("com.diffplug.gradle.spotless") version "3.27.1"
    id("com.github.kt3k.coveralls") version "2.12.0"
    id("com.palantir.git-version") version "0.12.3"
}

// calculate version string from git tag, hash and commit distance
fun getVersionDetails(): com.palantir.gradle.gitversion.VersionDetails = (extra["versionDetails"] as groovy.lang.Closure<*>)() as com.palantir.gradle.gitversion.VersionDetails
if (getVersionDetails().isCleanTag) {
    version = getVersionDetails().lastTag.substring(1)
} else {
    version = getVersionDetails().lastTag.substring(1) + "-" + getVersionDetails().commitDistance + "-" + getVersionDetails().gitHash + "-SNAPSHOT"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

omegat {
    version = "5.4.4"
    pluginClass = "tokyo.northside.omegat.epwing.OmegatEpwingDictionary"
}

repositories {
    mavenCentral()
    if (System.getenv("GITHUB_TOKEN") != null) {
        maven {
            url = uri("https://maven.pkg.github.com/eb4j/eb4j")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

dependencies {
    packIntoJar("io.github.eb4j:eb4j:2.1.8")
    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("commons-io:commons-io:2.7")
    implementation("commons-lang:commons-lang:2.6")
    implementation("org.slf4j:slf4j-nop:1.7.25")
    testImplementation("commons-io:commons-io:2.5")
    testImplementation("commons-lang:commons-lang:2.6")
    testImplementation("org.codehaus.groovy:groovy-all:3.0.8")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.0.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.7.2")
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

val pluginName = project.extra["plugin.name"]
val pluginDescription = project.extra["plugin.description"]
val pluginUrl = project.extra["plugin.link"]
val pluginGroupId = "org.omegat.plugin." + project.name
val packageUrl = "https://maven.pkg.github.com/miurahr/omegat-plugins"

publishing {
    publications {
        create<MavenPublication>("omegatPlugin") {
            from(components["java"])
            groupId = pluginGroupId
            pom.withXml {
                asNode().apply {
                    appendNode("name", pluginName)
                    appendNode("description", pluginDescription)
                    appendNode("url", pluginUrl)
                }
            }
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri(packageUrl)
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}