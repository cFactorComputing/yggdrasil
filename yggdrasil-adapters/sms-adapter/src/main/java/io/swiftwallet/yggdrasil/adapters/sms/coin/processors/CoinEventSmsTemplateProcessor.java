package io.swiftwallet.yggdrasil.adapters.sms.coin.processors;

import io.swiftwallet.yggdrasil.adapters.sms.processors.AbstractSmsAdapterPreProcessor;
import org.apache.camel.Exchange;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Component
public class CoinEventSmsTemplateProcessor extends AbstractSmsAdapterPreProcessor {
    private static final String COINS = "coins";
    private static final String DATE = "date";
    private static final String DATE_FORMAT = "dateFormat";
    private static final String CURRENCY_SYMBOL = "currencySymbol";
    private static final String PURCHASE_AMOUNT = "purchaseAmount";
    private static final String WALLET_USER = "walletUser";
    private static final String MERCHANT = "merchant";

    @Override
    protected String getMobileNumber(final Exchange exchange) {
        final Map<String, Object> params = exchange.getIn().getBody(Map.class);
        return (String) params.get("mobileNumber");
    }

    @Override
    protected void buildTemplateContext(Exchange exchange, final Context context) {
        final Map<String, Object> params = exchange.getIn().getBody(Map.class);
        final String currencySymbol = exchange.getIn().getHeader("currencySymbol", String.class);
        final String dateFormat = exchange.getIn().getHeader("dateFormat", String.class);
        final String walletUser = (String) params.get(WALLET_USER);
        final String merchant = (String) params.get(MERCHANT);

        final Double coins = (Double) params.get(COINS);
        final Double purchaseAmount = params.get(PURCHASE_AMOUNT) != null ?
                NumberUtils.toDouble(params.get(PURCHASE_AMOUNT).toString()) : 0;
        final Date date = new Date((Long) params.get(DATE));

        context.setVariable(COINS, BigDecimal.valueOf(coins));
        context.setVariable(PURCHASE_AMOUNT, BigDecimal.valueOf(purchaseAmount));
        context.setVariable(DATE, date);
        context.setVariable(WALLET_USER, walletUser);
        context.setVariable(CURRENCY_SYMBOL, StringUtils.defaultIfEmpty(currencySymbol, "INR"));
        context.setVariable(DATE_FORMAT, StringUtils.defaultIfEmpty(dateFormat, "dd-MMM-yyyy"));
        context.setVariable(MERCHANT, merchant);
    }
}