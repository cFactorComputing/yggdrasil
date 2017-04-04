package io.swiftwallet.yggdrasil.adapters.sms.otp.processors;

import io.swiftwallet.commons.domain.otp.WalletUserOtp;
import io.swiftwallet.yggdrasil.adapters.sms.processors.AbstractSmsAdapterPreProcessor;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

/**
 * Created by gibugeorge on 20/03/2017.
 */
@Component
public class OtpPreProcessor extends AbstractSmsAdapterPreProcessor {

    @Override
    protected String getMobileNumber(final Exchange exchange) {
        final WalletUserOtp walletUserOtp = exchange.getIn().getBody(WalletUserOtp.class);
        return walletUserOtp.getMobileNumber();
    }

    @Override
    protected void buildTemplateContext(Exchange exchange, final Context context) {
        context.setVariable("otp", exchange.getIn().getBody(WalletUserOtp.class).getOtp());
    }
}