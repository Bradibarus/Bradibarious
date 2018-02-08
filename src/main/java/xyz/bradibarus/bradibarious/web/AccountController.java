package xyz.bradibarus.bradibarious.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import xyz.bradibarus.bradibarious.model.Account;
import xyz.bradibarus.bradibarious.service.AccountService;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@Secured({"ROLE_USER"})
@RestController
@RequestMapping(value = "/account")
public class AccountController {
    @Autowired
    private final AccountService accountService;

    public AccountController(AccountService service) {
        accountService = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    String testGet() {
        return "You are successfully getting response from AccountController";
    }

    @RequestMapping(method = RequestMethod.DELETE)
    Account deleteUser(Principal principal, @RequestBody String password){
        return accountService.delete(new Account(principal.getName(), password))
            .orElseThrow(()->new UserNotFoundException(principal.getName()));
    }
}
