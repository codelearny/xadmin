package com.enjoyu.admin.components.mbp;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collections;

public class Generator {
    public static void main(String[] args) {
        String projectRoot = System.getProperty("user.dir");
        String srcPath = projectRoot + "/src/main/java";
        String mapperPath = projectRoot + "/src/main/resources/mapper";
        String[] tables = {
//                "smm_user",
                "smm_role", "smm_user_role",
                "smm_menu", "smm_role_menu", "smm_url",
                "smm_role_url", "smm_config", "sys_login_log"
        };
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/xadmin", "root", "root")
                .globalConfig(builder -> {
                    builder.author("mbp") // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(srcPath); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.enjoyu.admin.components.mbp") // 设置父包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, mapperPath)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables) // 设置需要生成的表名
                            .addTablePrefix("smm_", "c_"); // 设置过滤表前缀
                    builder.mapperBuilder().enableFileOverride().mapperAnnotation(Mapper.class);
                    builder.serviceBuilder().enableFileOverride();
                    builder.entityBuilder().enableFileOverride();
                })
                .templateConfig(builder -> {
                    builder.disable(TemplateType.CONTROLLER);
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}
