package vn.nvc.product.base.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

import java.io.Serializable;

@ConfigurationProperties(prefix = "application")
public class ApplicationProperties implements Serializable {
    private final CorsConfiguration cors = new CorsConfiguration();

    public CorsConfiguration getCors() {
        return cors;
    }

    @Override
    public String toString() {
        return "ApplicationProperties{" +
                "cors=" + cors +
                '}';
    }
}
