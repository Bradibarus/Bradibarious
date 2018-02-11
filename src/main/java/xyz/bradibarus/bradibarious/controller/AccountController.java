package xyz.bradibarus.bradibarious.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import xyz.bradibarus.bradibarious.model.Account;
import xyz.bradibarus.bradibarious.service.AccountService;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@RestController
@RequestMapping(value = "/account/{userId}")
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

    @Secured({"ROLE_GUEST"})
    @RequestMapping(method = RequestMethod.POST)
    Account addUser(@RequestBody String password, @PathVariable String userId) {
        return accountService.add(new Account(userId, password));
    }

}
