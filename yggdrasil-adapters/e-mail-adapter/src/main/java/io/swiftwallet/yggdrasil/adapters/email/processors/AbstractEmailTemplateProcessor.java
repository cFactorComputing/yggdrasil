package io.swiftwallet.yggdrasil.adapters.email.processors;

import io.swiftwallet.commons.domain.yggdrasil.email.EmailRequest;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.thymeleaf.context.Context;

public abstract class AbstractEmailTemplateProcessor extends AbstractEmailPreprocessor {
    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;

    @Override
    protected void buildTemplateContext(final Exchange exchange, final Context context) {
        final EmailRequest emailRequest = exchange.getIn().getBody(EmailRequest.class);
        context.setVariable("receiverName", emailRequest.getReceiverName());
        context.setVariable("emailId", emailRequest.getEmailId());

        buildEmailContextParams(exchange, context);

        //FIXME -- Get message using message source
        // final String subject = messageSource.getMessage(emailRequest.getSubject(), null, context.getLocale());
        exchange.getIn().setHeader("subject", emailRequest.getSubject());
        exchange.getIn().setHeader("to", emailRequest.getEmailId());
    }

    protected abstract void buildEmailContextParams(final Exchange exchange, final Context context);
}
