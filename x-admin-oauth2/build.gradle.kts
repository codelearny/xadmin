plugins {
    java
    id("com.enjoyu.java-conventions")
    id("com.enjoyu.module-conventions")
    id("org.springframework.boot")
}

dependencies {
    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery")
    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-config")
    implementation("org.springframework.cloud:spring-cloud-starter-oauth2")
    implementation("org.springframework.security:spring-security-oauth2-jose")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
}

description = "x-admin-oauth2"
