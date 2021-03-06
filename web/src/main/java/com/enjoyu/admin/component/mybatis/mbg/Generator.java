package com.enjoyu.admin.component.mybatis.mbg;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * mybatis generator 执行类
 *
 * @author enjoyu
 */
public class Generator {
    private static final boolean OVERWRITE = true;

    public static void main(String[] args) throws Exception {
        //MBG 执行过程中的警告信息
        List<String> warnings = new ArrayList<>();
        //当生成的代码重复时，覆盖原代码
        //读取我们的 MBG 配置文件
        try (InputStream is = Generator.class.getResourceAsStream("/mybatis/generatorConfig.xml")) {
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(is);
            DefaultShellCallback callback = new DefaultShellCallback(OVERWRITE);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        }
        for (String warning : warnings) {
            System.out.println(warning);
        }
    }
}
