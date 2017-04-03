package io.swiftwallet.yggdrasil.adapters.sms.processors;

import io.swiftwallet.commons.domain.account.AccountTransferNotification;
import io.swiftwallet.commons.domain.account.MoneyTransferData;
import io.swiftwallet.commons.domain.otp.WalletUserOtp;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class DebitSmsPreProcessor extends AbstractSmsAdapterPreProcessor {


    @Override
    protected String getSmsText(Exchange exchange, String template) {
        final AccountTransferNotification body = exchange.getIn().getBody(AccountTransferNotification.class);
        return String.format(template, body.getAmount(), body.getDate(), body.getDate(), body.getDate(),body.getTransactionId());
    }

    @Override
    protected String getMobileNumber(Exchange exchange) {
        final AccountTransferNotification body = exchange.getIn().getBody(AccountTransferNotification.class);
        return body.getFromUserId();
    }
}