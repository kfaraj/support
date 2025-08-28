package com.kfaraj.support

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.Base64

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
                    connection = "scm:git:https://github.com/kfaraj/support.git"
                    developerConnection = "scm:git:https://github.com/kfaraj/support.git"
                    url = "https://github.com/kfaraj/support"
                }
            }
        }
    }
    repositories {
        maven {
            val stagingUrl =
                "https://ossrh-staging-api.central.sonatype.com/service/local/staging/deploy/maven2/"
            val snapshotsUrl =
                "https://central.sonatype.com/repository/maven-snapshots/"
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

tasks.register("publishToMavenCentral") {
    group = "publishing"
    description = "Publishes all Maven publications produced by this project to Maven Central."
    dependsOn("publish")
    doLast {
        val username = properties["ossrhUsername"] as String?
        val password = properties["ossrhPassword"] as String?
        val namespace = properties["ossrhNamespace"] as String?
        val token = Base64.getEncoder().encodeToString("$username:$password".encodeToByteArray())
        val client = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder()
            .uri(uri("https://ossrh-staging-api.central.sonatype.com/manual/upload/defaultRepository/$namespace"))
            .header("Authorization", "Bearer $token")
            .POST(HttpRequest.BodyPublishers.noBody())
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        if (response.statusCode() < 400) {
            logger.info(response.body())
        } else {
            logger.error(response.body())
        }
    }
}
