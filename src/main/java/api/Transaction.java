package api;

public class Transaction {
    private final double amount;
    private final Currency currency;
    private final String payee;
    private final String payer;

    public Transaction(double amount, String payer, String payee, Currency currency) {
        this.amount = amount;
        this.payer = payer;
        this.payee = payee;
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getPayee() {
        return payee;
    }

    public String getPayer() {
        return payer;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", currency=" + currency +
                ", payee='" + payee + '\'' +
                ", payer='" + payer + '\'' +
                '}';
    }
}
