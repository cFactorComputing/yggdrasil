package in.cfcomputing.yggdrasil.adapters.email.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class EmailAdapterPostProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setHeaders(Collections.emptyMap());
        exchange.getIn().setBody(StringUtils.EMPTY);
    }
}