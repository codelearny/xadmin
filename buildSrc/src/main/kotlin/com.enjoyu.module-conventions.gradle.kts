plugins {
    `java-library`
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:2.3.12.RELEASE"))
    implementation(platform("org.springframework.cloud:spring-cloud-dependencies:Hoxton.SR11"))
    implementation(platform("com.alibaba.cloud:spring-cloud-alibaba-dependencies:2.2.1.RELEASE"))

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-devtools")
}
