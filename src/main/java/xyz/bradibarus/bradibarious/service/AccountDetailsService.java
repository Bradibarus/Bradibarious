package xyz.bradibarus.bradibarious.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import xyz.bradibarus.bradibarious.model.Account;
import xyz.bradibarus.bradibarious.web.UserNotFoundException;

@Service
public class AccountDetailsService implements UserDetailsService {
    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UserNotFoundException {
        accountService.findByUsername(s).map(AccountDetails::new).orElseThrow(()->new UserNotFoundException(s));
        return null;
    }
}
