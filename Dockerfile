FROM quay.io/babylonhealth/java:8
ARG JAR_FILE="build/libs/standalone-app.jar"
ADD "$JAR_FILE" "$EXECUTABLE_JAR"
