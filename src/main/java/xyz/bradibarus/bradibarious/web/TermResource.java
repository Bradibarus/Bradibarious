package xyz.bradibarus.bradibarious.web;

import org.springframework.hateoas.Link;

import org.springframework.hateoas.ResourceSupport;
import  static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import xyz.bradibarus.bradibarious.model.Term;

public class TermResource extends ResourceSupport {
    private final Term term;


    public TermResource(Term term) {
        String username =term.getAccount().getUsername();
        this.term = term;
        this.add(linkTo(TermsController.class, username).withRel("bookmarks"));
        this.add(linkTo(methodOn(TermsController.class, username).getTerm(username, term.getId())).withSelfRel());
    }

    public Term getTerm() {
        return term;
    }
}
