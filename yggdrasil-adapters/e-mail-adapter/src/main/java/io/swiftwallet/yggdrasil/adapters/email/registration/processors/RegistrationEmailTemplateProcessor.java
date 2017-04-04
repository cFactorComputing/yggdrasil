package io.swiftwallet.yggdrasil.adapters.email.registration.processors;

import io.swiftwallet.yggdrasil.adapters.email.processors.AbstractEmailTemplateProcessor;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

@Component
public class RegistrationEmailTemplateProcessor extends AbstractEmailTemplateProcessor {

    @Override
    protected void buildEmailContextParams(final Exchange exchange, final Context context) {
    }
}
