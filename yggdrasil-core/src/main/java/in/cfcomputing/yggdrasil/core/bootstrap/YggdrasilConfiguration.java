package in.cfcomputing.yggdrasil.core.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.cfcomputing.yggdrasil.core.RoutingEngineProperties;
import in.cfcomputing.yggdrasil.core.SpringPropertiesParser;
import in.cfcomputing.yggdrasil.core.adapters.configuration.AdapterConfiguration;
import in.cfcomputing.yggdrasil.core.context.YggdrasilContext;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.properties.PropertiesParser;
import org.apache.camel.impl.CompositeRegistry;
import org.apache.camel.spi.Registry;
import org.apache.camel.spring.CamelBeanPostProcessor;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.spi.ApplicationContextRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

/**
 * Created by gibugeorge on 25/09/2017.
 */
@Configuration
@EnableConfigurationProperties(RoutingEngineProperties.class)
public class YggdrasilConfiguration {

    @Autowired
    private RoutingEngineProperties routingEngineProperties;



    @Bean
    public CamelContext camelContext(final ApplicationContext applicationContext) throws Exception {
        final CompositeRegistry registry = new CompositeRegistry();
        registry.addRegistry(new ApplicationContextRegistry(applicationContext));
        final CamelContext camelContext = new YggdrasilContext(applicationContext,registry);
        if (!routingEngineProperties.isJmxEnabled()) {
            camelContext.disableJMX();
        }
        camelContext.setStreamCaching(routingEngineProperties.isStreamCachingEnabled());
        camelContext.setMessageHistory(routingEngineProperties.isMessageHistoryEnabled());
        camelContext.setHandleFault(routingEngineProperties.isHandleFault());
        camelContext.setAutoStartup(routingEngineProperties.isAutoStartup());
        camelContext.setAllowUseOriginalMessage(routingEngineProperties.isAllowUseOriginalMessage());
        camelContext.setTracing(routingEngineProperties.isTraceEnabled());
        camelContext.start();
        return camelContext;
    }

    @Bean
    public CamelBeanPostProcessor camelBeanPostProcessor(final ApplicationContext applicationContext) {
        CamelBeanPostProcessor processor = new CamelBeanPostProcessor();
        processor.setApplicationContext(applicationContext);
        return processor;
    }

    @Bean
    public ProducerTemplate producerTemplate(final CamelContext camelContext) {
        return camelContext.createProducerTemplate();
    }

    @Bean
    public ConsumerTemplate consumerTemplate(final CamelContext camelContext) {
        return camelContext.createConsumerTemplate();
    }

    @Bean
    public PropertiesParser propertiesParser() {
        return new SpringPropertiesParser();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public AdapterConfiguration adapterConfiguration() {
        return new AdapterConfiguration();
    }


}
