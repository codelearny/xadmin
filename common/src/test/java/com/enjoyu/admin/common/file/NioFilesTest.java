package com.enjoyu.admin.common.file;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class NioFilesTest {
    @Test
    public void travel() throws IOException {
        Path start = Paths.get("src/test/resources");
        Files.walk(start, FileVisitOption.FOLLOW_LINKS)
                .forEach(path -> {
                    System.out.println(path.getFileName());
                });
        System.out.println("==========");
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file.getFileName());
                return FileVisitResult.CONTINUE;
            }

        });

    }
}
