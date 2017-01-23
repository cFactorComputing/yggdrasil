package io.swiftwallet.yggdrasil.core.config;

import io.swiftwallet.yggdrasil.core.RoutingEngineProperties;
import io.swiftwallet.yggdrasil.core.SpringPropertiesParser;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.properties.PropertiesParser;
import org.apache.camel.spring.CamelBeanPostProcessor;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by gibugeorge on 23/01/2017.
 */

@Configuration
@EnableConfigurationProperties(RoutingEngineProperties.class)
public class RoutingEngineConfiguration {

    @Autowired
    private RoutingEngineProperties routingEngineProperties;

    @Bean
    public CamelContext camelContext(final ApplicationContext applicationContext) throws Exception {
        final CamelContext camelContext = new SpringCamelContext(applicationContext);
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
}
