package xyz.bradibarus.bradibarious.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.bradibarus.bradibarious.domain.Account;
import xyz.bradibarus.bradibarious.domain.Term;
import xyz.bradibarus.bradibarious.service.AccountService;
import xyz.bradibarus.bradibarious.service.TermsService;

import java.util.Collection;

@RestController
@RequestMapping("/{userId}/terms")
public class TermsController {
    @Autowired
    private final AccountService accountService;
    @Autowired
    private final TermsService termsService;

    TermsController(AccountService accountService, TermsService termsService){
        this.accountService = accountService;
        this.termsService = termsService;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Term> getWords(@PathVariable String userId) {
        return this.termsService.findWordsByAccountUsername(userId);
    }

    @RequestMapping(method = RequestMethod.POST)
    void add(@PathVariable String userId, @RequestBody Term input){
        Account account = accountService.findByUsername(userId);
        termsService.add(new Term(account, input.getWord1(), input.getWord2()));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{termId}")
    Term getTerm(@PathVariable String userId, @PathVariable Long termId) {
        return termsService.findOne(termId);
    }
}
