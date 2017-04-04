package io.swiftwallet.yggdrasil.adapters.sms.processors;

import io.swiftwallet.commons.domain.yggdrasil.AdapterType;
import io.swiftwallet.commons.domain.yggdrasil.ResourceEndpointType;
import io.swiftwallet.yggdrasil.core.YggdrasilConstants;
import io.swiftwallet.yggdrasil.core.adapters.domain.ResourceAdapter;
import io.swiftwallet.yggdrasil.core.adapters.domain.ResourceEndpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.thymeleaf.spring4.SpringTemplateEngine;

import java.util.Map;
import java.util.Set;

/**
 * Created by gibugeorge on 27/03/2017.
 */
public abstract class AbstractSmsAdapterPreProcessor implements Processor {


    @Autowired
    private SpringTemplateEngine springTemplateEngine;


    @Override
    public void process(Exchange exchange) throws Exception {
        final Message message = exchange.getIn();
        final AdapterType adapterType = message.getHeader(YggdrasilConstants.ADAPTER_TYPE, AdapterType.class);
        final ResourceAdapter resourceAdapter = exchange.getProperty(adapterType.name(), ResourceAdapter.class);
        final String httpUri = resourceAdapter.getProtocol() + "://" + resourceAdapter.getHost();
        message.setHeader(Exchange.HTTP_URI, httpUri);
        message.setHeader(Exchange.HTTP_PATH, resourceAdapter.getUri());
        final Map<String, String> params = resourceAdapter.getParams();
        StringBuilder query = new StringBuilder();
        final Set<String> keySet = params.keySet();
        for (final String key : keySet) {
            query.append("&").append(key).append("=").append(params.get(key));
        }
        final String resourceEndPoint = message.getHeader(YggdrasilConstants.RESOURCE_ENDPOINT_TYPE, ResourceEndpointType.class).name().toLowerCase();
        final ResourceEndpoint resourceEndpoint = resourceAdapter.getResourceEndPoints().get(resourceEndPoint);
        final String template = resourceEndpoint.getParams().get("template");
        final String finalQuery = String.format(query.toString(), getMobileNumber(exchange), getSmsText(exchange, template));
        message.setHeader(Exchange.HTTP_QUERY, finalQuery);
        message.setHeader(Exchange.HTTP_METHOD, "POST");
        exchange.getIn().setBody(null);

    }

    protected abstract String getSmsText(final Exchange exchange, final String template);

    protected abstract String getMobileNumber(final Exchange exchange);
}
