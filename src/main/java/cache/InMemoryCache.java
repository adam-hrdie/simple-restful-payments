package cache;

import api.Account;
import api.Currency;
import exceptions.PaymentFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Optional.ofNullable;

public class InMemoryCache implements Cache {

    private static Logger LOG = LoggerFactory.getLogger(InMemoryCache.class);
    private final Map<AccountKey, Account> accountsMap = new ConcurrentHashMap <>();

    /*
    todo - if we add withdraw / deposit methods we need to think about concurrent access to this cache!!
    Currently as the only  method is a payment the only access is via PaymentHandler.
    */

    @Override
    public synchronized Account createAndCacheAccount(String name, Currency currency, double balance) {
        accountsMap.merge(new AccountKey(name, currency), new Account(name, currency, balance), (v1, v2) -> {
            LOG.warn("attempt to create a duplicate account name=[{}] currency=[{}]", name, currency);
            throw new IllegalArgumentException("Duplicate account [" + name + "] for currency [" + currency + "]" );
        });
        return accountsMap.get(name.toLowerCase());
    }

    @Override
    public synchronized double increaseAccountBalance(String name, Currency currency, double amount) {
        AccountKey key = new AccountKey(name, currency);
        LOG.trace("Start increase to balance amount=[{}] accountKey=[{}]", amount, key);
        accountsMap.computeIfAbsent(key, v -> new Account(name, currency, 0d));
        double updatedBalance = accountsMap.get(key).incrementAndGetBalance(amount);
        LOG.trace("End increase to balance : resulting balance=[{}] accountKey=[{}]", updatedBalance, key);
        return updatedBalance;
    }

    @Override
    public synchronized double decreaseAccountBalance(String name, Currency currency, double amount) throws PaymentFailedException {
        AccountKey key = new AccountKey(name, currency);
        LOG.trace("Start decrease to wallet amount=[{}] wallet key=[{}]", amount, key);
        Account account = accountsMap.get(key);

        if(account == null)
            throw new PaymentFailedException("Account has no funds for payment currency");
        if(account.getBalance() < amount)
            throw new PaymentFailedException("Account does not have required funds for payment");

        double updatedBalance = account.decrementAndGetBalance(-amount);
        LOG.trace("End decrease to wallet : resulting balance=[{}] wallet key=[{}]", updatedBalance, key);
        return updatedBalance;
    }

    @Override
    public Optional<Account> getOptionalAccount(String name) {
        return ofNullable(accountsMap.get(name.toLowerCase()));
    }
}
