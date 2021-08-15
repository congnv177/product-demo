package vn.nvc.product.base.security;

import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import vn.nvc.product.base.security.model.CustomUserCredentials;
import vn.nvc.product.service.AccountService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private PasswordEncoder passwordEncoder;

    private AccountService accountService;

    public CustomAuthenticationProvider(PasswordEncoder passwordEncoder, AccountService accountService) {
        this.passwordEncoder = passwordEncoder;
        this.accountService = accountService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        val authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
        val username = (String) authentication.getPrincipal();
        if (username == null) {
            throw new BadCredentialsException("Bad credentials");
        }
        val userCredentials = (CustomUserCredentials) authentication.getCredentials();

        UserDetails user = accountService.loadUserByUsername(username);

        val presentedPassword = user.getPassword();
        if(userCredentials.getAuthenticateMethod().equals("password")) {
            // TODO chưa xử lý encode password
//            if (!passwordEncoder.matches(userCredentials.getPassword() ,presentedPassword)) {
//                throw new BadCredentialsException("Password not match");
//            }
            if (!StringUtils.equals(userCredentials.getPassword(), presentedPassword)) {
                throw new BadCredentialsException("Password not match");
            }
        }

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}