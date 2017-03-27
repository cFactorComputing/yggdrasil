package io.swiftwallet.yggdrasil.adapters.sms.processors;

import io.swiftwallet.commons.domain.otp.WalletUserOtp;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

/**
 * Created by gibugeorge on 20/03/2017.
 */
@Component
public class OtpPreProcessor extends AbstractSmsAdapterPreProcessor {


    @Override
    protected String getSmsText(Exchange exchange, String template) {
        return String.format(template, exchange.getIn().getBody(WalletUserOtp.class).getOtp());
    }

    @Override
    protected String getMobileNumber(Exchange exchange) {
        final WalletUserOtp walletUserOtp = exchange.getIn().getBody(WalletUserOtp.class);
        return walletUserOtp.getMobileNumber();
    }
}
