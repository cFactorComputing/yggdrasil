package in.cfcomputing.yggdrasil.core;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by gibugeorge on 27/02/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/camel-context.xml")
public class RouteDefinitionsCollectorTest {

    @Autowired
    public RouteDefinitionsCollector routeDefinitionsCollector;

    @Autowired
    public CamelContext camelContext;

    @Test
    public void test() {
        assertNotNull(routeDefinitionsCollector);
        assertEquals(2,camelContext.getRouteDefinitions().size());
    }

    public static class JavaDslRoutes extends RouteBuilder {

        @Override
        public void configure() throws Exception {
            from("direct://testjavadslroute").id("io.swiftwallet.yggdrasil.test.javadsl").to("mock://test");
        }
    }
}