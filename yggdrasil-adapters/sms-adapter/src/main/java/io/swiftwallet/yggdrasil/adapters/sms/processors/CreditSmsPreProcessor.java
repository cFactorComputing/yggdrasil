package io.swiftwallet.yggdrasil.adapters.sms.processors;

import io.swiftwallet.commons.domain.account.AccountTransferNotification;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class CreditSmsPreProcessor extends AbstractSmsAdapterPreProcessor {


    @Override
    protected String getSmsText(Exchange exchange, String template) {
        final AccountTransferNotification body = exchange.getIn().getBody(AccountTransferNotification.class);
        return String.format(template, body.getAmount(), body.getDate(), body.getDate(), body.getDate());
    }

    @Override
    protected String getMobileNumber(Exchange exchange) {
        final AccountTransferNotification body = exchange.getIn().getBody(AccountTransferNotification.class);
        return body.getToUserId();
    }
}
