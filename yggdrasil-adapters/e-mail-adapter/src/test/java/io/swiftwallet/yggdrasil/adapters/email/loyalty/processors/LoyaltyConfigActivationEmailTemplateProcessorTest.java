package io.swiftwallet.yggdrasil.adapters.email.loyalty.processors;

import in.cfcomputing.odin.core.bootstrap.config.OdinBootstrapConfiguration;
import io.swiftwallet.commons.domain.yggdrasil.ResourceEndpointType;
import io.swiftwallet.commons.domain.yggdrasil.email.EmailRequest;
import io.swiftwallet.yggdrasil.adapters.email.support.EmailTestConfiguration;
import io.swiftwallet.yggdrasil.core.YggdrasilConstants;
import io.swiftwallet.yggdrasil.core.adapters.domain.ResourceAdapter;
import io.swiftwallet.yggdrasil.core.adapters.domain.ResourceEndpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.apache.camel.test.spring.CamelSpringDelegatingTestContextLoader;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.apache.camel.test.spring.CamelTestContextBootstrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Created by sheraf on 24/05/17.
 */
@RunWith(CamelSpringRunner.class)
@BootstrapWith(CamelTestContextBootstrapper.class)
@ContextConfiguration(classes = {EmailTestConfiguration.class,
        LoyaltyConfigActivationEmailTemplateProcessorTest.ContextConfig.class, OdinBootstrapConfiguration.class}, loader = CamelSpringDelegatingTestContextLoader.class)
public class LoyaltyConfigActivationEmailTemplateProcessorTest {

    @EndpointInject(uri = "mock:result")
    protected MockEndpoint resultEndpoint;
    @Produce(uri = "direct:start")
    protected ProducerTemplate template;
    @Autowired
    @Qualifier(value = "email_adapter")
    private ResourceAdapter resourceAdapter;

    @Test
    public void loyaltyConfigActivationEmailProcess() {
        Map<String, String> params = new HashMap<>();
        params.put("template-name", "loyalty-config-activation-email");
        final ResourceEndpoint emailEndPoint = new ResourceEndpoint();
        emailEndPoint.setParams(params);
        Map<String, ResourceEndpoint> endPoint = new HashMap<>();
        endPoint.put(ResourceEndpointType.LOYALTY_CONFIG_ACTIVATION_EMAIL.name().toLowerCase(), emailEndPoint);
        when(resourceAdapter.getResourceEndPoints()).thenReturn(endPoint);

        final EmailRequest emailRequest = new EmailRequest();
        emailRequest.setEmailId("unittest@test.com");
        emailRequest.setReceiverName("tester");
        emailRequest.setSubject("email.loyalty.config.active.subject");
        emailRequest.addProperty("title", "Test title");
        emailRequest.addProperty("activated", true);
        emailRequest.addProperty("expiryDate", new Date().getTime());

        Map<String, Object> headers = new HashMap<>();
        headers.put(YggdrasilConstants.RESOURCE_ENDPOINT_TYPE, ResourceEndpointType.LOYALTY_CONFIG_ACTIVATION_EMAIL);
        template.sendBodyAndHeaders(emailRequest, headers);
        assertNotNull(resultEndpoint.getReceivedExchanges());
    }

    @Configuration
    public static class ContextConfig extends SingleRouteCamelConfiguration {
        @Autowired
        private LoyaltyConfigActivationEmailTemplateProcessor processor;

        @Bean
        public RouteBuilder route() {
            return new RouteBuilder() {
                public void configure() {
                    from("direct:start").process(processor).to("mock:result");
                }
            };
        }
    }
}
