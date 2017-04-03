package io.swiftwallet.yggdrasil.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.messageresolver.SpringMessageResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

/**
 * Created by gibugeorge on 22/03/2017.
 */
@Configuration
@EnableConfigurationProperties(TemplateEngineProperties.class)
public class TemplateEngineConfiguration {


    @Autowired
    private TemplateEngineProperties templateEngineProperties;

    @Bean
    public FileTemplateResolver templateResolver() {
        final FileTemplateResolver fileTemplateResolver = new FileTemplateResolver();
        fileTemplateResolver.setPrefix(templateEngineProperties.getTemplateLocation());
        fileTemplateResolver.setSuffix(".html");
        fileTemplateResolver.setTemplateMode("HTML5");
        fileTemplateResolver.setCacheable(false);
        fileTemplateResolver.setOrder(1);
        return fileTemplateResolver;
    }

    @Bean
    public ReloadableResourceBundleMessageSource templateEngineMessageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(templateEngineProperties.getTemplateLocation() + "/localization");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public SpringMessageResolver templateEngineMessageResolver() {
        final SpringMessageResolver messageResolver = new SpringMessageResolver();
        messageResolver.setMessageSource(templateEngineMessageSource());
        return messageResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        final SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.setMessageResolver(templateEngineMessageResolver());
        springTemplateEngine.setTemplateResolver(templateResolver());
        return springTemplateEngine;
    }

}
