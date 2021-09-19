plugins {
    id("com.enjoyu.java-conventions")
}

dependencies {
    compileOnly("org.openjdk.jmh:jmh-core:1.22")
    compileOnly("org.openjdk.jmh:jmh-generator-annprocess:1.22")
}

description = "benchmark"
