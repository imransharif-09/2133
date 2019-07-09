package transfer.api.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import transfer.api.model.Account;
import transfer.api.service.MoneyTransferService;

import javax.inject.Inject;
import java.util.List;


@Controller
public class MoneyTransferController {

    @Inject
    private MoneyTransferService moneyTransferService;


    @Get("/list-accounts")
    public HttpResponse<List<Account>> listAccounts() {
        return HttpResponse.ok(moneyTransferService.processListAccounts());
    }

    @Post("/transfer-money")
    public HttpResponse<String> transferMoney(String fromAccountNumber, String toAccountNumber, Double transferAmount) {
        return HttpResponse.ok(moneyTransferService.processMoneyTransferRequest(fromAccountNumber, toAccountNumber, transferAmount));
    }

    @Get("/get-balance")
    public HttpResponse<String> getBalance(String accountNumber) {
        return HttpResponse.ok(moneyTransferService.processAccountBalanceRequest(accountNumber));
    }

    @Post("/create-account")
    public HttpResponse<String> createAccount(String accountNumber, Double initialBalance) {
        Account account = moneyTransferService.processCreateAccount(accountNumber, initialBalance);
        return HttpResponse.created(account.toString());
    }
}
