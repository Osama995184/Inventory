/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventory;

/**
 *
 * @author Osama
 */
public class UserRoles {

    // Main Pages
    public boolean mainPage;
    public boolean salesPage;
    public boolean purchesPage;
    public boolean wareHousesPage;
    public boolean expensesPage;
    public boolean monyPage;

    // Sales
    public boolean salesMain;
    public boolean salesInvoice;
    public boolean salesReturn;
    public boolean deferredSales;
    public boolean addClient;
    public boolean clientDetailedReport;
    public boolean clientSummaryReport;

    // Purchases
    public boolean addSupplier;
    public boolean purchaseInvoice;
    public boolean items;
    public boolean purchaseReturn;
    public boolean missingItems;
    public boolean supplierDetailedReport;
    public boolean supplierSummaryReport;

    // Warehouses
    public boolean currentStock;
    public boolean transferStock;
    public boolean itemMovement;
    public boolean stockSettlement;

    // Expenses & Money
    public boolean expenses;
    public boolean receipts;
}
