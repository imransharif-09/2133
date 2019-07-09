package transfer.api.exception.handler;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import transfer.api.exception.AccountNotFoundException;
import transfer.api.exception.InsufficientBalanceException;
import transfer.api.exception.SameAccountException;

import javax.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {AccountNotFoundException.class, InsufficientBalanceException.class, SameAccountException.class, ExceptionHandler.class})
public class MoneyTransferExceptionHandler implements ExceptionHandler<Exception, HttpResponse> {

    @Override
    public HttpResponse handle(HttpRequest request, Exception exception) {
        return HttpResponse.badRequest(exception.getMessage());
    }
}







