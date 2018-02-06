package xyz.bradibarus.bradibarious.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.bradibarus.bradibarious.model.Account;
import xyz.bradibarus.bradibarious.service.AccountService;

@RestController
@RequestMapping(value = "/{userId}")
public class AccountController {
    @Autowired
    private final AccountService accountService;

    public AccountController(AccountService service) {
        accountService = service;
    }

    @RequestMapping(method = RequestMethod.POST)
    void addUser(@PathVariable String userId, @RequestBody String password){
        accountService.add(new Account(userId, password));
    }

    @RequestMapping(method = RequestMethod.DELETE)
    void deleteUser(@PathVariable String userId, @RequestBody String password){
        accountService.delete(new Account(userId, password));
    }
}
