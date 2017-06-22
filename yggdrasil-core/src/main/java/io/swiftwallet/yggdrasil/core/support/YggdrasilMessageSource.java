package io.swiftwallet.yggdrasil.core.support;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

public class YggdrasilMessageSource extends ReloadableResourceBundleMessageSource {
    private final boolean initialized;

    public YggdrasilMessageSource(final String location, final String messagePath) {
        setResourceLoader(new DefaultResourceLoader());
        final String baseName = "file:" + location + "/i18n/messages/" + messagePath;
        setBasename(baseName);
        setDefaultEncoding("UTF-8");
        this.initialized = true;
    }

    @Override
    public void setResourceLoader(final ResourceLoader resourceLoader) {
        //Do not allow to set resource loader after initialization
        if (!initialized) {
            super.setResourceLoader(resourceLoader);
        }
    }
}