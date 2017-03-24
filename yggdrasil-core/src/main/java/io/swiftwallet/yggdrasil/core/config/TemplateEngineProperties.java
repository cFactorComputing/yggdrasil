package io.swiftwallet.yggdrasil.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by gibugeorge on 22/03/2017.
 */
@ConfigurationProperties(prefix = "template-engine")
public class TemplateEngineProperties {

    private String templateLocation;

    public String getTemplateLocation() {
        return templateLocation;
    }

    public void setTemplateLocation(String templateLocation) {
        this.templateLocation = templateLocation;
    }
}
