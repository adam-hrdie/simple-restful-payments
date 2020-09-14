package handler;

import api.Account;
import api.Currency;
import cache.Cache;
import cache.InMemoryCache;
import exceptions.AccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountHandler {
    private static Logger LOG = LoggerFactory.getLogger(AccountHandler.class);

    Cache cache;

    public AccountHandler (Cache cache) {
        this.cache = cache;
    }

    public Account createAccount (String name, String currency, String balanceStr) throws Exception {
        if(name == null)
            throw new AccountException("name cannot be null");

        if(checkCurrency(currency.toUpperCase()))
            throw new AccountException("unknown currency [" + currency + "]");

        double balance;
        try {
            balance = Double.parseDouble(balanceStr);
        } catch (NumberFormatException e) {
            throw new AccountException("balance is NAN");
        }

        if(balance < 0)
            throw new AccountException("balance cannot be below zero");

        return cache.createAndCacheAccount(name, Currency.valueOf(currency.toUpperCase()), balance);
    }

    private static boolean checkCurrency(String currency){
        for (Currency c : Currency.values()) {
            if (c.name().equals(currency)) {
                return true;
            }
        }
        return false;
    }

}
