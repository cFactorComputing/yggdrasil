/*
 * Copyright (c) Nibodha Technologies Pvt. Ltd. 2016. All rights reserved.  http://www.nibodha.com
 */

package io.swiftwallet.yggdrasil.adapters.email.processors;


import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author anoopkjohn on 14/04/16.
 * @version 1.0
 */

@Component
public class EmailUriProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        final Map<String, Object> headers = exchange.getIn().getHeaders();
        final boolean sslEnabled = "true".equalsIgnoreCase((String) headers.get("sslEnabled"));
        final String port = StringUtils.defaultIfEmpty((String) headers.get("emailPort"), "25");

        final StringBuilder mailEndpointParameters = new StringBuilder();
        mailEndpointParameters
                .append("://")
                .append(headers.get("emailHost"))
                .append(":")
                .append(port)
                .append("?username=")
                .append(headers.get("emailUsername"))
                .append("&password=")
                .append(headers.get("emailPassword"))
                .append("&mail.smtp.auth=")
                .append(headers.get("emailAuthEnabled"))
                .append("&mail.smtp.starttls.enable=true");

        final StringBuilder mailEndpoint = new StringBuilder();
        if (sslEnabled) {
            mailEndpoint.append("smtps").append(mailEndpointParameters.toString()).
                    append("&sslContextParameters=#sslContextParameters");
        } else {
            mailEndpoint.append("smtp").append(mailEndpointParameters.toString());
        }

        exchange.getIn().setHeader("mailEndpoint", mailEndpoint.toString());
    }
}
