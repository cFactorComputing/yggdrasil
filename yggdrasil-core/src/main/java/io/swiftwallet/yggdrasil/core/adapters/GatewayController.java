package io.swiftwallet.yggdrasil.core.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swiftwallet.commons.domain.yggdrasil.Error;
import io.swiftwallet.commons.domain.yggdrasil.Message;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultExchange;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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

    @RequestMapping(path = "/gw", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public <RS, RQ> Message<RS> recieveRequest(@RequestHeader final Map httpHeaders, @RequestBody final Message<RQ> body) throws ClassNotFoundException, IOException {
        LOGGER.info("Request recieved to send to adapter {}", body.getDestination());
        final Exchange exchange = new DefaultExchange(camelContext);
        final Map headers = new HashMap(httpHeaders);
        headers.putAll(body.getHeaders());
        exchange.getIn().setHeaders(headers);
        exchange.getIn().setHeader("destination", body.getDestination());
        exchange.getIn().setBody(objectMapper.convertValue(body.getPayload(), Class.forName(body.getPayloadType())));
        exchange.getIn().setHeader("resourceEndpoint", body.getResourceEndpointType());
        producerTemplate.send("direct://gw", exchange);
        final Message message = new Message();
        message.setDestination(body.getDestination());
        message.setHeaders(body.getHeaders());
        final Exception exception = exchange.getException();
        if (exception != null) {
            final Error error = new Error();
            error.setErrorCode("Y-001");
            error.setErrorMessage(exception.getMessage());
            message.setPayload(error);
        } else {
            final InputStream inputStream = exchange.getOut().getBody(InputStream.class);
            message.setPayload(IOUtils.toString(inputStream));
        }
        return message;
    }
}
