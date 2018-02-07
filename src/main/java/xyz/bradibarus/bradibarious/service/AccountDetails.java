package xyz.bradibarus.bradibarious.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.bradibarus.bradibarious.model.Account;

import java.util.ArrayList;
import java.util.Collection;

public class AccountDetails implements UserDetails {
    private final Account account;

    public AccountDetails(Account account) {
        this.account = account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        return authorities;
    }

    @Override
    public String getPassword() {
        return account.getUsername();
    }

    @Override
    public String getUsername() {
        return account.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
