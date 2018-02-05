package xyz.bradibarus.bradibarious.service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import xyz.bradibarus.bradibarious.dao.DAO;
import xyz.bradibarus.bradibarious.domain.Account;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    private DAO dao;
//
    public AccountService() {
        this.dao = new DAO();
        //accountList = new ArrayList<>();
    }

    public Account add(Account account){
        dao.persist(account);
        return account;
    }

    public Account findByUsername(String username){
        return  dao.findAccountByUsername(username);
    }
}
