# Bams Java Spring Template

Standard reference for all Spring Boot Web MVC microservices.
To create a new service, simply clone and rename.

## Building

1. Setup your Artifactory credentials as per [the instructions on engineering guidelines](https://engineering.ops.babylontech.co.uk/docs/languages-java/#artifactory).
2. Clone the repo and run `./gradlew clean build`.

## Setting Up

### IntelliJ

To set up this project with IntelliJ:

1. Import it as a new project from existing sources using Gradle.
2. Install the Lombok plugin.
3. Enable annotation processing:
    1. Open IntelliJ IDEA -> Preferences.
    2. Type "annotation processing" in the search bar in the top left. It should take you to Build, Execution, Deployment -> Compiler -> Annotation Processors
    3. On the right there should be a box marked "Enable annotation processing". Check it and click okay.
4. Install the google-java-format IntelliJ plugin and follow [these instructions](https://github.com/google/google-java-format#intellij) to enable the google-java-format plugin and to configure IntellIJ to do google-java-format's import ordering when you do a CMD+ALT+L.

## Developing

The dev process is the same as for any Spring Boot app. For the unfamiliar:

1. If you followed [the steps above](#intellij), IntelliJ should be configured for you to write and run code (including tests) as usual.
2. You can build the whole project from the command line using gradle with `./gradlew build`, and you can run the app with `./gradlew bootRun`.

## Maintainers
* Developer Platform
* Anyone interested

## Hardware Requirements
### CPU
Minimal

### RAM
Minimal

### GPU
N/A

### IO
Minimal
