package vn.nvc.product.base.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class JwtConfig {
    @Value("${spring.security.jwt.secret-token}")
    private String SECRET;

    @Value("${spring.security.jwt.token-prefix}")
    private String TOKEN_PREFIX;

    @Value("${spring.security.jwt.header-name}")
    private String HEADER_NAME;

    @Value("${spring.security.jwt.expiration-time}")
    private long EXPIRATION_TIME;
}
