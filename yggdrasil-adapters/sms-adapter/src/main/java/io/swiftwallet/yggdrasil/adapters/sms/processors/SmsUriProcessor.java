package io.swiftwallet.yggdrasil.adapters.sms.processors;

import io.swiftwallet.io.swiftwallet.commons.domain.otp.WalletUserOtp;
import io.swiftwallet.yggdrasil.core.adapters.domain.ResourceAdapter;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * Created by gibugeorge on 20/03/2017.
 */
@Component
public class SmsUriProcessor implements Processor {

    @Autowired
    @Qualifier("sms-adapter")
    private ResourceAdapter resourceAdapter;

    @Override
    public void process(final Exchange exchange) throws Exception {

        final Message message = exchange.getIn();
        final String httpUri = resourceAdapter.getProtocol() + "://" + resourceAdapter.getHost();
        message.setHeader(Exchange.HTTP_URI, httpUri);
        message.setHeader(Exchange.HTTP_PATH, resourceAdapter.getUri());
        final Map<String, String> params = resourceAdapter.getParams();
        StringBuilder query = new StringBuilder();
        final Set<String> keySet = params.keySet();
        for (final String key : keySet) {
            query.append("&").append(key).append("=").append(params.get(key));
        }
        final WalletUserOtp walletUserOtp = message.getBody(WalletUserOtp.class);
        String finalQuery = String.format(query.toString(), walletUserOtp.getMobileNumber(), "This is your one time password " + walletUserOtp.getOtp());
        message.setHeader(Exchange.HTTP_QUERY, finalQuery);
        message.setHeader(Exchange.HTTP_METHOD, "POST");
        exchange.getIn().setBody(null);
    }
}
