package transfer.api.validator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import transfer.api.exception.SameAccountException;

public class MoneyTransferRequestValidatorTest {

    private static final String TEST_VALID_ACCOUNT_NUMBER = "XX123";
    private static final String TEST_VALID_TO_ACCOUNT_NUMBER = "YY456";
    private static final Double TEST_TRANSFER_MONEY = 1.0;

    @InjectMocks
    private MoneyTransferRequestValidator underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessMoneyTransferRequestThrowExceptionWhenFromAccountIsNullEmpty() {
        underTest.validateInput(null, TEST_VALID_TO_ACCOUNT_NUMBER, TEST_TRANSFER_MONEY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessMoneyTransferRequestThrowExceptionWhenToAccountIsNullEmpty() {
        underTest.validateInput(TEST_VALID_ACCOUNT_NUMBER, null, TEST_TRANSFER_MONEY);
    }

    @Test(expected = SameAccountException.class)
    public void testProcessMoneyTransferRequestThrowExceptionWhenBothAccountsAreSame() {
        underTest.validateInput(TEST_VALID_ACCOUNT_NUMBER, TEST_VALID_ACCOUNT_NUMBER, TEST_TRANSFER_MONEY);
    }
}