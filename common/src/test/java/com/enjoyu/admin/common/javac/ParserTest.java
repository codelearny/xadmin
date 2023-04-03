package com.enjoyu.admin.common.javac;

import org.junit.jupiter.api.Test;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;

public class ParserTest {
    @Test
    public void parser() throws IOException {
        File[] files = new File[]{};
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> compilationUnits2 = fileManager.getJavaFileObjects(files);
        compiler.getTask(null, fileManager, null, null, null, compilationUnits2).call();
        fileManager.close();
    }
}
