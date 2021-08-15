package vn.nvc.product.controller;

import lombok.val;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.nvc.product.base.security.JwtTokenProvider;
import vn.nvc.product.base.security.model.CustomUserCredentials;
import vn.nvc.product.model.account.LoginRequest;
import vn.nvc.product.model.account.LoginResponse;
import vn.nvc.product.service.AccountService;

@RestController
@RequestMapping("/admin/accounts")
public class AccountController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider tokenProvider;
    private final AccountService accountService;

    public AccountController(AuthenticationManagerBuilder authenticationManagerBuilder, JwtTokenProvider tokenProvider,
                             AccountService accountService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;
        this.accountService = accountService;
    }

    @PostMapping("/login")
    public LoginResponse authenticateUser(@RequestBody LoginRequest request) {
        val userDetails = accountService.loadUserByUsername(request.getUsername());
        val userCredentials = new CustomUserCredentials(request.getPassword().toLowerCase(), "password");
        val authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), userCredentials, userDetails.getAuthorities());
        val authentication = this.authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        val token = tokenProvider.createToken(request.getUsername(), "password");

        return new LoginResponse(userDetails.getUsername(), token);
    }
}
