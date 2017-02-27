package io.swiftwallet.yggdrasil.core.adapters;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gibugeorge on 27/02/2017.
 */
@RestController
public class ReceiveSendRequest {

    @Autowired
    private ProducerTemplate producerTemplate;

    @RequestMapping(path = "/gw", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public Map<String, Object> recieveRequest(final Object body) {
        producerTemplate.sendBody("direct://gw", body);
        return new HashMap<>();
    }
}
