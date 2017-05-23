package io.swiftwallet.yggdrasil.adapters.email.loyalty.coin.processors;

import io.swiftwallet.commons.domain.yggdrasil.email.EmailRequest;
import io.swiftwallet.yggdrasil.adapters.email.processors.AbstractEmailTemplateProcessor;
import org.apache.camel.Exchange;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class CoinCollectionEmailTemplateProcessor extends AbstractEmailTemplateProcessor {
    private static final String COINS = "coins";
    private static final String DATE = "date";
    private static final String DATE_FORMAT = "dateFormat";
    private static final String WALLET_USER = "walletUser";
    private static final String CURRENCY_SYMBOL = "currencySymbol";
    private static final String PURCHASE_AMOUNT = "purchaseAmount";
    private static final String MERCHANT = "merchant";

    @Override
    protected void buildEmailContextParams(final Exchange exchange, final Context context) {
        final EmailRequest emailRequest = exchange.getIn().getBody(EmailRequest.class);
        final Double coins = emailRequest.<Double>getProperty(COINS);
        final Double purchaseAmount = emailRequest.<Double>getProperty(PURCHASE_AMOUNT);
        final Date date = new Date(emailRequest.<Long>getProperty(DATE));
        final String walletUser = emailRequest.getProperty(WALLET_USER);
        final String merchant = emailRequest.getProperty(MERCHANT);

        final String currencySymbol = emailRequest.getProperty(CURRENCY_SYMBOL);
        final String dateFormat = emailRequest.getProperty(DATE_FORMAT);
        context.setVariable(COINS, BigDecimal.valueOf(coins));
        context.setVariable(PURCHASE_AMOUNT, BigDecimal.valueOf(purchaseAmount));
        context.setVariable(DATE, date);
        context.setVariable(WALLET_USER, walletUser);
        context.setVariable(MERCHANT, merchant);
        context.setVariable(CURRENCY_SYMBOL, StringUtils.defaultIfEmpty(currencySymbol, "INR"));
        context.setVariable(DATE_FORMAT, StringUtils.defaultIfEmpty(dateFormat, "dd-MMM-yyyy"));
    }
}