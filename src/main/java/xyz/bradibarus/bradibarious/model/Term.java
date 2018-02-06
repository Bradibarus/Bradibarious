package xyz.bradibarus.bradibarious.domain;

import net.minidev.json.annotate.JsonIgnore;
import xyz.bradibarus.bradibarious.domain.Account;

import javax.persistence.*;

@Entity
public class Term{
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Account account;

    @Column
    private String word1;
    @Column
    private String word2;

    private Term (){}

    public Term (final Account account, final String word1, final String word2){
        this.account = account;
        this.word1 = word1;
        this.word2 = word2;
    }

    public Account getAccount() {
        return account;
    }

    public String getWord1() {
        return word1;
    }

    public String getWord2() {
        return word2;
    }

    @Override
    public String toString(){
        return this.getClass().toString() + "[" + this.account + ", " + this.word1 + ", " + this.word2 + "]";
    }
}
