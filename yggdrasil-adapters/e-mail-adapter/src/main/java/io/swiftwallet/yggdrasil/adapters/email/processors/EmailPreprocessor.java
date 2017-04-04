/*
 * Copyright (c) Nibodha Technologies Pvt. Ltd. 2016. All rights reserved.  http://www.nibodha.com
 */

package io.swiftwallet.yggdrasil.adapters.email.processors;


import java.util.Map;

import io.swiftwallet.commons.domain.yggdrasil.ResourceEndpointType;
import io.swiftwallet.commons.domain.yggdrasil.email.EmailInfo;
import io.swiftwallet.commons.domain.yggdrasil.email.EmailRequest;
import io.swiftwallet.yggdrasil.core.adapters.domain.ResourceAdapter;
import io.swiftwallet.yggdrasil.core.adapters.domain.ResourceEndpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author anoopkjohn on 14/04/16.
 * @version 1.0
 */

@Component
public class EmailPreprocessor implements Processor {

    @Autowired
    @Qualifier("EMAIL_ADAPTER")
    private ResourceAdapter resourceAdapter;

    @Override
    public void process(Exchange exchange) throws Exception {
        final EmailRequest.SimpleEmailRequest emailRequest = exchange.getIn().getBody(EmailRequest.SimpleEmailRequest.class);
        final String endpointCode = exchange.getIn().getHeader("resourceEndpoint", ResourceEndpointType.class).name().toLowerCase();
        final ResourceEndpoint resourceEndpoint = resourceAdapter.getResourceEndPoints().get(endpointCode);
        exchange.getIn().setHeader("emailHost", resourceAdapter.getHost());
        exchange.getIn().setHeader("emailUsername", resourceAdapter.getParams().get("user-name"));
        exchange.getIn().setHeader("emailPassword", resourceAdapter.getParams().get("password"));
        exchange.getIn().setHeader("emailAuthEnabled", resourceAdapter.getParams().get("authentication-required"));
        exchange.getIn().setHeader("sslEnabled", resourceAdapter.getParams().get("ssl-enabled"));
        exchange.getIn().setHeader("templateFileName", resourceEndpoint.getParams().get("template-name"));
        final Map<String, Object> headers = exchange.getIn().getHeaders();
        final EmailInfo emailInfo = EmailInfo.of(headers, emailRequest);
        exchange.getIn().setBody(emailInfo);
    }
}
