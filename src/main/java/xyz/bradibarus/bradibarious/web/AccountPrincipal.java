package xyz.bradibarus.bradibarious.web;

import xyz.bradibarus.bradibarious.model.Account;

import java.security.Principal;

public class AccountPrincipal implements Principal {
    private final Account account;

    public AccountPrincipal(Account account) {
        this.account = account;
    }

    @Override
    public String getName() {
        return account.getUsername();
    }
}
