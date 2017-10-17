package in.cfcomputing.yggdrasil.core.config;

import in.cfcomputing.yggdrasil.core.adapters.GatewayController;
import in.cfcomputing.yggdrasil.core.adapters.configuration.AdapterConfiguration;
import in.cfcomputing.yggdrasil.core.spring.spi.YggdrasilPropertyPlaceholderConfigurer;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.CompositeRegistry;
import org.apache.camel.impl.PropertyPlaceholderDelegateRegistry;
import org.apache.camel.spring.spi.ApplicationContextRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


/**
 * Created by gibugeorge on 23/01/2017.
 */

@Configuration
@ImportResource(locations = "classpath*:META-INF/routing-engine/**/*.xml")
public class RoutingEngineConfiguration {

    @Autowired
    private CamelContext camelContext;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public GatewayController receiveSendRequest() {
        return new GatewayController();
    }

    @PostConstruct
    public void addContextToRegistry() {
        final CompositeRegistry registry = (CompositeRegistry) ((PropertyPlaceholderDelegateRegistry)
                this.camelContext.getRegistry()).getRegistry();
        registry.addRegistry(new ApplicationContextRegistry(applicationContext));
    }

    @PreDestroy
    public void preDestroy() throws Exception {
        camelContext.stop();
    }

}
