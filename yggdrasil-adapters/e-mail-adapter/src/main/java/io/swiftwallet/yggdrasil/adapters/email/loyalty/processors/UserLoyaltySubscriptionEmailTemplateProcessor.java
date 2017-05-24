package io.swiftwallet.yggdrasil.adapters.email.loyalty.processors;

import io.swiftwallet.commons.domain.yggdrasil.email.EmailRequest;
import io.swiftwallet.yggdrasil.adapters.email.processors.AbstractEmailTemplateProcessor;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

@Component
public class UserLoyaltySubscriptionEmailTemplateProcessor extends AbstractEmailTemplateProcessor {
    private static final String MOBILE_NUMBER = "mobileNumber";
    private static final String BUSINESS_NAME = "businessName";
    private static final String PROGRAM_NAME = "programName";
    private static final String PROGRAM_RULE = "rule";

    @Override
    protected void buildEmailContextParams(final Exchange exchange, final Context context) {
        final EmailRequest emailRequest = exchange.getIn().getBody(EmailRequest.class);
        final String mobileNumber = emailRequest.getProperty(MOBILE_NUMBER);
        final String businessName = emailRequest.getProperty(BUSINESS_NAME);
        final String programName = emailRequest.getProperty(PROGRAM_NAME);
        final String rule = emailRequest.getProperty(PROGRAM_RULE);
        context.setVariable(MOBILE_NUMBER, mobileNumber);
        context.setVariable(BUSINESS_NAME, businessName);
        context.setVariable(PROGRAM_NAME, programName);
        context.setVariable(PROGRAM_RULE, rule);
    }
}