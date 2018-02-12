package xyz.bradibarus.bradibarious.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.bradibarus.bradibarious.service.AccountService;
import xyz.bradibarus.bradibarious.web.UserNotFoundException;

@Service
public class AccountDetailsService implements UserDetailsService {
    @Autowired
    AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return accountService.findByUsername(s).map(a->
            new User(a.getUsername(), a.getPassword(), true, true, true, true, AuthorityUtils.createAuthorityList("ROLE_USER"))
        ).orElseThrow(()->new UserNotFoundException(s));
    }
}
