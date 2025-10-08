package app.BUS;

import java.util.List;

import app.DAO.AccountDAO;
import app.DTO.Account;

public class AccountBUS {
    private AccountDAO dao;

    public AccountBUS() {
        dao = new AccountDAO();
    }

    public Account Login(String username, String password) {
        return dao.Login(username, password);
    }

    public List<Account> getAll() {
        return dao.getAll();
    }

    public Account getAccountById(int accountId) {
        return dao.getAccountById(accountId);
    }

    public int addAccount(Account account) {
        return dao.addAccount(account);
    }

    public int updateAccount(Account account) {
        return dao.updateAccount(account);
    }

    public int deleteAccount(int accountId) {
        return dao.deleteAccount(accountId);
    }

    public static void main(String[] args) {
        AccountBUS bus = new AccountBUS();
        List<Account> list = bus.getAll();
        for (Account acc : list) {
            System.out.println("Account check: " + acc.toString());
        }
    }
}
