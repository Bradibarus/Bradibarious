package xyz.bradibarus.bradibarious.web;

import org.springframework.hateoas.Link;

import org.springframework.hateoas.ResourceSupport;
import  static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import xyz.bradibarus.bradibarious.model.Term;

import java.security.Principal;

public class TermResource extends ResourceSupport {
    private final Term term;


    public TermResource(Term term) {
        this.term = term;
        Principal principal = new AccountPrincipal(term.getAccount());
        this.add(linkTo(TermsController.class, principal).withRel("terms"));
        this.add(linkTo(methodOn(TermsController.class, principal).getTerm(principal, term.getId())).withSelfRel());
    }

    public Term getTerm() {
        return term;
    }
}
