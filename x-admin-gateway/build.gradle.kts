plugins {
    java
    id("com.enjoyu.java-conventions")
    id("com.enjoyu.module-conventions")
    id("org.springframework.boot")
}

dependencies {
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery")
    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-config")
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("org.springframework.cloud:spring-cloud-starter-oauth2")
    implementation("org.springframework.security:spring-security-oauth2-client")
    implementation("org.springframework.security:spring-security-oauth2-jose")
}

description = "x-admin-gateway"
