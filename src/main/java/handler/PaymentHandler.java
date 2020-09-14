package handler;

import api.*;
import cache.Cache;
import cache.InMemoryCache;
import exceptions.AccountException;
import exceptions.PaymentFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static utils.DoubleUtil.format;

public class PaymentHandler {
    private static Logger LOG = LoggerFactory.getLogger(PaymentHandler.class);

    Cache cache;

    public PaymentHandler(Cache cache) {
        this.cache = cache;
    }

    public synchronized TransactionSuccessful makePayment(Transaction tx) throws AccountException, PaymentFailedException {
        LOG.info("handle transaction [{}]", tx);
        Optional<Account> payer = cache.getOptionalAccount(tx.getPayer(), tx.getCurrency());
        Optional<Account> payee = cache.getOptionalAccount(tx.getPayee(), tx.getCurrency());

        if(payee.isPresent() == false)
            throw new AccountException("payee account not found name = [" + payee + "] currency = [" + tx.getCurrency() + "]");
        if(payer.isPresent() == false)
            throw new AccountException("payer account not found name = [" + payer + "] currency = [" + tx.getCurrency() + "]");

        double newBalance = cache.decreaseAccountBalance(payer.get().getName(), tx.getCurrency(), tx.getAmount());
        cache.increaseAccountBalance(payee.get().getName(), tx.getCurrency(), +tx.getAmount());
        LOG.info("transaction successfully procesed [{}] new payer balance = [{}]", tx, format(newBalance));
        return new TransactionSuccessful(tx, newBalance);
    }

}
