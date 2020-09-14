package api;

import utils.DoubleUtil;

import static utils.DoubleUtil.*;
import static utils.DoubleUtil.round;

public class Account {
    private final String name;
    private final Currency currency;
    private volatile Double balance;

    public Account(String name, Currency currency, Double balance) {
        this.name = name;
        this.currency = currency;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public Double getBalance() {
        return balance;
    }

    public double incrementAndGetBalance(double amount) {
        this.balance = round(add(balance, amount));
        return this.balance;
    }

    public double decrementAndGetBalance(double amount) {
        this.balance = round(subtract(balance, amount));
        return this.balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", currency=" + currency +
                ", balance=" + balance +
                '}';
    }
}
