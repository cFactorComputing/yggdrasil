package io.swiftwallet.yggdrasil.adapters.sms.processors;

import io.swiftwallet.commons.domain.account.AccountTransferNotification;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class DebitSmsPreProcessor extends AbstractAccountTransferPreProcessor {

    @Override
    protected String getMobileNumber(final Exchange exchange) {
        final AccountTransferNotification body = exchange.getIn().getBody(AccountTransferNotification.class);
        return body.getFromUserId();
    }
}
