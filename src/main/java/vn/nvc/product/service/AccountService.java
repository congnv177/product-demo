package vn.nvc.product.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.nvc.product.dto.AccountDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService implements UserDetailsService {

    private final List<AccountDto> USERS = addUser();

    @Override
    public UserDetails loadUserByUsername(String username) {
        AccountDto account = USERS.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
        if (account == null) {
            throw new UsernameNotFoundException(username);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new User(account.getUsername(), account.getPassword(), authorities);
    }

    private List<AccountDto> addUser() {
        List<AccountDto> users = new ArrayList<>();
        users.add(new AccountDto(1, "congnv", "123456"));
        users.add(new AccountDto(2, "vannt", "654321"));

        return users;
    }

    public AccountDto getAccountFromSecurityContext() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return USERS.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
    }
}
