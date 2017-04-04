package io.swiftwallet.yggdrasil.adapters.email.registration.processor;

import io.swiftwallet.yggdrasil.adapters.email.processors.EmailTemplateProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.thymeleaf.spring4.SpringTemplateEngine;

/**
 * Created by gibugeorge on 28/03/2017.
 */
public class RegistrationEmailTemplateProcessor extends EmailTemplateProcessor{

    @Autowired
    protected RegistrationEmailTemplateProcessor(final SpringTemplateEngine springTemplateEngine, final ReloadableResourceBundleMessageSource messageSource) {
        super(springTemplateEngine, messageSource);
    }
}
