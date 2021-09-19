plugins {
    `java-library`
    id("com.enjoyu.java-conventions")
    id("com.enjoyu.module-conventions")
}

dependencies {
    api("com.google.guava:guava:30.1-jre")
    api("org.springframework.boot:spring-boot-starter-validation")
    api("io.netty:netty-all")
}

description = "common"
