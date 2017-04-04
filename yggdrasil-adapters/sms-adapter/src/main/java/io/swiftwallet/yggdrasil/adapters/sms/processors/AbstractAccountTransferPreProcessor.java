package io.swiftwallet.yggdrasil.adapters.sms.processors;

import io.swiftwallet.commons.domain.account.AccountTransferNotification;
import org.apache.camel.Exchange;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.context.Context;

public abstract class AbstractAccountTransferPreProcessor extends AbstractSmsAdapterPreProcessor {

    @Override
    protected void buildTemplateContext(Exchange exchange, final Context context) {
        final AccountTransferNotification notification = exchange.getIn().getBody(AccountTransferNotification.class);
        final String currencySymbol = exchange.getIn().getHeader("currencySymbol", String.class);
        final String dateFormat = exchange.getIn().getHeader("dateFormat", String.class);
        context.setVariable("value", notification.getAmount());
        context.setVariable("date", notification.getDate());
        context.setVariable("transactionId", notification.getTransactionId());
        context.setVariable("currencySymbol", StringUtils.defaultIfEmpty(currencySymbol, "INR"));
        context.setVariable("dateFormat", StringUtils.defaultIfEmpty(dateFormat, "dd-MMM-yyyy"));
    }
}
