package interfaces;

public interface IServicePayment extends ITransaction {
    void verifyInvoice();

    void payService();

    boolean createTransaction();
}