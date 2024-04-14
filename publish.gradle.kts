apply(plugin = "maven-publish")
apply(plugin = "signing")

configure<PublishingExtension> {
    publications {
        register<MavenPublication>("release") {
            afterEvaluate {
                from(components["release"])
            }
            pom {
                name = extra["pomName"] as String
                description = extra["pomDescription"] as String
                url = extra["pomUrl"] as String
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

configure<SigningExtension> {
    sign(the<PublishingExtension>().publications["release"])
}
