package transfer.api.validator;

import io.micronaut.core.util.StringUtils;
import transfer.api.exception.SameAccountException;

import javax.inject.Singleton;

@Singleton
public class MoneyTransferRequestValidator {

    public void validateInput(String fromAccountNumber, String toAccountNumber, Double transferAmount) {
        if(StringUtils.isEmpty(fromAccountNumber)) {
            throw new IllegalArgumentException("Invalid From Account provided");
        }

        if(StringUtils.isEmpty(toAccountNumber)) {
            throw new IllegalArgumentException("Invalid To Account provided");
        }

        if(fromAccountNumber.equalsIgnoreCase(toAccountNumber)) {
            throw new SameAccountException("From and To accounts are same");
        }

        if(transferAmount < 1) {
            throw new IllegalArgumentException("Invalid Transfer Amount provided");
        }
    }
}
