package transfer.api.dataloader;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.discovery.event.ServiceStartedEvent;
import transfer.api.model.Account;
import transfer.api.repository.AccountRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;


@Singleton
public class DataLoader implements ApplicationEventListener<ServiceStartedEvent> {


    @Inject
    private AccountRepository accountRepository;

    @Override
    public void onApplicationEvent(ServiceStartedEvent event) {
        loadAccountsData();
    }

    private void loadAccountsData() {
        List<Account> list = new ArrayList<>();

        list.add(createAccount("XX123", 200.0));
        list.add(createAccount("YY456", 400.0));
        accountRepository.saveAll(list);
    }

    private Account createAccount(String accountId, Double amount) {
        Account account = new Account();
        account.setAccountNumber(accountId);
        account.setBalance(amount);
        return account;
    }
}
