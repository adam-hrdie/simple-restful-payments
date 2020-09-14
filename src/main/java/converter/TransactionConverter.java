package converter;

import api.Currency;
import api.Transaction;
import utils.DoubleUtil;

public class TransactionConverter {

    public TransactionConverter() {
    }

    public static Transaction convertParamsToTx(String amountStr, String fromStr, String toStr, String currencyStr) throws Exception {
        double amount = DoubleUtil.round(Double.valueOf(amountStr));
        Currency currency = Currency.valueOf(currencyStr);
        return new Transaction(amount, fromStr, toStr, currency);
    }
}
