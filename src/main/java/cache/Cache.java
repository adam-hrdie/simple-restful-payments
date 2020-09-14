package cache;

import api.Account;
import api.Currency;
import exceptions.PaymentFailedException;

import java.util.Optional;

public interface Cache {
    Account createAndCacheAccount(String name, Currency currency, double balance);

    double increaseAccountBalance(String name, Currency currency, double amount);

    double decreaseAccountBalance(String name, Currency currency, double amount) throws PaymentFailedException;

    Optional<Account> getOptionalAccount(String name);
}
