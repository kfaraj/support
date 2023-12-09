apply(plugin = "maven-publish")
apply(plugin = "signing")

configure<PublishingExtension> {
    publications {
        register<MavenPublication>("release") {
            afterEvaluate {
                from(components["release"])
            }
            pom {
                name.set(extra["pomName"] as String)
                description.set(extra["pomDescription"] as String)
                url.set(extra["pomUrl"] as String)
                licenses {
                    license {
                        name.set("Apache-2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        name.set("Kamal Faraj")
                        email.set("kfaraj.dev@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:https://gitlab.com/kfaraj/support.git")
                    developerConnection.set("scm:git:https://gitlab.com/kfaraj/support.git")
                    url.set("https://gitlab.com/kfaraj/support")
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
