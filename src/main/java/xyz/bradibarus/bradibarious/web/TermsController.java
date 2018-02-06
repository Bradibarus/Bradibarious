package xyz.bradibarus.bradibarious.web;

import com.sun.jndi.toolkit.url.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import xyz.bradibarus.bradibarious.model.Account;
import xyz.bradibarus.bradibarious.model.Term;
import xyz.bradibarus.bradibarious.service.AccountService;
import xyz.bradibarus.bradibarious.service.TermsService;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    Resources<TermResource> getTerms(@PathVariable String userId) {
        validateUser(userId);
        List<TermResource> termResourceList = termsService.findWordsByAccountUsername(userId).stream().map(TermResource::new).collect(Collectors.toList());
        return new Resources<>(termResourceList);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String userId, @RequestBody Term input){
        validateUser(userId);
        return accountService.findByUsername(userId).map(account -> {
            Term term = termsService.add(new Term(account, input.getWord1(), input.getWord2()));
            Link termLink = new TermResource(term).getLink("self");
            return ResponseEntity.created(URI.create(termLink.getHref())).build();
        }).orElse(ResponseEntity.noContent().build());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{termId}", produces = MediaType.APPLICATION_JSON_VALUE)
    TermResource getTerm(@PathVariable String userId, @PathVariable Long termId) {
        validateUser(userId);
        return new TermResource(termsService.findOne(termId));
    }

    private void validateUser(String userId) {
        this.accountService.findByUsername(userId).orElseThrow(()->new UserNotFoundException(userId));
    }
}
