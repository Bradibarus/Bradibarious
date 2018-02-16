package xyz.bradibarus.bradibarious.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import xyz.bradibarus.bradibarious.model.Account;
import xyz.bradibarus.bradibarious.model.Term;

import javax.persistence.NoResultException;
import java.util.Collection;
import java.util.List;

@Repository
@Service
@Transactional
public class DAO {
    @Autowired
    SessionFactory sessionFactory;

    //private Transaction tx;

    public DAO() {
        //sessionFactory = transactionManager.getSessionFactory();
    }

    public Account findAccountByUsername(String username) {
        return (Account) sessionFactory.getCurrentSession().createQuery("from Account a where a.username =:username ")
                    .setParameter("username", username)
                    .getSingleResult();
    }

    public Account deleteAccountByUsername(String username) {
        Account toDelete = this.findAccountByUsername(username);
        sessionFactory.getCurrentSession().delete(toDelete);
        return toDelete;
    }

    public Collection<Term> findTermsByAccountUsername (String username) {
        return sessionFactory.getCurrentSession().createQuery("from Term t where t.account.username =:username")
                    .setParameter("username", username)
                    .list();
    }

    public Term findTermById(Long id) {
        return (Term) sessionFactory.getCurrentSession().createQuery("from Term t where t.id =:id")
                    .setParameter("id", id)
                    .getSingleResult();
    }

    public Account persist(Account account){
        sessionFactory.getCurrentSession().save(account);
        return account;
    }

    public Term persist(Term term){
        sessionFactory.getCurrentSession().save(term);
            return term;
    }

    public void deleteTerm(long id) {
        sessionFactory.getCurrentSession().delete(this.findTermById(id));
    }
}
