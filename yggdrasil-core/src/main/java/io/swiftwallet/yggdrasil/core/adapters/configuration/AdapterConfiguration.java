package io.swiftwallet.yggdrasil.core.adapters.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swiftwallet.odin.core.bootstrap.cd.RuntimeConfiguration;
import io.swiftwallet.yggdrasil.core.adapters.domain.ResourceAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import javax.annotation.PostConstruct;
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
        adapterConfig.forEach((adapterCode, config) -> {
            final ResourceAdapter resourceAdapter = objectMapper.convertValue(config, ResourceAdapter.class);
            LOGGER.info("Register configuration for resource adapter {} with application context", adapterCode);
            beanFactory.registerSingleton(adapterCode, resourceAdapter);
        });

    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

}
