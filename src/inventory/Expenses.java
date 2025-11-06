package inventory;

public class Expenses {

    private int expense_id;
    private double amount;
    private double cash;
    private double vodafone;
    private double instaPay;
    private String reason;
    private String em_username;
    private String withdrawn_by;
    private String date;
    private String payStatues;

    public Expenses(int expense_id, double amount, double cash, double vodafone, double instaPay, String reason, String em_username, String withdrawn_by, String date, String payStatues) {
        this.expense_id = expense_id;
        this.amount = amount;
        this.cash = cash;
        this.vodafone = vodafone;
        this.instaPay = instaPay;
        this.reason = reason;
        this.em_username = em_username;
        this.withdrawn_by = withdrawn_by;
        this.date = date;
        this.payStatues = payStatues;

    }

    public Expenses(double cash, double vodafone, double instaPay, String reason, String em_username, String withdrawn_by, String payStatues) {
        this(0, 0, cash, vodafone, instaPay, reason, em_username, withdrawn_by, null, payStatues);
    }

    public int getExpense_id() {
        return expense_id;
    }

    public double getAmount() {
        return amount;
    }

    public double getCash() {
        return cash;
    }

    public double getVodafone() {
        return vodafone;
    }

    public double getInstaPay() {
        return instaPay;
    }

    public String getReason() {
        return reason;
    }

    public String getEm_username() {
        return em_username;
    }

    public String getWithdrawn_by() {
        return withdrawn_by;
    }

    public String getDate() {
        return date;
    }

    public String getPayStatues() {
        return payStatues;
    }
}
