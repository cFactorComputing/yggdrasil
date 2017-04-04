/*
 * Copyright (c) Nibodha Technologies Pvt. Ltd. 2016. All rights reserved.  http://www.nibodha.com
 */

package io.swiftwallet.yggdrasil.adapters.email.processors;

import io.swiftwallet.commons.domain.yggdrasil.email.EmailInfo;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

/**
 * @author anoopkjohn on 14/04/16.
 * @version 1.0
 */

public abstract class EmailTemplateProcessor implements Processor {

    private final SpringTemplateEngine springTemplateEngine;
    private final ReloadableResourceBundleMessageSource messageSource;

    protected EmailTemplateProcessor(final SpringTemplateEngine springTemplateEngine,
                                     final ReloadableResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
        this.springTemplateEngine = springTemplateEngine;
    }


    @Override
    public void process(final Exchange exchange) throws Exception {
        final EmailInfo emailInfo = (EmailInfo) exchange.getIn().getBody();
        final Context context = new Context(emailInfo.getLocale());

        preProcess(context, emailInfo);

        final String htmlTemplate = emailInfo.getHeaderValue("templateFileName");
        final String htmlContent = springTemplateEngine.process(htmlTemplate, context);
        exchange.getIn().setBody(htmlContent);

        postProcess(exchange, emailInfo);
    }

    protected void preProcess(final Context context, final EmailInfo emailInfo) {
        context.setVariable("receiverName", emailInfo.getReceiverName());
        context.setVariable("emailId", emailInfo.getEmailId());
    }

    protected void postProcess(final Exchange exchange, EmailInfo emailInfo) {
        final String subject = messageSource.getMessage(emailInfo.getSubject(), null, emailInfo.getLocale());
        exchange.getIn().setHeader("subject", subject);
        exchange.getIn().setHeader("to", emailInfo.getEmailId());
    }

}
