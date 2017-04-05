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
    private static final String VALUE = "value";
    private static final String DATE = "date";
    private static final String DATE_FORMAT = "dateFormat";
    private static final String WALLET_USER = "walletUser";
    private static final String DESCRIPTION = "description";
    private static final String TRANSACTION_ID = "transactionId";
    private static final String CURRENCY_SYMBOL = "currencySymbol";

    @Override
    protected void buildEmailContextParams(final Exchange exchange, final Context context) {
        final EmailRequest emailRequest = exchange.getIn().getBody(EmailRequest.class);
        final Double amount = emailRequest.getProperty(VALUE);
        final BigDecimal value = BigDecimal.valueOf(amount);
        final Date date = new Date(emailRequest.<Long>getProperty(DATE));
        final String transactionId = emailRequest.getProperty(TRANSACTION_ID);

        final String currencySymbol = emailRequest.getProperty(CURRENCY_SYMBOL);
        final String dateFormat = emailRequest.getProperty(DATE_FORMAT);
        context.setVariable(VALUE, value);
        context.setVariable(WALLET_USER, emailRequest.getProperty(WALLET_USER));
        context.setVariable(DESCRIPTION, emailRequest.getProperty(DESCRIPTION));
        context.setVariable(DATE, date);
        context.setVariable(TRANSACTION_ID, transactionId);
        context.setVariable(CURRENCY_SYMBOL, StringUtils.defaultIfEmpty(currencySymbol, "INR"));
        context.setVariable(DATE_FORMAT, StringUtils.defaultIfEmpty(dateFormat, "dd-MMM-yyyy"));
    }
}