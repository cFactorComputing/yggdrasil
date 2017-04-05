package io.swiftwallet.yggdrasil.adapters.email.otp.processors;

import io.swiftwallet.commons.domain.yggdrasil.email.EmailRequest;
import io.swiftwallet.yggdrasil.adapters.email.processors.AbstractEmailTemplateProcessor;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

@Component
public class RegistrationOtpEmailTemplateProcessor extends AbstractEmailTemplateProcessor {
    private static final String MOBILE_NUMBER = "mobileNumber";
    private static final String OTP = "otp";

    @Override
    protected void buildEmailContextParams(final Exchange exchange, final Context context) {
        final EmailRequest emailRequest = exchange.getIn().getBody(EmailRequest.class);
        final String mobileNumber = emailRequest.getProperty(MOBILE_NUMBER);
        final Integer otp = emailRequest.getProperty(OTP);
        context.setVariable(MOBILE_NUMBER, mobileNumber);
        context.setVariable(OTP, otp);
    }
}