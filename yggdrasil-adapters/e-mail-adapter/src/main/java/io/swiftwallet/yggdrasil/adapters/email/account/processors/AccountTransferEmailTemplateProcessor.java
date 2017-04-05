package io.swiftwallet.yggdrasil.adapters.email.account.processors;

import io.swiftwallet.commons.domain.yggdrasil.email.EmailRequest;
import io.swiftwallet.yggdrasil.adapters.email.processors.AbstractEmailTemplateProcessor;
import org.apache.camel.Exchange;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class AccountTransferEmailTemplateProcessor extends AbstractEmailTemplateProcessor {

    @Override
    protected void buildEmailContextParams(final Exchange exchange, final Context context) {
        final EmailRequest emailRequest = exchange.getIn().getBody(EmailRequest.class);
        final Double amount = emailRequest.getProperty("value");
        final BigDecimal value = BigDecimal.valueOf(amount);
        final Date date = new Date(emailRequest.<Long>getProperty("date"));
        final String transactionId = emailRequest.getProperty("transactionId");

        final String currencySymbol = emailRequest.getProperty("currencySymbol");
        final String dateFormat = emailRequest.getProperty("dateFormat");
        context.setVariable("value", value);
        context.setVariable("walletUser", emailRequest.getProperty("walletUser"));
        context.setVariable("description", emailRequest.getProperty("description"));
        context.setVariable("date", date);
        context.setVariable("transactionId", transactionId);
        context.setVariable("currencySymbol", StringUtils.defaultIfEmpty(currencySymbol, "INR"));
        context.setVariable("dateFormat", StringUtils.defaultIfEmpty(dateFormat, "dd-MMM-yyyy"));
    }
}