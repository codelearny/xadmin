plugins {
    `java-library`
    id("com.enjoyu.java-conventions")
}

dependencies {
    implementation(project(":domain"))
    api(project(":common"))
}

description = "application"
