package io.swiftwallet.yggdrasil.core.adapters.processor;


import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import java.util.Locale;

public abstract class AbstractTemplatePreprocessor {
    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    protected String processTemplate(final Exchange exchange, final String templateName) {
        final Message message = exchange.getIn();
        final String localeString = message.getHeader("locale", String.class);
        final Locale locale = StringUtils.isEmpty(localeString) ? Locale.UK : new Locale(localeString);
        final Context context = new Context(locale);
        buildTemplateContext(exchange, context);
        return springTemplateEngine.process(templateName, context);
    }

    protected abstract void buildTemplateContext(final Exchange exchange, final Context context);
}
