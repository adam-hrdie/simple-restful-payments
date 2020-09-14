package api;

public class TransactionSuccessful {
    private final double amount;
    private final Currency currency;
    private final String payee;
    private final String payer;
    private final double newBalance;

    public TransactionSuccessful(Transaction tx, double newBalance) {
        this.amount = tx.getAmount();
        this.currency = tx.getCurrency();
        this.payee = tx.getPayee();
        this.payer = tx.getPayer();
        this.newBalance = newBalance;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionSuccessful that = (TransactionSuccessful) o;

        if (Double.compare(that.amount, amount) != 0) return false;
        if (Double.compare(that.newBalance, newBalance) != 0) return false;
        if (currency != that.currency) return false;
        if (payee != null ? !payee.equals(that.payee) : that.payee != null) return false;
        return payer != null ? payer.equals(that.payer) : that.payer == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(amount);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (payee != null ? payee.hashCode() : 0);
        result = 31 * result + (payer != null ? payer.hashCode() : 0);
        temp = Double.doubleToLongBits(newBalance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
