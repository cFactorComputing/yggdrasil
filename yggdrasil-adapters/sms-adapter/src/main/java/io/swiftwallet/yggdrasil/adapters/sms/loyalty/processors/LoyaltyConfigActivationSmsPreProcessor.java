package io.swiftwallet.yggdrasil.adapters.sms.loyalty.processors;

import io.swiftwallet.yggdrasil.adapters.sms.processors.AbstractSmsAdapterPreProcessor;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.util.Map;

@Component
public class LoyaltyConfigActivationSmsPreProcessor extends AbstractSmsAdapterPreProcessor {

    @Override
    protected String getMobileNumber(final Exchange exchange) {
        final Map<String, Object> params = exchange.getIn().getBody(Map.class);
        return (String) params.get("mobileNumber");
    }

    @Override
    protected void buildTemplateContext(Exchange exchange, final Context context) {
        final Map<String, Object> params = exchange.getIn().getBody(Map.class);
        context.setVariable("activated", (Boolean) params.get("activated"));
        context.setVariable("title", (String) params.get("title"));
    }
}