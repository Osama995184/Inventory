package inventory;

public class Invoices {

    private int invoice_id;
    private double total_quantity;
    private double total_returns;
    private double total_price_before_discount;
    private double discount_percentage;
    private double total_price_after_discount;
    private double cashPay;
    private double instaPay;
    private double vodafonePay;
    private double deffered;
    private String username;
    private String invoice_date;
    private String clientName;
    private String clientPhone;

    public Invoices(int invoice_id, double total_quantity, double total_price_before_discount, double discount_percentage, double total_price_after_discount, double cashPay, double instaPay, double vodafonePay, double deffered, String username, String invoice_date, String clientName, String clientPhone, double total_returns) {
        this.invoice_id = invoice_id;
        this.total_quantity = total_quantity;
        this.total_price_before_discount = total_price_before_discount;
        this.total_price_after_discount = total_price_after_discount;
        this.discount_percentage = discount_percentage;
        this.cashPay = cashPay;
        this.instaPay = instaPay;
        this.vodafonePay = vodafonePay;
        this.deffered = deffered;
        this.username = username;
        this.invoice_date = invoice_date;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.total_returns = total_returns;
    }

    public Invoices(int invoice_id, double total_quantity, double total_price_before_discount, double discount_percentage, double total_price_after_discount, double cashPay, double instaPay, double vodafonePay, double deffered, String username, String invoice_date, String clientName, String clientPhone) {
        this.invoice_id = invoice_id;
        this.total_quantity = total_quantity;
        this.total_price_before_discount = total_price_before_discount;
        this.total_price_after_discount = total_price_after_discount;
        this.discount_percentage = discount_percentage;
        this.cashPay = cashPay;
        this.instaPay = instaPay;
        this.vodafonePay = vodafonePay;
        this.deffered = deffered;
        this.username = username;
        this.invoice_date = invoice_date;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public double getTotal_quantity() {
        return total_quantity;
    }

    public double getTotalReturns() {
        return total_returns;
    }

    public double getTotal_price_before_discount() {
        return total_price_before_discount;
    }

    public double getDiscount_percentage() {
        return discount_percentage;
    }

    public double getTotal_price_after_discount() {
        return total_price_after_discount;
    }

    public double getCashPay() {
        return cashPay;
    }

    public double getInstaPay() {
        return instaPay;
    }

    public double getVodafonePay() {
        return vodafonePay;
    }

    public double getDeffered() {
        return deffered;
    }

    public String getUsername() {
        return username;
    }

    public String getInvoice_date() {
        return invoice_date;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }
}
