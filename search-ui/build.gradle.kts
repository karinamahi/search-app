plugins {
    kotlin("jvm") version "1.9.20"
    id("com.github.node-gradle.node") version "7.1.0"
}

repositories {
    mavenCentral()
}

node {
    version.set("20.11.0") // Node.js version
    npmVersion.set("10.2.3") // npm version
    download.set(true) // Download Node/npm locally
    workDir.set(file("${project.buildDir}/nodejs"))
    nodeProjectDir.set(file("src/main/frontend")) // Directory containing package.json
}

// The 'NpmTask' indicates it will be executed as an NPM task.
val viteBuild by tasks.registering(com.github.gradle.node.npm.task.NpmTask::class) {
    dependsOn("npmInstall") // The npmInstall task is exposed by the plugin
    args.set(listOf("run", "build"))
    workingDir.set(file("src/main/frontend/"))
}

val viteDev by tasks.registering(com.github.gradle.node.npm.task.NpmTask::class) {
    dependsOn("npmInstall") // The npmInstall task is exposed by the plugin
    args.set(listOf("run", "dev"))
    workingDir.set(file("src/main/frontend/"))
}

// Responsible for copying the build content from 'dist' to 'META-INF/resources/'
tasks.named<ProcessResources>("processResources") {
    dependsOn(viteBuild)
    /*
     The final JAR will include the dist content inside 'META-INF/resources/'
     The webapp will automatically serve this content at:
     http://localhost:<port>/react-ui/index.html
    */
    from("src/main/frontend/dist") {
        into("META-INF/resources/search-ui")
    }
}