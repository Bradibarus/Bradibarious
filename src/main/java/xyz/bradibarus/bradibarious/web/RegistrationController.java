package xyz.bradibarus.bradibarious.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import xyz.bradibarus.bradibarious.model.Account;
import xyz.bradibarus.bradibarious.service.AccountService;

import javax.annotation.security.RolesAllowed;

@Secured({"ROLE_GUEST"})
@Controller
@RequestMapping("/register/{userId}")
public class RegistrationController {

    @Autowired
    AccountService accountService;

    @RequestMapping(method = RequestMethod.GET)
    String testGet() {
        return "You are successfully getting response from RegistrationController";
    }

    @RequestMapping(method = RequestMethod.POST)
    Account addAccount(@PathVariable String userId, @RequestBody String password) {
        return accountService.add(new Account(userId, password));
    }
}
