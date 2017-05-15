package io.swiftwallet.yggdrasil.core.async.processors;


import io.swiftwallet.yggdrasil.core.async.domain.AsynchronousResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class AsyncResponseProcessor implements Processor {

    @Override
    public void process(final Exchange exchange) throws Exception {
        final AsynchronousResponse asyncResponse = new AsynchronousResponse();
        if (exchange.getException() != null) {
            asyncResponse.setStatus(AsynchronousResponse.Status.FAILURE);
        } else {
            asyncResponse.setStatus(AsynchronousResponse.Status.SUCCESS);
        }
        exchange.getIn().setBody(asyncResponse);
    }
}