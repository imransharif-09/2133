package transfer.api.service;

import transfer.api.model.Account;

import java.util.List;


public interface MoneyTransferService {

    List<Account> processListAccounts();

    String processMoneyTransferRequest(String fromAccountNumber, String toAccountNumber, Double transferAmount);

    String processAccountBalanceRequest(String accountNumber);

    Account processCreateAccount(String accountNumber, Double initialBalance);

}
