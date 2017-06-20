package io.swiftwallet.yggdrasil.core.config;

import in.cfcomputing.odin.core.bootstrap.cd.RuntimeConfiguration;
import org.apache.camel.CamelContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertNotNull;

/**
 * Created by gibugeorge on 27/02/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {RoutingEngineConfigurationTest.TestConfiguration.class, RoutingEngineConfiguration.class})
public class RoutingEngineConfigurationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testRoutingEngineConfiguration() {
        CamelContext camelContext = applicationContext.getBean(CamelContext.class);
        assertNotNull(camelContext);
    }

    @Configuration
    public static class TestConfiguration {
        @Bean
        public RuntimeConfiguration runtimeConfiguration() {
            return new RuntimeConfiguration(new java.util.HashMap<>());
        }
    }
}