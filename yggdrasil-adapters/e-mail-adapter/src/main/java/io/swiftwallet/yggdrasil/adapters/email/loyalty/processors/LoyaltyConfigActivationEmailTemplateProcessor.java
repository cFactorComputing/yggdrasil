package io.swiftwallet.yggdrasil.adapters.email.loyalty.processors;

import io.swiftwallet.commons.domain.yggdrasil.email.EmailRequest;
import io.swiftwallet.yggdrasil.adapters.email.processors.AbstractEmailTemplateProcessor;
import org.apache.camel.Exchange;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.util.Date;

@Component
public class LoyaltyConfigActivationEmailTemplateProcessor extends AbstractEmailTemplateProcessor {
    private static final String TITLE = "title";
    private static final String ACTIVATED = "activated";
    private static final String EXPIRY_DATE = "expiryDate";
    private static final String DATE_FORMAT = "dateFormat";

    @Override
    protected void buildEmailContextParams(final Exchange exchange, final Context context) {
        final EmailRequest emailRequest = exchange.getIn().getBody(EmailRequest.class);
        final String title = emailRequest.getProperty(TITLE);
        final Date expiryDate = new Date(emailRequest.<Long>getProperty(EXPIRY_DATE));
        final String dateFormat = emailRequest.getProperty(DATE_FORMAT);

        context.setVariable(TITLE, title);
        context.setVariable(EXPIRY_DATE, expiryDate);
        context.setVariable(ACTIVATED, emailRequest.getProperty(ACTIVATED));
        context.setVariable(DATE_FORMAT, StringUtils.defaultIfEmpty(dateFormat, "dd-MMM-yyyy"));
    }
}