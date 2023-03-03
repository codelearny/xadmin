package com.enjoyu.admin.common.parser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileNotFoundException;

/**
 * http://javaparser.org/blog.html
 */
public class ListClassesExample {
    public static void main(String[] args) {
        PathWalker.interested((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
            try {
                CompilationUnit unit = StaticJavaParser.parse(file);
                for (Node childNode : unit.getChildNodes()) {
                    System.out.println(childNode);
                }

                new VoidVisitorAdapter<Object>() {
                    @Override
                    public void visit(MethodCallExpr n, Object arg) {
                        super.visit(n, arg);
                        System.out.println(n);
                    }
                }.visit(unit, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }).explore("C:\\Repository\\github\\xadmin\\common\\src\\main\\java\\com\\enjoyu\\admin\\common\\parser");
    }
}
