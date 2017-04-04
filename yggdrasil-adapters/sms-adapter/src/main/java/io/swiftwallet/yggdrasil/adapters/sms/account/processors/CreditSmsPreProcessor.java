package io.swiftwallet.yggdrasil.adapters.sms.account.processors;

import io.swiftwallet.commons.domain.account.AccountTransferNotification;
import io.swiftwallet.yggdrasil.adapters.sms.account.processors.AbstractAccountTransferPreProcessor;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class CreditSmsPreProcessor extends AbstractAccountTransferPreProcessor {
    @Override
    protected String getMobileNumber(Exchange exchange) {
        final AccountTransferNotification body = exchange.getIn().getBody(AccountTransferNotification.class);
        return body.getToUserId();
    }
}