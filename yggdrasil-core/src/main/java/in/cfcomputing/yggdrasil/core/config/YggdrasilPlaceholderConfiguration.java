package in.cfcomputing.yggdrasil.core.config;

import in.cfcomputing.yggdrasil.core.spring.spi.YggdrasilPropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;

@Configuration
public class YggdrasilPlaceholderConfiguration implements Ordered{

    @Bean
    public YggdrasilPropertyPlaceholderConfigurer platformPropertyPlaceholderConfigurer(final Environment environment) {
        return new YggdrasilPropertyPlaceholderConfigurer(environment);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
