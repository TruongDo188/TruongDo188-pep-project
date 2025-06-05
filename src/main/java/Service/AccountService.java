package Service;
import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;
    
    public AccountService() {
        this.accountDAO = new AccountDAO();
    }
 public Account register(Account account) {
    return accountDAO.insert(account);
 }
 public boolean usernameExists(String username) {
   return accountDAO.findByUsername(username) != null;
 }
 public Account login(Account credentials) {
    return accountDAO.findByUsernameAndPassword(
            credentials.getUsername(),
            credentials.getPassword()

    );
 }
}
