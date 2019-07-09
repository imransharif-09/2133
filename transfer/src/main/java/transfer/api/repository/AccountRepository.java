package transfer.api.repository;

import transfer.api.model.Account;

import java.util.List;


public interface AccountRepository {

    Account findById(String accountNumber);

    List<Account> findAll();

    void saveAll(List<Account> accounts);

    void performTransaction(Account fromAccount, Account toAccount);

    void add(Account account);
}
