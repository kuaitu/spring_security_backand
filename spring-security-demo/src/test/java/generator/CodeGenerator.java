package generator;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// 代码生成器
public class CodeGenerator {

    @Getter
    @AllArgsConstructor
    static enum GenerateType {
        NORMAL("1",
                "com.lmk.springsecuritydemo.base.controller.BaseController",
                "com.lmk.springsecuritydemo.base.entity.BaseModel",
                "com.lmk.springsecuritydemo.base.service.IBaseService",
                "com.lmk.springsecuritydemo.base.service.impl.BaseServiceImpl",
                "normal",
                new String[] {}),
        BY_ID("2",
               "com.lmk.springsecuritydemo.base.controller.BaseController",
               "com.lmk.springsecuritydemo.base.entity.BaseIdModel",
               "com.lmk.springsecuritydemo.base.service.IBaseService",
               "com.lmk.springsecuritydemo.base.service.impl.BaseServiceImpl",
               "byid",
               new String[] { "id" });

        /**
         * 不要改变下面的字段的顺序
         */
        String code;
        String controllerClassName;
        String entityClassName;
        String serviceClassName;
        String serviceImplClassName;
        String templatePrefix;
        String[] superEntityColumns;
    }

    private static GenerateType generateType;

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        String type = scanner("输入要生成的类型： 1.统用, 2使用ID");

        if (GenerateType.BY_ID.getCode().equals(type)) {
            generateType = GenerateType.BY_ID;
        } else if (GenerateType.NORMAL.getCode().equals(type)) {
            generateType = GenerateType.NORMAL;
        } else {
            throw new IllegalArgumentException("请选择正确的类型，1~4");
        }

        generate(new String[] { "com", "lmk", "springsecuritydemo" });
    }

    public static void generate(String[] packageInfo) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("linmk");
        gc.setOpen(false);
        gc.setDateType(DateType.ONLY_DATE);
        gc.setSwagger2(true); //实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/spring_security?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名"));
        pc.setParent(StringUtils.join(packageInfo, "."));
        pc.setMapper("dao");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/" + generateType.getTemplatePrefix() + "/mapper.xml.ftl";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/java/" + StringUtils.join(packageInfo, "/") + "/" + pc.getModuleName()
                        + "/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                if (fileType == FileType.ENTITY) {
                    // entity默认要覆盖
                    return true;
                }

                /*if (fileType == FileType.CONTROLLER && generateType == GenerateType.ADDON) {
                    return false;
                }*/

                if (FileUtil.exist(filePath)) {
                    // 其他的不存在则创建，不覆盖
                    return false;
                }

                checkDir(filePath);
                return true;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = getTemplateConfigByGenerateType(generateType);
        templateConfig.setXml(null);

        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");



        // 公共父类
        strategy.setSuperEntityClass(generateType.entityClassName);
        // 公共service
        strategy.setSuperServiceClass(generateType.serviceClassName);
        // 公共service实现
        strategy.setSuperServiceImplClass(generateType.serviceImplClassName);
        // 公共controller
        strategy.setSuperControllerClass(generateType.controllerClassName);
        // 写于父类中的公共字段
        if (generateType.getSuperEntityColumns().length > 0) {
            strategy.setSuperEntityColumns(generateType.getSuperEntityColumns());
        }
        strategy.setLogicDeleteFieldName("is_delete");

        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());// 设置模板引擎为freemarker
        mpg.execute();
    }

    // 配置各个模板位置
    private static TemplateConfig getTemplateConfigByGenerateType(GenerateType generateType) {
        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别

        String prefix = "/templates/" + generateType.getTemplatePrefix() + "/";

        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        templateConfig.setEntity(prefix + "entity.java");
        templateConfig.setController(prefix + "controller.java");
        templateConfig.setServiceImpl(prefix + "serviceImpl.java");
        templateConfig.setService(prefix + "service.java");

        return templateConfig;
    }
}
