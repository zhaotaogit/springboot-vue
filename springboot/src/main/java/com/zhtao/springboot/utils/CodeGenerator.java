package com.zhtao.springboot.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

/**
 * @Author ZhTao
 * @PackageName:com.zhtao.springboot.utils
 * @ClassName: CodeGenerator
 * @Date 2022/6/4 18:35
 * MP代码生成器
 */
public class CodeGenerator {
    public static void main(String[] args) {
        generator();
    }

    public static void generator() {
        FastAutoGenerator.create("jdbc:mysql://43.128.41.124:3306/springboot?serverTimezone=Asia/Shanghai&useSSL=false", "root", "HHH123456!")
                .globalConfig(builder -> {
                    builder.author("zhtao") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D:\\Coding\\Java\\springboot+vue\\springboot\\src\\main\\java\\"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.zhtao.springboot") // 设置父包名
                            .moduleName(null) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D:\\Coding\\Java\\springboot+vue\\springboot\\src\\main\\resources\\mapper\\")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("sys_user") // 设置需要生成的表名
                            .addTablePrefix("t_", "sys_"); // 设置过滤表前缀
                })
                 // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}
