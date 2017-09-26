package in.cfcomputing.yggdrasil.core.context;

import org.apache.camel.spi.Registry;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;


/**
 * Created by gibugeorge on 26/09/2017.
 */
public class YggdrasilContext extends SpringCamelContext {

    public YggdrasilContext(final ApplicationContext applicationContext, final Registry registry) {
        super(applicationContext);
        this.setRegistry(registry);
    }
}
