package io.swiftwallet.yggdrasil.core.adapters.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.cfcomputing.odin.core.bootstrap.cd.RuntimeConfiguration;
import io.swiftwallet.yggdrasil.core.adapters.domain.ResourceAdapter;
import io.swiftwallet.yggdrasil.core.adapters.domain.ResourceEndpoint;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gibugeorge on 20/03/2017.
 */
public class AdapterConfiguration implements BeanFactoryAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdapterConfiguration.class);
    @Autowired
    private RuntimeConfiguration runtimeConfiguration;

    @Autowired
    private ObjectMapper objectMapper;

    private ConfigurableListableBeanFactory beanFactory;

    @PostConstruct
    public void initializeConfigurations() {
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        final Map<String, Object> adapterConfig = (Map<String, Object>) runtimeConfiguration.getConfigurations().get("adapters");
        if (MapUtils.isNotEmpty(adapterConfig)) {
            adapterConfig.forEach((adapterCode, config) -> {
                final ResourceAdapter resourceAdapter = objectMapper.convertValue(config, ResourceAdapter.class);
                final Map<String, Object> resourceEndpoints = (Map<String, Object>) ((Map<String, Object>) config).get("resourceEndpoints");
                final Map<String, ResourceEndpoint> resourceEndpointMap = new HashMap<String, ResourceEndpoint>();
                resourceEndpoints.forEach((resourceEndpointCode, resourceEndpointConfig) -> {
                    final ResourceEndpoint resourceEndpoint = objectMapper.convertValue(resourceEndpointConfig, ResourceEndpoint.class);
                    resourceEndpointMap.put(resourceEndpointCode, resourceEndpoint);
                });
                resourceAdapter.setResourceEndPoints(resourceEndpointMap);
                LOGGER.info("Register configuration for resource adapter {} with application context", adapterCode);
                beanFactory.registerSingleton(adapterCode, resourceAdapter);
            });
        }

    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

}
