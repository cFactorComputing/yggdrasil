package io.swiftwallet.yggdrasil.core;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.spring.CamelRouteContextFactoryBean;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * Created by gibugeorge on 23/01/2017.
 */
public class RouteDefinitionsCollector {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteDefinitionsCollector.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CamelContext camelContext;


    @PostConstruct
    public void collect() {
        addSpringDslRoutes();
        addJavaDslRoutes();
    }


    private void addSpringDslRoutes() {

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Getting route contexts from spring application contexts");
        }
        final Map<String, CamelRouteContextFactoryBean> routeContexts = applicationContext.getBeansOfType(CamelRouteContextFactoryBean.class);
        if (MapUtils.isEmpty(routeContexts)) {
            logInfo("No route contexts found");
        }
        routeContexts.forEach((routeContextName, routecontext) -> {
            if (StringUtils.isEmpty(routeContextName)) {
                logInfo("Skipping the route context as id is null");
            } else {
                final String routeContextId = routeContextName.replace("&", "");
                logInfo("Getting routes from route context:: {}", routeContextId);
                final List<RouteDefinition> routeDefinitions = routecontext.getRoutes();
                addRoutesToCamelContext(routeContextId, routeDefinitions);
            }

        });
    }

    private void addRoutesToCamelContext(String routeContextId, List<RouteDefinition> routeDefinitions) {
        routeDefinitions.forEach(routeDefinition -> {
            final String routeDefinitionId = routeDefinition.getId();
            if (StringUtils.isEmpty(routeDefinitionId)) {
                logInfo("Rejecting route as routedefinition id is null");
            }
            if (isAlreadyDefined(routeDefinition)) {
                logInfo("Route {} is already added to the camel context", routeDefinitionId);
            } else {
                logInfo("Adding route definition with id {} from route context with id {} to camel context", routeDefinitionId, routeContextId);
                routeDefinition.setGroup(routeContextId);
                try {
                    this.camelContext.addRouteDefinition(routeDefinition);
                } catch (Exception e) {
                    LOGGER.error("Exception occured while adding route definition with id " + routeDefinition + " to camel context", e);
                }
            }
        });

    }

    private void addJavaDslRoutes() {
        logInfo("Getting route builders from spring application contexts");
        final Map<String, RouteBuilder> routeBuilders = applicationContext.getBeansOfType(RouteBuilder.class);
        if (MapUtils.isEmpty(routeBuilders)) {
            logInfo("No route builders found");
            return;
        }
        routeBuilders.forEach((routeBuilderName, routeBuilder) -> {
            try {
                camelContext.addRoutes(routeBuilder);
            } catch (Exception e) {
                LOGGER.error("Exception while adding route builder " + routeBuilderName + " to context", e);
            }
        });


    }

    private boolean isAlreadyDefined(final RouteDefinition route) {
        return camelContext.getRouteDefinition(route.getId()) != null;
    }


    private static void logInfo(final String message, Object... arguments) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(message, arguments);
        }
    }
}
