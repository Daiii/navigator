package com.tool.navigator.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * config加载类
 *
 * @author zhangbo
 * @since 1.0.0
 */
@Configuration
public class NavigatorConfiguration {

    @Bean
    @ConditionalOnMissingBean(StaticPropertiesConfig.class)
    public StaticPropertiesConfig staticPropertiesConfig() {
        return new StaticPropertiesConfig();
    }

    @Bean
    @Primary
    public ServletWebServerFactory servletWebServerFactory() {
        return new JettyServletWebServerFactory();
    }
}
