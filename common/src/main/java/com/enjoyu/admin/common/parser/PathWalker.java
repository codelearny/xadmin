package com.enjoyu.admin.common.parser;

import java.io.File;

public class PathWalker {
    public interface FileHandler {
        void handle(int level, String path, File file);
    }

    public interface Filter {
        boolean test(int level, String path, File file);
    }

    private final FileHandler fileHandler;
    private final Filter filter;

    private PathWalker(Filter filter, FileHandler fileHandler) {
        this.filter = filter;
        this.fileHandler = fileHandler;
    }

    public static PathWalker interested(Filter filter, FileHandler fileHandler) {
        return new PathWalker(filter, fileHandler);
    }

    public void explore(String root) {
        walk(0, "", new File(root));
    }

    private void walk(int level, String path, File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) {
                return;
            }
            for (File child : files) {
                walk(level + 1, path + File.pathSeparator + child.getName(), child);
            }
        } else {
            if (filter.test(level, path, file)) {
                fileHandler.handle(level, path, file);
            }
        }
    }
}
