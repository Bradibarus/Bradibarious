package xyz.bradibarus.bradibarious.service;

import org.springframework.stereotype.Service;
import xyz.bradibarus.bradibarious.dao.DAO;
import xyz.bradibarus.bradibarious.domain.Term;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class TermsService {

    private DAO dao;
    private List<Term> words;
    private TranslationService translationService;

    public TermsService() {
        words = new ArrayList<>();
        this.dao = new DAO();
    }

    public void add(Term term){
        if(term.getWord2().isEmpty()){
            //do translation
            String translated = translationService.translate(term.getWord1());
            term = new Term(term.getAccount(), term.getWord1(), translated);
        }
        dao.persist(term);
    }

    public Collection<Term> findWordsByAccountUsername (String username) {
        return dao.findTermsByAccountUsername(username);
    }

    public Term findOne (Long id){
        return dao.findTermById(id);
    }
}
