package xyz.bradibarus.bradibarious.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "account")
    private Set<Term> termSet = new HashSet<>();

    private Account() {}

    public Account(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    //public Set<WordPair> getWordsSet() {
    //    return wordsSet;
    //}

    @Override
    public String toString(){
        return this.getClass().toString() + "[" + this.username + "]";
    }
}
