package in.cfcomputing.yggdrasil.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by gibugeorge on 22/03/2017.
 */
@ConfigurationProperties(prefix = "template-engine")
public class TemplateEngineProperties {

    private String templateLocation;
    private String messagePath;

    public String getTemplateLocation() {
        return templateLocation;
    }

    public void setTemplateLocation(String templateLocation) {
        this.templateLocation = templateLocation;
    }

    public String getMessagePath() {
        return messagePath;
    }

    public void setMessagePath(String messagePath) {
        this.messagePath = messagePath;
    }
}
