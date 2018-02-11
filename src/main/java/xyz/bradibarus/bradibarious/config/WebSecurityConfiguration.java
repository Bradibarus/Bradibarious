package xyz.bradibarus.bradibarious.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import xyz.bradibarus.bradibarious.service.AccountService;

@Configuration
@EnableGlobalMethodSecurity(
        securedEnabled = true)
public class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    AccountService accountService;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    protected UserDetailsService userDetailsService() {
        return (username) -> accountService.findByUsername(username)
                .map(a -> new User(a.getUsername(), a.getPassword(), true, true, true, true,
                        AuthorityUtils.createAuthorityList("ROLE_USER", "write")))
                //.orElseThrow(() -> new UserNotFoundException(username));
        .orElse(new User("guest", " ", true, true, true, true,
        AuthorityUtils.createAuthorityList("ROLE_GUEST")));
    }
}
