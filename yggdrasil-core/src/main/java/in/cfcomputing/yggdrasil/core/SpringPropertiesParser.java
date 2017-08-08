package in.cfcomputing.yggdrasil.core;

import org.apache.camel.component.properties.DefaultPropertiesParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.PropertyResolver;

import java.util.Properties;

/**
 * Created by gibugeorge on 23/01/2017.
 */
public class SpringPropertiesParser extends DefaultPropertiesParser {

    @Autowired
    private PropertyResolver propertyResolver;

    @Override
    public String parseProperty(final String key, final String value, final Properties properties) {
        return propertyResolver.getProperty(key);
    }
}
