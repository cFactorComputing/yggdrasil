package in.cfcomputing.yggdrasil.adapters.email.processors;


import in.cfcomputing.commons.yggdrasil.domain.IResourceEndpointType;
import in.cfcomputing.yggdrasil.core.YggdrasilConstants;
import in.cfcomputing.yggdrasil.core.adapters.domain.ResourceAdapter;
import in.cfcomputing.yggdrasil.core.adapters.domain.ResourceEndpoint;
import in.cfcomputing.yggdrasil.core.adapters.processor.AbstractTemplatePreprocessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class AbstractEmailPreprocessor extends AbstractTemplatePreprocessor implements Processor {

    @Autowired
    @Qualifier("email_adapter")
    private ResourceAdapter resourceAdapter;

    @Override
    public void process(Exchange exchange) throws Exception {
        final String resourceEndPoint = exchange.getIn().getHeader(YggdrasilConstants.RESOURCE_ENDPOINT_TYPE, IResourceEndpointType.class).toString().toLowerCase();
        final ResourceEndpoint resourceEndpoint = resourceAdapter.getResourceEndPoints().get(resourceEndPoint);
        exchange.getIn().setHeader("emailHost", resourceAdapter.getHost());
        exchange.getIn().setHeader("emailPort", String.valueOf(resourceAdapter.getPort()));
        exchange.getIn().setHeader("emailUsername", resourceAdapter.getParams().get("user-name"));
        exchange.getIn().setHeader("emailPassword", resourceAdapter.getParams().get("password"));
        exchange.getIn().setHeader("emailAuthEnabled", resourceAdapter.getParams().get("authentication-required"));
        exchange.getIn().setHeader("sslEnabled", resourceAdapter.getParams().get("ssl-enabled"));
        exchange.getIn().setHeader("contentType", "text/html");
        final String templateName = resourceEndpoint.getParams().get("template-name");

        final String emailInfo = processTemplate(exchange, templateName);
        exchange.getIn().setBody(emailInfo);
    }
}