package api;

public class Balance {

    private final Currency currency;
    private final Double balance;

    public Balance(Currency currency, Double balance) {
        this.currency = currency;
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "currency=" + currency +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Balance balance1 = (Balance) o;

        if (currency != balance1.currency) return false;
        return balance != null ? balance.equals(balance1.balance) : balance1.balance == null;
    }

    @Override
    public int hashCode() {
        int result = currency != null ? currency.hashCode() : 0;
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        return result;
    }
}
