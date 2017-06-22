package io.swiftwallet.yggdrasil.adapters.sms.processors;

import in.cfcomputing.commons.yggdrasil.domain.IResourceEndpointType;
import io.swiftwallet.yggdrasil.core.YggdrasilConstants;
import io.swiftwallet.yggdrasil.core.adapters.domain.ResourceAdapter;
import io.swiftwallet.yggdrasil.core.adapters.domain.ResourceEndpoint;
import io.swiftwallet.yggdrasil.core.adapters.processor.AbstractTemplatePreprocessor;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Map;
import java.util.Set;

/**
 * Created by gibugeorge on 27/03/2017.
 */
public abstract class AbstractSmsAdapterPreProcessor extends AbstractTemplatePreprocessor implements Processor {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSmsAdapterPreProcessor.class);

    @Autowired
    @Qualifier("sms_adapter")
    private ResourceAdapter resourceAdapter;

    @Override
    public void process(Exchange exchange) throws Exception {
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
        final String resourceEndPoint = message.getHeader(YggdrasilConstants.RESOURCE_ENDPOINT_TYPE, IResourceEndpointType.class).toString().toLowerCase();
        final ResourceEndpoint resourceEndpoint = resourceAdapter.getResourceEndPoints().get(resourceEndPoint);
        final String template = resourceEndpoint.getParams().get("template-name");

        final String smsText = processTemplate(exchange, template);
        logSms(smsText);

        final String finalQuery = String.format(query.toString(), getMobileNumber(exchange), smsText);
        message.setHeader(Exchange.HTTP_QUERY, finalQuery);
        message.setHeader(Exchange.HTTP_METHOD, "POST");
        exchange.getIn().setBody(null);
    }

    private void logSms(final String smsText) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(smsText);
        }
    }

    protected abstract String getMobileNumber(final Exchange exchange);
}