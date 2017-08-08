package in.cfcomputing.yggdrasil.core.async.processors;


import in.cfcomputing.yggdrasil.core.async.domain.AsynchronousResponse;
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