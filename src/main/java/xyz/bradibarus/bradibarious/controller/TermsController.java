package xyz.bradibarus.bradibarious.controller;

import com.sun.jndi.toolkit.url.Uri;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import xyz.bradibarus.bradibarious.model.Account;
import xyz.bradibarus.bradibarious.model.Term;
import xyz.bradibarus.bradibarious.service.AccountService;
import xyz.bradibarus.bradibarious.service.TermsService;

import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Secured({"ROLE_USER"})
@RestController
@RequestMapping("api/terms")
public class TermsController {
    @Autowired
    private final AccountService accountService;
    @Autowired
    private final TermsService termsService;

    TermsController(AccountService accountService, TermsService termsService){
        this.accountService = accountService;
        this.termsService = termsService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    Resources<TermResource> getTerms(Principal principal) {
        validateUser(principal);
        List<TermResource> termResourceList = termsService.findWordsByAccountUsername(principal.getName()).stream().map(TermResource::new).collect(Collectors.toList());
        return new Resources<>(termResourceList);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    TermResource add(Principal principal, @RequestBody Term input) {
        validateUser(principal);
        return accountService.findByUsername(principal.getName()).map(name -> {
            Term term = termsService.add(new Term(name, input.getWord1(), input.getWord2()));
            return new TermResource(term);
        }).orElseThrow(()->new UserNotFoundException(principal.getName()));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{termId}", produces = MediaType.APPLICATION_JSON_VALUE)
    TermResource getTerm(Principal principal, @PathVariable Long termId) {
        validateUser(principal);
        return new TermResource(termsService.findOne(termId));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{termId}")
    void delete(Principal principal, @PathVariable long termId){
        validateUser(principal);
        termsService.deleteOne(termId);
    }

    private void validateUser(Principal principle) {
        String userId = principle.getName();
        this.accountService.findByUsername(userId)
                .orElseThrow(()->new UserNotFoundException(userId));
    }
}
