package io.swiftwallet.yggdrasil.core.adapters;

import io.swiftwallet.commons.domain.yggdrasil.Message;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gibugeorge on 27/02/2017.
 */
@RestController
public class ReceiveSendRequest {

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private CamelContext camelContext;

    @RequestMapping(path = "/gw", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public <RS, RQ> Message<RS> recieveRequest(@RequestHeader final Map httpHeaders, @RequestBody final Message<RQ> body) {
        final Exchange exchange = new DefaultExchange(camelContext);
        final Map headers = new HashMap(httpHeaders);
        headers.putAll(body.getHeaders());
        exchange.getIn().setHeaders(headers);
        exchange.getIn().setBody(body.getPayload());
        producerTemplate.send("direct://gw", exchange);
        final Message message = new Message();
        message.setDestination(body.getDestination());
        message.setHeaders(body.getHeaders());
        message.setPayload(exchange.getIn().getBody());
        return message;
    }
}
