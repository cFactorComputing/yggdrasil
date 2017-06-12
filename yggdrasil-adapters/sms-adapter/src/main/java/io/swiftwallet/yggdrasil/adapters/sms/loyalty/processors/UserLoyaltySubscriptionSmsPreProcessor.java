package io.swiftwallet.yggdrasil.adapters.sms.loyalty.processors;

import io.swiftwallet.commons.domain.security.WalletUser;
import io.swiftwallet.yggdrasil.adapters.sms.processors.AbstractSmsAdapterPreProcessor;
import org.apache.camel.Exchange;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.util.Map;

@Component
public class UserLoyaltySubscriptionSmsPreProcessor extends AbstractSmsAdapterPreProcessor {

    @Override
    protected String getMobileNumber(final Exchange exchange) {
        final WalletUser walletUser = exchange.getIn().getBody(WalletUser.class);
        return walletUser.getMobileNumber();
    }

    @Override
    protected void buildTemplateContext(Exchange exchange, final Context context) {
        final Map<String, Object> params = exchange.getIn().getHeaders();
        context.setVariable("businessName", params.get("businessName"));
        final String subscribed = (String) params.get("subscribed");
        context.setVariable("subscribed", StringUtils.isNotEmpty(subscribed) ? Boolean.valueOf(subscribed) : true);
    }
}