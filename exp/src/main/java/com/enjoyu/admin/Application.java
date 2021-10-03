package com.enjoyu.admin;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

/**
 * @author enjoyu
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                //Application Startup tracking
                //$ java -XX:StartFlightRecording:filename=recording.jfr,duration=10s -jar demo.jar
                .applicationStartup(new BufferingApplicationStartup(2048))
                .run(args);
    }
}
