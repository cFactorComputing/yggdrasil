package io.swiftwallet.yggdrasil.core.config;

import org.apache.camel.CamelContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertNotNull;

/**
 * Created by gibugeorge on 27/02/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {RoutingEngineConfiguration.class})
public class RoutingEngineConfigurationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testRoutingEngineConfiguration() {
        CamelContext camelContext = applicationContext.getBean(CamelContext.class);
        assertNotNull(camelContext);
    }
}