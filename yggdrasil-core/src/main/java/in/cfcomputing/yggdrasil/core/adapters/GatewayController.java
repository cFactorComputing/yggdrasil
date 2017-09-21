package in.cfcomputing.yggdrasil.core.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.cfcomputing.commons.yggdrasil.domain.Error;
import in.cfcomputing.commons.yggdrasil.domain.Message;
import in.cfcomputing.yggdrasil.core.adapters.domain.ResourceAdapter;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultExchange;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static in.cfcomputing.yggdrasil.core.YggdrasilConstants.ADAPTER_TYPE;
import static in.cfcomputing.yggdrasil.core.YggdrasilConstants.RESOURCE_ENDPOINT_TYPE;

/**
 * Created by gibugeorge on 27/02/2017.
 */
@RestController
public class GatewayController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayController.class);
    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private CamelContext camelContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping(path = "/gw", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public <RS, RQ> Message<RS> recieveRequest(@RequestHeader final Map httpHeaders, @RequestBody final Message<RQ> body) throws ClassNotFoundException, IOException {
        LOGGER.info("Request recieved to send to adapter {}", body.getDestination());
        final Exchange exchange = new DefaultExchange(camelContext);
        final Map headers = new HashMap(httpHeaders);
        headers.putAll(body.getHeaders());
        exchange.getIn().setHeaders(headers);
        final String destinationAdapter = body.getDestination().toString().toLowerCase();
        exchange.getIn().setHeader(ADAPTER_TYPE, body.getDestination());
        exchange.getIn().setBody(objectMapper.convertValue(body.getPayload(), Class.forName(body.getPayloadType())));
        exchange.getIn().setHeader(RESOURCE_ENDPOINT_TYPE, body.getResourceEndpointType());
        exchange.setProperty(ADAPTER_TYPE, applicationContext.getBean(destinationAdapter, ResourceAdapter.class));
        producerTemplate.send("direct://gw", exchange);
        //TODO: Move the post processing to a processors;
        final Message message = new Message();
        message.setDestination(body.getDestination());
        message.setHeaders(body.getHeaders());
        final Exception exception = exchange.getException();
        if (exception != null) {
            final Error error = new Error();
            error.setErrorCode("Y-001");
            error.setErrorMessage(exception.getMessage());
            message.setPayload(error);
        } else if (exchange.getOut().getBody() instanceof InputStream) {
            final InputStream inputStream = exchange.getOut().getBody(InputStream.class);
            message.setPayload(IOUtils.toString(inputStream));
        } else {
            message.setPayload(exchange.getIn().getBody());
        }
        return message;
    }
}
