package com.enjoyu.admin.common.file;

import com.google.common.graph.Traverser;
import com.google.common.io.Files;
import org.junit.jupiter.api.Test;

import java.io.File;

public class GuavaFilesTest {
    @Test
    public void travel() {
        Traverser<File> fileTraverser = Files.fileTraverser();
        File startNode = new File("src/test/resources");
        fileTraverser.breadthFirst(startNode).forEach(System.out::println);
        System.out.println("==============================");
        fileTraverser.depthFirstPreOrder(startNode).forEach(System.out::println);
        System.out.println("==============================");
        fileTraverser.depthFirstPostOrder(startNode).forEach(System.out::println);
    }
}
