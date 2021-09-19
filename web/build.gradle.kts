plugins {
    id("com.enjoyu.java-conventions")
    id("com.enjoyu.module-conventions")
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":application"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt:0.9.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("org.apache.commons:commons-pool2")
    implementation("org.springframework.kafka:spring-kafka")

    implementation("mysql:mysql-connector-java")

    implementation("org.mybatis.generator:mybatis-generator-core:1.4.0")

    implementation("com.github.pagehelper:pagehelper-spring-boot-starter:1.3.0")

    implementation("io.springfox:springfox-boot-starter:3.0.0")

    implementation("com.alibaba:easyexcel:2.2.9")

    compileOnly("org.mapstruct:mapstruct:1.4.2.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.4.2.Final")

    compileOnly("org.projectlombok:lombok:1.18.12")
    annotationProcessor("org.projectlombok:lombok:1.18.12")

}

description = "web"
