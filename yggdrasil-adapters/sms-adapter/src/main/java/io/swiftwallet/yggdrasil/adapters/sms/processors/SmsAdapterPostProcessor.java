package io.swiftwallet.yggdrasil.adapters.sms.processors;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swiftwallet.commons.domain.yggdrasil.Error;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * Created by gibugeorge on 24/03/2017.
 */
@Component
public class SmsAdapterPostProcessor implements Processor {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void process(final Exchange exchange) throws Exception {
        final InputStream body = exchange.getIn().getBody(InputStream.class);
        final Error error = objectMapper.readValue(body, Error.class);
        if (!error.getErrorMessage().equalsIgnoreCase("success")) {
            throw new Exception(error.getErrorMessage());
        }
        exchange.getIn().setBody(error);
    }
}