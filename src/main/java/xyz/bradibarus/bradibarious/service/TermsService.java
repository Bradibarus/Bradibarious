package xyz.bradibarus.bradibarious.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.bradibarus.bradibarious.dao.DAO;
import xyz.bradibarus.bradibarious.model.Term;

import java.util.Collection;
import java.util.List;

@Service
public class TermsService {
    @Autowired
    private DAO dao;
    private List<Term> words;
    @Autowired
    private TranslationService translationService;

    public TermsService() {

    }

    //@Transactional
    public Term add(Term term){
        if(term.getWord2().isEmpty()){
            //do translation
            String translated = translationService.translate(term.getWord1());
            term = new Term(term.getAccount(), term.getWord1(), translated);
        }
        dao.persist(term);
        return term;
    }

    public Collection<Term> findWordsByAccountUsername (String username) {
        return dao.findTermsByAccountUsername(username);
    }

    public Term findOne (Long id){
        return dao.findTermById(id);
    }

    public void deleteOne (long id) {
        dao.deleteTerm(id);
    }
}
