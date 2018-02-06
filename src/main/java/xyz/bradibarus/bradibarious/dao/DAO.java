package xyz.bradibarus.bradibarious.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xyz.bradibarus.bradibarious.model.Account;
import xyz.bradibarus.bradibarious.model.Term;

import javax.persistence.NoResultException;
import java.util.Collection;
import java.util.List;

@Repository
public class DAO {
    @Autowired
    SessionFactory sessionFactory;
    private Session session;
    private Transaction tx;

    public DAO() {
        session = null;
    }

    public Account findAccountByUsername(String username) {
        Account result = null;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }        tx = session.beginTransaction();
        try {
            result =  (Account) session.createQuery("from Account a where a.username =:username ")
                    .setParameter("username", username)
                    .getSingleResult();
            tx.commit();
        }catch(NoResultException e){
            tx.rollback();
            return null;
        }catch(Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return result;
    }

    public Account deleteAccountByUsername(String username) {
        Account toDelete = this.findAccountByUsername(username);
        if(toDelete == null) return null;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }        tx = session.beginTransaction();
        try {
            session.delete(toDelete);
            tx.commit();
        }catch(Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return toDelete;
    }

    public Collection<Term> findTermsByAccountUsername (String username) {
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }        tx = session.beginTransaction();
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
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }        tx = session.beginTransaction();
        try {
            Term term = (Term) session.createQuery("from Term t where t.id =:id")
                    .setParameter("id", id)
                    .getSingleResult();
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
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        tx = session.getTransaction();
        if(tx == null) tx = session.beginTransaction();
        if(!tx.isActive()) tx.begin();
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
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        tx = session.beginTransaction();
        try{
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
