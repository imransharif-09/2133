package transfer.api.service;

import io.micronaut.core.util.StringUtils;
import transfer.api.exception.AccountNotFoundException;
import transfer.api.exception.InsufficientBalanceException;
import transfer.api.model.Account;
import transfer.api.repository.AccountRepository;
import transfer.api.validator.MoneyTransferRequestValidator;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class MoneyTransferServiceImpl implements MoneyTransferService {

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private MoneyTransferRequestValidator moneyTransferRequestValidator;

    @Override
    public List<Account> processListAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public String processMoneyTransferRequest(String fromAccountNumber, String toAccountNumber, Double transferAmount) {
        moneyTransferRequestValidator.validateInput(fromAccountNumber, toAccountNumber, transferAmount);
        return performTransfer(fromAccountNumber, toAccountNumber, transferAmount);
    }

    @Override
    public String processAccountBalanceRequest(String accountNumber) {
        Account account = accountRepository.findById(accountNumber);
        if(account == null) {
            throw new AccountNotFoundException("Account Not Found");
        }
        return account.getBalance().toString();
    }

    @Override
    public Account processCreateAccount(String accountNumber, Double initialBalance) {
        if(StringUtils.isEmpty(accountNumber)) {
            throw new IllegalArgumentException("Provided Account is not valid");
        }

        if(Double.compare(initialBalance, 0.0) < 0) {
            throw new IllegalArgumentException("Invalid initial balance provided.");
        }

        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setBalance(initialBalance);

        accountRepository.add(account);
        return account;
    }


    private synchronized String performTransfer(String fromAccountNumber, String toAccountNumber, Double transferAmount) {
        Account fromAccount = accountRepository.findById(fromAccountNumber);

        if(Double.compare(fromAccount.getBalance(),transferAmount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance in From Account");
        }

        Account toAccount = accountRepository.findById(toAccountNumber);
        fromAccount.setBalance(fromAccount.getBalance() - transferAmount);
        toAccount.setBalance(toAccount.getBalance() + transferAmount);

        accountRepository.performTransaction(fromAccount, toAccount);

        return new StringBuffer().append(transferAmount).append(" successfully transferred from Account:").append(fromAccountNumber).append(" to Account:").append(toAccountNumber).append(".").toString();
    }
}
