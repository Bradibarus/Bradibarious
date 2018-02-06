import xyz.bradibarus.bradibarious.model.Account;
import xyz.bradibarus.bradibarious.model.Term;
import xyz.bradibarus.bradibarious.service.AccountService;
import xyz.bradibarus.bradibarious.service.TermsService;

public class Hibernatetest {
    public static void main(String[] args) {
        AccountService accountService = new AccountService();
        TermsService termsService = new TermsService();
        accountService.add(new Account("kek", "1997"));
        Account account = accountService.add(new Account("lola", "password"));
        termsService.add(new Term(account, "kek", "кек"));
        termsService.add(new Term(account, "lol", "лол"));
        System.out.println(termsService.findWordsByAccountUsername("lola").iterator().next());
    }
}
