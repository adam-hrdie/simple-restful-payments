package cache;

import api.Currency;

public class AccountKey {
    final String name;
    final Currency currency;

    public AccountKey(String name, Currency currency) {
        this.name = name;
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountKey that = (AccountKey) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return currency == that.currency;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AccountKey{" +
                "name='" + name + '\'' +
                ", currency=" + currency +
                '}';
    }
}