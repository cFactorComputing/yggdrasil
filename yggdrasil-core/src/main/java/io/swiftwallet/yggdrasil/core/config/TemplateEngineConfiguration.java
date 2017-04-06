package io.swiftwallet.yggdrasil.core.config;

import io.swiftwallet.yggdrasil.core.support.YggdrasilMessageResolver;
import io.swiftwallet.yggdrasil.core.support.YggdrasilMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.thymeleaf.messageresolver.AbstractMessageResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.FileTemplateResolver;

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
        return new YggdrasilMessageSource(templateEngineProperties.getTemplateLocation());
    }

    @Bean
    public AbstractMessageResolver templateEngineMessageResolver() {
        return new YggdrasilMessageResolver(templateEngineMessageSource());
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        final SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.setMessageResolver(templateEngineMessageResolver());
        springTemplateEngine.setTemplateResolver(templateResolver());
        return springTemplateEngine;
    }
}