package io.swiftwallet.yggdrasil.adapters.sms.processors;

import io.swiftwallet.commons.domain.account.MoneyTransferData;
import io.swiftwallet.commons.domain.otp.WalletUserOtp;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class DebitSmsPreProcessor extends AbstractSmsAdapterPreProcessor {


    @Override
    protected String getSmsText(Exchange exchange, String template) {
        final MoneyTransferData body = exchange.getIn().getBody(MoneyTransferData.class);
        return String.format(template, body.getTransferDetails().getAmount(), body.getTransferDetails().getDate());
    }

    @Override
    protected String getMobileNumber(Exchange exchange) {
        final MoneyTransferData moneyTransferData = exchange.getIn().getBody(MoneyTransferData.class);
        return moneyTransferData.getFromUserId();
    }
}
