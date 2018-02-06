package xyz.bradibarus.bradibarious.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.bradibarus.bradibarious.dao.DAO;
import xyz.bradibarus.bradibarious.model.Account;

import java.util.Optional;


@Service
public class AccountService {
    @Autowired
    private DAO dao;

    public AccountService() {
        this.dao = new DAO();
    }

    //@Transactional
    public Account add(Account account){
        dao.persist(account);
        return account;
    }

    public Optional<Account> findByUsername(String username){
        return  Optional.ofNullable(dao.findAccountByUsername(username));
    }

    public Optional<Account> delete(Account account){
        return Optional.ofNullable(dao.deleteAccountByUsername(account.getUsername()));
    }
}
