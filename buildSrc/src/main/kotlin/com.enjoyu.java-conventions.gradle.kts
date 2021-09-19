plugins {
    java
}

group = "com.enjoyu"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}
repositories {
    mavenLocal()
    maven { url = uri("https://maven.aliyun.com/repository/public") }
    gradlePluginPortal()
    mavenCentral()
}

tasks.withType<Test> {
    useJUnitPlatform()
}