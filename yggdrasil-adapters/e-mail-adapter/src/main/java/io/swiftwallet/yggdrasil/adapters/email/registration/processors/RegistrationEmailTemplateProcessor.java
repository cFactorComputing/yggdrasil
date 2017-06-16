package io.swiftwallet.yggdrasil.adapters.email.registration.processors;

import io.swiftwallet.commons.domain.yggdrasil.email.EmailRequest;
import io.swiftwallet.yggdrasil.adapters.email.processors.AbstractEmailTemplateProcessor;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

@Component
public class RegistrationEmailTemplateProcessor extends AbstractEmailTemplateProcessor {
    private static final String MOBILE_NUMBER = "mobileNumber";
    private static final String ROLE_TYPE = "roleType";

    @Value("${admin-bcc-email-enabled:false}")
    private boolean adminBccEnabled;

    @Value("${admin-bcc-email:admin@paytezz.com}")
    private String adminBccEmail;

    @Override
    protected void buildEmailContextParams(final Exchange exchange, final Context context) {
        final EmailRequest emailRequest = exchange.getIn().getBody(EmailRequest.class);
        final String mobileNumber = emailRequest.getProperty(MOBILE_NUMBER);
        final String roleType = emailRequest.getProperty(ROLE_TYPE);

        context.setVariable(MOBILE_NUMBER, mobileNumber);
        context.setVariable(ROLE_TYPE, roleType);
        if (adminBccEnabled) {
            exchange.getIn().setHeader("bcc", adminBccEmail);
        }
    }
}