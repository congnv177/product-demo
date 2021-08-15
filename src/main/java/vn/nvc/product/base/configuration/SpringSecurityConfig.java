package vn.nvc.product.base.configuration;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import vn.nvc.product.base.security.CustomAuthenticationProvider;
import vn.nvc.product.base.security.JWTConfigurer;
import vn.nvc.product.base.security.JwtTokenProvider;
import vn.nvc.product.service.AccountService;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
@Order(2)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CorsFilter corsFilter;
    private final AccountService accountService;

    public SpringSecurityConfig(AuthenticationManagerBuilder authenticationManagerBuilder, JwtTokenProvider jwtTokenProvider, CorsFilter corsFilter, AccountService accountService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.corsFilter = corsFilter;
        this.accountService = accountService;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // remove csrf and state in session because in jwt we do not need them
                .csrf()
                .disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/admin/accounts/login").permitAll()
                .antMatchers("/admin/categories/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .apply(securityConfigurerAdapter());
    }

    @PostConstruct
    public void init() {
        try {
            CustomAuthenticationProvider customAuthenticationProvider = new CustomAuthenticationProvider(passwordEncoder(), accountService);
            authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);
        } catch (Exception e) {
            throw new BeanInitializationException("Security configuration failed", e);
        }
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(jwtTokenProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}