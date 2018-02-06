package xyz.bradibarus.bradibarious;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import xyz.bradibarus.bradibarious.model.Account;
import xyz.bradibarus.bradibarious.model.Term;
import xyz.bradibarus.bradibarious.service.AccountService;
import xyz.bradibarus.bradibarious.service.TermsService;

import java.util.Arrays;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(AccountService accountService, TermsService termsService) {
        return (args) ->
                Arrays.asList("jhoeller,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong".split(","))
                        .forEach(a -> {
                            Account account = accountService.add(new Account(a, "1337password"));
                            termsService.add(new Term(account, "Oh my fucking god", ""));
                            termsService.add(new Term(account, "Are u fucking kidding me", ""));
                            termsService.add(new Term(account, "Send nudes", ""));

                        });
    }
}
