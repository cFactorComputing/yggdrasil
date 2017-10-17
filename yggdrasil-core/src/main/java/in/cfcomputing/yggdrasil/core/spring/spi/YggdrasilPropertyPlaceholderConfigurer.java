package in.cfcomputing.yggdrasil.core.spring.spi;

import in.cfcomputing.odin.core.bootstrap.cd.ConfigurationPropertySource;
import org.apache.camel.spring.spi.BridgePropertyPlaceholderConfigurer;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;

import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

public class YggdrasilPropertyPlaceholderConfigurer extends BridgePropertyPlaceholderConfigurer {

    private final Environment environment;

    public YggdrasilPropertyPlaceholderConfigurer(final Environment environment) {
        this.environment = environment;
    }

    @Override
    public void loadProperties(final Properties props) throws IOException {
        super.loadProperties(props);
        loadBootstrapProperties(props);
    }

    private void loadBootstrapProperties(final Properties props) {

        final PropertySource bootstrapProperties = ((StandardEnvironment) environment).getPropertySources().get("bootstrap.properties");
        if (bootstrapProperties instanceof CompositePropertySource) {
            final Collection<PropertySource<?>> propertySources = ((CompositePropertySource) bootstrapProperties).getPropertySources();
            if (CollectionUtils.isNotEmpty(propertySources)) {
                final PropertySource<?> propertySource = propertySources.iterator().next();
                if (propertySource instanceof ConfigurationPropertySource) {
                    props.putAll(((ConfigurationPropertySource) propertySource).getProperties());
                }
            }

        }
    }
}


