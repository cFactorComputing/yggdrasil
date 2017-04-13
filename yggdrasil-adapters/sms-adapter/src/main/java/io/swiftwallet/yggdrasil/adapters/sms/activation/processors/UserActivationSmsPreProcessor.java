package io.swiftwallet.yggdrasil.adapters.sms.activation.processors;

import io.swiftwallet.commons.domain.security.WalletUser;
import io.swiftwallet.yggdrasil.adapters.sms.processors.AbstractSmsAdapterPreProcessor;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

@Component
public class UserActivationSmsPreProcessor extends AbstractSmsAdapterPreProcessor {

    @Override
    protected String getMobileNumber(final Exchange exchange) {
        final WalletUser walletUser = exchange.getIn().getBody(WalletUser.class);
        return walletUser.getMobileNumber();
    }

    @Override
    protected void buildTemplateContext(Exchange exchange, final Context context) {
        context.setVariable("activated", exchange.getIn().getHeader("activated"));
    }
}