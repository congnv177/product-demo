package vn.nvc.product.base.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.apachecommons.CommonsLog;
import lombok.val;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import vn.nvc.product.base.configuration.JwtConfig;
import vn.nvc.product.base.security.model.CustomUserCredentials;
import vn.nvc.product.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;


@Component
@CommonsLog
public class JwtTokenProvider {
    private final AccountService accountService;

    private final JwtConfig jwtConfig;

    public JwtTokenProvider(AccountService accountService, JwtConfig jwtConfig) {
        this.accountService = accountService;
        this.jwtConfig = jwtConfig;
    }

    public String createToken(String username, String authenticationMethod) {
        UserDetails account = accountService.loadUserByUsername(username);
        if (account != null) {
            return JWT.create()
                    .withSubject(account.getUsername())
                    .withArrayClaim("authorities", account.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
                    .withClaim("authentication_method", authenticationMethod)
                    .withExpiresAt(Date.from(Instant.ofEpochSecond(System.currentTimeMillis() + jwtConfig.getEXPIRATION_TIME())))
                    .sign(HMAC512(jwtConfig.getSECRET().getBytes()));
        }
        return null;
    }

    public String resolveToken(HttpServletRequest request) {
        if (request.getHeader(jwtConfig.getHEADER_NAME()) != null) {
            return request.getHeader(jwtConfig.getHEADER_NAME())
                    .replace(jwtConfig.getTOKEN_PREFIX(), "").trim();
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            DecodedJWT jwt = JWT.require(HMAC512(jwtConfig.getSECRET().getBytes())).build().verify(token);
            return !jwt.getExpiresAt().before(new Date());
        } catch (JWTVerificationException e) {
            log.error("Invalid JWT token trace: ", e);
        }
        return false;
    }

    public Authentication getAuthentication(String token) {
        if (token != null) {
            String username = JWT.require(HMAC512(jwtConfig.getSECRET().getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();

            String authenticationMethod = JWT.require(HMAC512(jwtConfig.getSECRET().getBytes()))
                    .build()
                    .verify(token)
                    .getClaim("authentication_method").asString();

            if (authenticationMethod != null) {
                UserDetails userDetails = accountService.loadUserByUsername(username);

                val userCredentials = new CustomUserCredentials(userDetails.getPassword(), authenticationMethod);
                if (username != null) {
                    return new UsernamePasswordAuthenticationToken(userDetails, userCredentials, userDetails.getAuthorities());
                }
            }

            return null;
        }
        return null;
    }
}
