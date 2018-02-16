package xyz.bradibarus.bradibarious.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.bradibarus.bradibarious.model.Account;
import xyz.bradibarus.bradibarious.service.AccountService;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@RestController
@RequestMapping(value = "api/account")
public class AccountController {
    @Autowired
    private final AccountService accountService;

    public AccountController(AccountService service) {
        accountService = service;
    }

    @Secured({"ROLE_USER"})
    @RequestMapping(method = RequestMethod.GET)
    Account testGet(Principal principal) {
        return accountService.findByUsername(principal.getName()).get();
    }

    @Secured({"ROLE_USER"})
    @RequestMapping(method = RequestMethod.DELETE)
    Account deleteUser(Principal principal, @RequestBody String password){
        return accountService.delete(new Account(principal.getName(), password))
            .orElseThrow(()->new UserNotFoundException(principal.getName()));
    }

    @PreAuthorize("isAnonymous()")
    @RequestMapping(method = RequestMethod.POST, value = "/{userId}")
    Account addUser(@RequestBody String password, @PathVariable String userId) {
        return accountService.add(new Account(userId, password));
    }

}
