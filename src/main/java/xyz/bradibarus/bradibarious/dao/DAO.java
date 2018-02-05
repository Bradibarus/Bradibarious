package xyz.bradibarus.bradibarious.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import xyz.bradibarus.bradibarious.domain.Account;
import xyz.bradibarus.bradibarious.domain.Term;

import java.util.Collection;
import java.util.List;

public class DAO {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction tx;

    public DAO() {
        this.sessionFactory = new Configuration()
                .configure()
                .addAnnotatedClass(Account.class)
                .addAnnotatedClass(Term.class)
                .buildSessionFactory();
        session = null;
    }

    public Account findAccountByUsername(String username) {
        session = sessionFactory.getCurrentSession();
        tx = session.beginTransaction();
        try {
            Account account =  (Account) session.createQuery("from Account a where a.username =:username ")
                    .setParameter("username", username)
                    .setMaxResults(1);
            tx.commit();
            return account;
        }catch(Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    public Collection<Term> findTermsByAccountUsername (String username) {
        session = sessionFactory.getCurrentSession();
        tx = session.beginTransaction();
        try {
            List<Term> termsList = session.createQuery("from Term t where t.account.username =:username")
                    .setParameter("username", username)
                    .list();
            tx.commit();
            return termsList;
        }catch(Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    public Term findTermById(Long id) {
        session = sessionFactory.getCurrentSession();
        tx = session.beginTransaction();
        try {
            Term term = (Term) session.createQuery("from Term t where t.id =:id")
                    .setParameter("id", id)
                    .setMaxResults(1);
            tx.commit();
            return term;
        }catch(Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    public void persist(Account account){
        session = sessionFactory.getCurrentSession();
        tx = session.beginTransaction();
        try{
            session.save(account);
            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public void persist(Term term){
        session = sessionFactory.getCurrentSession();
        tx = session.beginTransaction();
        try {
            session.save(term);
            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
}
