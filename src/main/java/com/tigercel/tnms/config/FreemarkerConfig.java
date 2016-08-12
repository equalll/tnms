package com.tigercel.tnms.config;

import com.tigercel.tnms.service.support.SViewHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration.FreeMarkerWebConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by freedom on 2016/4/13.
 */
@Configuration
public class FreemarkerConfig extends FreeMarkerWebConfiguration {

    @Autowired
    private SViewHelper svh;

    @Override
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer cfg = super.freeMarkerConfigurer();
        Map<String, Object> sharedVariables = new HashMap<>();
        Properties properties = new Properties();

        properties.setProperty("auto_import", "include/spring.ftl as spring");

        sharedVariables.put("viewHelper", svh);
        cfg.setFreemarkerSettings(properties);
        cfg.setFreemarkerVariables(sharedVariables);

        return cfg;
    }


/*
    @Bean
    public SViewHelper sViewHelper() {
        return new SViewHelper();
    }
*/
/*
    @Value("${spring.freemarker.allow-request-override}")
    private Boolean allowRequestOverride;

    @Value("${spring.freemarker.allow-session-override}")
    private Boolean allowSessionOverride;

    @Value("${spring.freemarker.cache}")
    private Boolean cache;

    @Value("${spring.freemarker.charset}")
    private String charset;

    @Value("${spring.freemarker.check-template-location}")
    private Boolean checkTemplateLocation;

    @Value("${spring.freemarker.content-type}")
    private String contentType;

    @Value("${spring.freemarker.enabled}")
    private Boolean enabled;

    @Value("${spring.freemarker.expose-request-attributes}")
    private Boolean exposeRequestAttributes;

    @Value("${spring.freemarker.expose-session-attributes}")
    private Boolean exposeSessionAttributes;

    @Value("${spring.freemarker.expose-spring-macro-helpers}")
    private Boolean exposeSpringMacroHelpers;

    @Value("${spring.freemarker.prefer-file-system-access}")
    private Boolean preferFileSystemAccess;

    @Value("${spring.freemarker.suffix}")
    private String suffix;

    @Value("${spring.freemarker.template-loader-path}")
    private String templateLoaderPath;
*/
}