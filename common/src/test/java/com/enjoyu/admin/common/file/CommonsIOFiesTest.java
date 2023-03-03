package com.enjoyu.admin.common.file;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Iterator;

public class CommonsIOFiesTest {
    @Test
    public void list() {
        File startNode = new File("src/test/resources");

        Iterator<File> fileIterator = FileUtils.iterateFiles(startNode, FileFilterUtils.fileFileFilter(), TrueFileFilter.INSTANCE);
        while (fileIterator.hasNext()) {
            System.out.println(fileIterator.next());
        }
        System.out.println("====================");
        FileUtils.iterateFiles(startNode, new String[]{"txt"}, true).forEachRemaining(System.out::println);
    }
}
