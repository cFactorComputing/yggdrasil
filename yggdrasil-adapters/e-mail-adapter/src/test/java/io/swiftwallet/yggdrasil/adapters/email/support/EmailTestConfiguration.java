package io.swiftwallet.yggdrasil.adapters.email.support;


import in.cfcomputing.odin.core.bootstrap.cd.RuntimeConfiguration;
import io.swiftwallet.yggdrasil.core.adapters.domain.ResourceAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.thymeleaf.messageresolver.AbstractMessageResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.messageresolver.SpringMessageResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import static org.mockito.Mockito.mock;


@Configuration
@ComponentScan({
        "io.swiftwallet.yggdrasil.adapters.email.loyalty.processors"
})
public class EmailTestConfiguration {

    @Bean(name = "email_adapter")
    public ResourceAdapter resourceAdapter() {
        ResourceAdapter resourceAdapter = mock(ResourceAdapter.class);
        return resourceAdapter;
    }

    @Bean
    public RuntimeConfiguration runtimeConfiguration() {
        return new RuntimeConfiguration(new java.util.HashMap<>());
    }

    @Bean
    public SpringTemplateEngine springTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(htmlTemplateResolver());
        templateEngine.setTemplateEngineMessageSource(reloadableResourceBundleMessageSource());
        templateEngine.setMessageResolver(templateEngineMessageResolver());
        return templateEngine;
    }

    @Bean
    public AbstractMessageResolver templateEngineMessageResolver() {
        SpringMessageResolver springMessageResolver = new SpringMessageResolver();
        springMessageResolver.setMessageSource(reloadableResourceBundleMessageSource());
        return springMessageResolver;
    }

    private ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(Integer.valueOf(1));
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Bean
    public ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setResourceLoader(new DefaultResourceLoader());
        messageSource.setBasename("classpath:templates/i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

}
