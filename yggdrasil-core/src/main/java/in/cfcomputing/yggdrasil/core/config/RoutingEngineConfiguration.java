package in.cfcomputing.yggdrasil.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.cfcomputing.yggdrasil.core.RoutingEngineProperties;
import in.cfcomputing.yggdrasil.core.SpringPropertiesParser;
import in.cfcomputing.yggdrasil.core.adapters.GatewayController;
import in.cfcomputing.yggdrasil.core.adapters.configuration.AdapterConfiguration;
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
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import javax.annotation.PreDestroy;


/**
 * Created by gibugeorge on 23/01/2017.
 */

@Configuration
@ImportResource(locations = "classpath*:META-INF/routing-engine/**/*.xml")
@Import(RoutingEngineConfiguration.RoutingEnginePreConfiguration.class)
public class RoutingEngineConfiguration {


    @Bean
    public GatewayController receiveSendRequest() {
        return new GatewayController();
    }

    @Bean
    public AdapterConfiguration adapterConfiguration() {
        return new AdapterConfiguration();
    }

    @Configuration
    @EnableConfigurationProperties(RoutingEngineProperties.class)
    static class RoutingEnginePreConfiguration {

        @Autowired
        private RoutingEngineProperties routingEngineProperties;
        private CamelContext camelContext;

        @Bean
        public CamelContext camelContext(final ApplicationContext applicationContext) throws Exception {
            camelContext = new SpringCamelContext(applicationContext);
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

        @PreDestroy
        public void stop() throws Exception {
            camelContext.stop();
        }

        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }

    }
}
