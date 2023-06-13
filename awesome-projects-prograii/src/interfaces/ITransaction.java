package interfaces;

public interface ITransaction {
    String getUUID();

    double getAmount();

    String getTransactionType();

    String getSummaryLine();

    abstract boolean createTransaction();
}