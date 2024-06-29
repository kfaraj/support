plugins {
    `maven-publish`
    signing
}

interface MavenPomExtension {
    val name: Property<String>
    val description: Property<String>
    val url: Property<String>
}

val pomExtension = extensions.create<MavenPomExtension>("pom")

publishing {
    publications {
        register<MavenPublication>("release") {
            afterEvaluate {
                from(components["release"])
            }
            pom {
                name = pomExtension.name
                description = pomExtension.description
                url = pomExtension.url
                licenses {
                    license {
                        name = "Apache-2.0"
                        url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        name = "Kamal Faraj"
                        email = "kfaraj.dev@gmail.com"
                    }
                }
                scm {
                    connection = "scm:git:https://gitlab.com/kfaraj/support.git"
                    developerConnection = "scm:git:https://gitlab.com/kfaraj/support.git"
                    url = "https://gitlab.com/kfaraj/support"
                }
            }
        }
    }
    repositories {
        maven {
            val stagingUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
            val snapshotsUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
            url = uri(if (version.toString().endsWith("-SNAPSHOT")) snapshotsUrl else stagingUrl)
            credentials {
                username = properties["ossrhUsername"] as String?
                password = properties["ossrhPassword"] as String?
            }
        }
    }
}

signing {
    sign(publishing.publications["release"])
}
