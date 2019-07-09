package transfer.api.service;


import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import transfer.api.exception.AccountNotFoundException;
import transfer.api.model.Account;
import transfer.api.repository.AccountRepository;
import transfer.api.validator.MoneyTransferRequestValidator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MoneyTransferServiceTest {

    private static final String TEST_VALID_ACCOUNT_NUMBER = "XX123";
    private static final String TEST_INVALID_ACCOUNT_NUMBER = "XX123Ivalid";

    private static final String TEST_VALID_TO_ACCOUNT_NUMBER = "YY456";

    private static final Double TEST_ACCOUNT_BALANCE = 10.0;
    private static final Double TEST_TRANSFER_MONEY = 1.0;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private MoneyTransferRequestValidator moneyTransferRequestValidator;

    @InjectMocks
    private MoneyTransferServiceImpl underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testProcessMoneyTransferRequestWhenAllParametersAreValid() {
        when(accountRepository.findById(ArgumentMatchers.any())).thenReturn(createValidMockAccount());
        underTest.processMoneyTransferRequest(TEST_VALID_ACCOUNT_NUMBER, TEST_VALID_TO_ACCOUNT_NUMBER, TEST_TRANSFER_MONEY);

        verify(accountRepository, times(2)).findById(ArgumentMatchers.any());
        verify(accountRepository, times(1)).performTransaction(ArgumentMatchers.any(), ArgumentMatchers.any());
        verify(accountRepository, times(2)).findById(ArgumentMatchers.any());
    }

    @Test
    public void testProcessAccountBalanceRequestReturnAccountBalanceWhenAccountIsValid() {
        Account mockAccount = createValidMockAccount();
        when(accountRepository.findById(ArgumentMatchers.any())).thenReturn(createValidMockAccount());

        String result = underTest.processAccountBalanceRequest(TEST_VALID_ACCOUNT_NUMBER);

        verify(accountRepository, times(1)).findById(ArgumentMatchers.anyString());

        assertEquals(TEST_ACCOUNT_BALANCE.toString(), result);
    }

    @Test(expected = AccountNotFoundException.class)
    public void testProcessAccountBalanceRequestReturnAccountBalanceWhenAccountIsInValid() {
        when(accountRepository.findById(ArgumentMatchers.any())).thenReturn(null);
        underTest.processAccountBalanceRequest(TEST_INVALID_ACCOUNT_NUMBER);
    }

    private Account createValidMockAccount() {
        Account account = new Account();
        account.setAccountNumber(TEST_VALID_ACCOUNT_NUMBER);
        account.setBalance(TEST_ACCOUNT_BALANCE);
        return account;
    }
}