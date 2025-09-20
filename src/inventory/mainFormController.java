package inventory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import services.alert;
import services.customer;
import services.functions;
import services.printReportsValues;
import services.purchaseInvoiceDetail;
import services.purchesServices;
import services.recordItemMovement;
import services.supplierServices;
import services.voucherServices;
import services.wareHouse;

public class mainFormController implements Initializable {

    @FXML
    private AnchorPane main_form;
    @FXML
    private AnchorPane addProductForm;
    @FXML
    private AnchorPane addPurchaseForm;

    @FXML
    private Button mainPage_btn;

    @FXML
    private Button sales_btn;

    @FXML
    private Button purches_btn;

    @FXML
    private Button capital_btn;

    @FXML
    private Button expenses_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private Button settings_btn;

    @FXML
    private Button warehouses_Btn;

    @FXML
    private Label username;

    @FXML
    private AnchorPane settings_form;

    @FXML
    private AnchorPane wareHouseForm;

    @FXML
    private AnchorPane viewreturnPurchaseInvoicesForm1;
    @FXML
    private AnchorPane addreturnPurchaseInvoicesForm1;

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, String> col_employeePassword;

    @FXML
    private TableColumn<Employee, String> col_employeeUsername;

    @FXML
    private TableColumn<Employee, String> col_emplyeeId;

    @FXML
    private TableView<customer> addClientsTable;

    @FXML
    private TableColumn<customer, String> addClients_col_remarks_sales;

    @FXML
    private TableColumn<customer, String> addClients_col_clientAddress_sales;

    @FXML
    private TableColumn<customer, String> addClients_col_clientPhone_sales;

    @FXML
    private TableColumn<customer, String> addClients_col_clientName_sales;

    @FXML
    private TableColumn<customer, String> addClients_col_clientID_sales;

    @FXML
    private TextField search_clientSales;

    @FXML
    private TextField clientName_sales;

    @FXML
    private TextField clientPhone_sales;

    @FXML
    private TextField clientAddress_sales;

    @FXML
    private TextField remark_sales;

    @FXML
    private TextField emplyeeName;

    @FXML
    private TextField employeePassword;

    @FXML
    private TextField searchEmployee;

///////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private TextField departmentTextField;

    @FXML
    private TextField return_reciptID;

    @FXML
    private TableView<Departments> departmentTable;

    @FXML
    private TableColumn<Departments, String> col_departmentId;

    @FXML
    private TableColumn<Departments, String> col_dedepartment;

    @FXML
    private CheckBox mainPage_checkBox;
    @FXML
    private CheckBox wareHouses_checkBox;
    @FXML
    private CheckBox purches_checkBox;
    @FXML
    private CheckBox salesPage_checkBox;
    @FXML
    private CheckBox expensesPage_checkBox;
    @FXML
    private CheckBox monyPage_checkBox;
///////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private AnchorPane addProduct_form;

    @FXML
    private TableView<productData> inventoryTableView;

    @FXML
    private TableColumn<productData, String> inventory_col_realPrice;

    @FXML
    private TableColumn<productData, String> inventory_col_wholesalePrice;

    @FXML
    private TableColumn<productData, String> inventory_col_mNo;

    @FXML
    private TableColumn<productData, String> inventory_col_model;

    @FXML
    private TableColumn<productData, String> inventory_col_id;

    @FXML
    private TableColumn<productData, String> inventory_col_soldNo;

    @FXML
    private TableColumn<productData, String> inventory_col_type;
    @FXML
    private TableColumn<productData, String> inventory_col_color;
    @FXML
    private TableColumn<productData, String> inventory_col_size;
    @FXML
    private TableColumn<productData, String> inventory_col_warehouse;
    @FXML
    private TableColumn<productData, String> inventory_col_unit;

    @FXML
    private TextField purchaseDetails_productID;

    @FXML
    private TextField purchaseDetails_model;

    @FXML
    private ComboBox<String> returnMethodList;

    @FXML
    private TextField purchaseDetails_Quantity;

    @FXML
    private TextField purchaseDetails_wholesalePrice;

    @FXML
    private TextField purchaseDetails_realPrice;

    @FXML
    private TextField inventory_Search;

    @FXML
    private TextField purchaseDetails_Search;

///////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private AnchorPane viewreturnPurchaseInvoicesForm;
    @FXML
    private AnchorPane addreturnPurchaseInvoicesForm;
    @FXML
    private ComboBox<String> returnMethodList_SuppliersReturn;
    @FXML
    private TextField inventory_Mno_SuppliersReturn;
    @FXML
    private TextField inventory_model_SuppliersReturn;
    @FXML
    private TextField inventory_color_suppliersReturn;
    @FXML
    private TextField inventory_size_SuppliersReturn;
    @FXML
    private TextField inventory_productID_suppliersReturn;
    @FXML
    private TextField inventory_invoicesID_suppliersReturn;
    @FXML
    private TextField search_purchaseInvoices_SuppliersReturn;
    @FXML
    private Label inventoryLable_quantity_SuppliersReturn;
    @FXML
    private TableView<purchaseInvoiceDetail> inventoryTableView_SuppliersReturn;
    @FXML
    private TableColumn<purchaseInvoiceDetail, String> inventory_col_wholesalePrice_SuppliersReturn;
    @FXML
    private TableColumn<purchaseInvoiceDetail, String> inventory_col_quantity_SuppliersReturn;
    @FXML
    private TableColumn<purchaseInvoiceDetail, String> inventory_col_type_SuppliersReturn;
    @FXML
    private TableColumn<purchaseInvoiceDetail, String> inventory_col_model_SuppliersReturn;
    @FXML
    private TableColumn<purchaseInvoiceDetail, String> inventory_col_id_SuppliersReturn;
    @FXML
    private TableColumn<purchaseInvoiceDetail, String> inventory_col_inovicesId_SuppliersReturn;
    @FXML
    private TableColumn<purchaseInvoiceDetail, String> inventory_col_warehouse_SuppliersReturn;
    @FXML
    private TableColumn<purchaseInvoiceDetail, String> inventory_col_supplier_SuppliersReturn;
    @FXML
    private TableColumn<purchaseInvoiceDetail, String> inventory_col_unit_SuppliersReturn;
    @FXML
    private TableColumn<purchaseInvoiceDetail, String> inventory_col_size_SuppliersReturn;
    @FXML
    private TableColumn<purchaseInvoiceDetail, String> inventory_col_color_SuppliersReturn;
///////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private TextField search_purchaseItems;
    @FXML
    private TableView<ReturnedItemsToSuppliers> itemsReturnTableView;
    @FXML
    private TableColumn<ReturnedItemsToSuppliers, String> item_col_wholesalePrice;
    @FXML
    private TableColumn<ReturnedItemsToSuppliers, String> item_col_mNoReturned;
    @FXML
    private TableColumn<ReturnedItemsToSuppliers, String> item_col_mNo;
    @FXML
    private TableColumn<ReturnedItemsToSuppliers, String> item_col_type;
    @FXML
    private TableColumn<ReturnedItemsToSuppliers, String> item_col_model;
    @FXML
    private TableColumn<ReturnedItemsToSuppliers, String> item_col_supplier;
    @FXML
    private TableColumn<ReturnedItemsToSuppliers, String> item_col_itemId;
    @FXML
    private TableColumn<ReturnedItemsToSuppliers, String> item_col_invoicesId;
    @FXML
    private TableColumn<ReturnedItemsToSuppliers, String> item_col_returnId;
    @FXML
    private TableColumn<ReturnedItemsToSuppliers, String> item_col_warehouse;
    @FXML
    private TableColumn<ReturnedItemsToSuppliers, String> item_col_date;
    @FXML
    private TableColumn<ReturnedItemsToSuppliers, String> item_col_unit;
    @FXML
    private TableColumn<ReturnedItemsToSuppliers, String> item_col_size;
    @FXML
    private TableColumn<ReturnedItemsToSuppliers, String> item_col_color;

///////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private TableView<AccountStatement> accountStatementSuppliersTable;
    @FXML
    private TableColumn<AccountStatement, String> accountStatement_col_suppliers_purchase;
    @FXML
    private TableColumn<AccountStatement, String> accountStatement_col_phone_purchase;
    @FXML
    private TableColumn<AccountStatement, String> accountStatement_col_cash_purchase;
    @FXML
    private TableColumn<AccountStatement, String> accountStatement_col_deferred_purchase;
    @FXML
    private TableColumn<AccountStatement, String> accountStatement_col_totalPrice_purchase;
    @FXML
    private TableColumn<AccountStatement, String> accountStatement_col_remarks_purchase;
    @FXML
    private TableColumn<AccountStatement, String> accountStatement_col_date_purchase;
    @FXML
    private TableColumn<AccountStatement, String> accountStatement_col_invoicesID_purchase;
    @FXML
    private TableColumn<AccountStatement, String> accountStatement_col_return_purchase;
    @FXML
    private TableColumn<AccountStatement, Integer> accountStatement_col_index;
    @FXML
    private DatePicker fromDatePickerAccountStatement;
    @FXML
    private DatePicker toDatePickerAccountStatement;
    @FXML
    private ComboBox<String> SuppliersListAccountStatements;
    @FXML
    private TextField search_accountStatements;
    @FXML
    private TextField totalPurchase_accountStatement;
    @FXML
    private TextField totalRuturns_accountStatement;
    @FXML
    private TextField totalDeferred_accountStatement;
    @FXML
    private TextField totalCash_accountStatement;
///////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private TableView<InvoiceItem> receipt_tableView;

    @FXML
    private TableColumn<InvoiceItem, String> receipt_col_model;

    @FXML
    private TableColumn<InvoiceItem, String> receipt_col_No;

    @FXML
    private TableColumn<InvoiceItem, String> receipt_col_price;

    @FXML
    private TextField disqaunt_textField;
///////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private TextField supplierName_purchase;
    @FXML
    private TextField phone_purchase;
    @FXML
    private TextField supplierAddress_purchase;
    @FXML
    private TextField remark_purchase;
    @FXML
    private TableView<Supplier> suppliersTable;
    @FXML
    private TableColumn<Supplier, Integer> suppliers_col_suppliersID_purchase;
    @FXML
    private TableColumn<Supplier, String> suppliers_col_suppliersName_purchase;
    @FXML
    private TableColumn<Supplier, String> suppliers_col_suppliersPhone_purchase;
    @FXML
    private TableColumn<Supplier, String> suppliers_col_suppliersAddress_purchase;
    @FXML
    private TableColumn<Supplier, String> suppliers_col_remarks_purchase;
    @FXML
    private TextField search_purchase;

///////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private TextField search_purchaseInvoices;
    @FXML
    private TextField InvoicesOutID;
    @FXML
    private TextField remark_invoices;
    @FXML
    private TextField totalPrice_purchase;
    @FXML
    private TextField cash_purchase;
    @FXML
    private TextField deferredInvoices_purchase;

    @FXML
    private TableView<InvoiceModel> invoicesSuppliersTable;
    @FXML
    private TableColumn<InvoiceModel, String> invoices_col_date_purchase;
    @FXML
    private TableColumn<InvoiceModel, String> invoices_col_remarks_purchase;
    @FXML
    private TableColumn<InvoiceModel, String> invoices_col_totalPrice_purchase;
    @FXML
    private TableColumn<InvoiceModel, String> invoices_col_deferred_purchase;
    @FXML
    private TableColumn<InvoiceModel, String> invoices_col_cash_purchase;
    @FXML
    private TableColumn<InvoiceModel, String> invoices_col_suppliers_purchase;
    @FXML
    private TableColumn<InvoiceModel, String> invoices_col_invoicesOutID_purchase;
    @FXML
    private TableColumn<InvoiceModel, String> invoices_col_invoicesID_purchase;
    @FXML
    private ComboBox<String> SuppliersListInvoices;
    @FXML
    private DatePicker fromDatePickerInvoices;
    @FXML
    private DatePicker toDatePickerInvoices;
    @FXML
    private DatePicker fromDatePickerInvoicesInsert;

///////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private AnchorPane menu_form;

    @FXML
    private TextField menu_cashPay;

    @FXML
    private TextField menu_VodafonePay;

    @FXML
    private TextField menu_instaPay;

    @FXML
    private TableView<productData> menuTableView;

    @FXML
    private TableColumn<productData, String> menu_col_realPrice;

    @FXML
    private TableColumn<productData, Double> menu_col_mNo;

    @FXML
    private TableColumn<productData, String> menu_col_type;

    @FXML
    private TableColumn<productData, String> menu_col_size;

    @FXML
    private TableColumn<productData, String> menu_col_color;

    @FXML
    private TableColumn<productData, String> menu_col_unit;

    @FXML
    private TableColumn<productData, String> menu_col_model;

    @FXML
    private DatePicker ReturnItemsFromDatePicker;

    @FXML
    private DatePicker ReturnItemsToDatePicker;

    @FXML
    private TableView<Returns> returnProductTableView;

    @FXML
    private TableColumn<?, ?> menu_col_id;

    @FXML
    private TextField menu_model;

    @FXML
    private TextField menu_deferred;

    @FXML
    private TextField menu_realPrice;

    @FXML
    private TextField menu_productColor;

    @FXML
    private TextField menu_productSize;

    @FXML
    private Label unit_label;

    @FXML
    private Label unit_returnForm;

    @FXML
    private TextField menu_Search;

    @FXML
    private TextField menu_spinner_no;

    @FXML
    private ComboBox<String> menu_clientName;
    // كشف حساب عميل مفصل
    @FXML
    private ComboBox<String> clientsListAccountStatementsDetails;
    @FXML
    private TextField totalPurchase_accountStatementDetails;
    @FXML
    private TextField clientDetailSearch;
    @FXML
    private DatePicker fromDatePickerClientDetails;
    @FXML
    private DatePicker toDatePickerClient1Details;
    @FXML
    private TableView<productData> clientSalesDetailsTable1;
    @FXML
    private TableColumn<productData, String> clientSalesDetails_col_date;
    @FXML
    private TableColumn<productData, Double> clientSalesDetails_col_totalPrice;
    @FXML
    private TableColumn<productData, Double> clientSalesDetails_col_quantity;
    @FXML
    private TableColumn<productData, String> clientSalesDetails_col_type_sales;
    @FXML
    private TableColumn<productData, String> clientSalesDetails_col_warehouse_sales;
    @FXML
    private TableColumn<productData, String> clientSalesDetails_col_model_sales;
    @FXML
    private TableColumn<productData, String> clientSalesDetails_col_unit;
    @FXML
    private TableColumn<productData, String> clientSalesDetails_col_size;
    @FXML
    private TableColumn<productData, String> clientSalesDetails_col_color;
    @FXML
    private TableColumn<productData, Integer> clientSalesDetails_col_modelId;
    @FXML
    private TableColumn<productData, Integer> clientSalesDetails_col_invoiceId;
    // كشف حساب عميل اجمالى
    @FXML
    private ComboBox<String> clientsListAccountStatements;
    @FXML
    private TextField totalPurchase_accountStatementClients;
    @FXML
    private TextField totalRuturns_accountStatementClients;
    @FXML
    private TextField totalDeferred_accountStatementClients;
    @FXML
    private TextField totalCash_accountStatementClients;
    @FXML
    private TextField clientSearch;
    @FXML
    private DatePicker fromDatePickerClient;
    @FXML
    private DatePicker toDatePickerClient;
    @FXML
    private TableView<Invoices> accountStatementClientsTable;
    @FXML
    private TableColumn<Invoices, String> accountStatementClients_col_date;
    @FXML
    private TableColumn<Invoices, Double> accountStatementClients_col_return;
    @FXML
    private TableColumn<Invoices, Double> accountStatementClients_col_totalPrice;
    @FXML
    private TableColumn<Invoices, Double> accountStatementClients_col_totalAfterDiscount;
    @FXML
    private TableColumn<Invoices, Double> accountStatementClients_col_deferred;
    @FXML
    private TableColumn<Invoices, String> accountStatementClients_col_clientPhone;
    @FXML
    private TableColumn<Invoices, String> accountStatementClients_col_clientName;
    @FXML
    private TableColumn<Invoices, Integer> accountStatementClients_col_invoicesID;
    ///////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private ComboBox<String> warehouseList_menuForm;

    @FXML
    private TextField menu_clientPhone;

    @FXML
    private TextField color_purchaseDetails;

    @FXML
    private ComboBox<String> itemTypeList_menuForm;

    @FXML
    private ComboBox<String> itemTypeList_salesForm;

    @FXML
    private ComboBox<String> QuantityList_purchaseDetails;

    @FXML
    private ComboBox<String> sizeList_purchaseDetails;

    @FXML
    private ComboBox<String> itemTypeList_purchaseDetails;

    @FXML
    private ComboBox<String> itemTypeListSearch_addForm;

    @FXML
    private ComboBox<String> itemWareHouseListSearch_addForm;

    @FXML
    private ComboBox<String> warehouseList_purchaseDetails;

    @FXML
    public Label menu_total;

    @FXML
    private TableView<purchaseInvoiceDetail> purchaseDetailsTableView;
    @FXML
    private TableColumn<purchaseInvoiceDetail, Double> purchaseDetails_col_realPrice;
    @FXML
    private TableColumn<purchaseInvoiceDetail, Double> purchaseDetails_col_wholesalePrice;
    @FXML
    private TableColumn<purchaseInvoiceDetail, String> purchaseDetails_col_unit;
    @FXML
    private TableColumn<purchaseInvoiceDetail, Double> purchaseDetails_col_quantity;
    @FXML
    private TableColumn<purchaseInvoiceDetail, String> purchaseDetails_col_warehouse;
    @FXML
    private TableColumn<purchaseInvoiceDetail, String> purchaseDetails_col_type;
    @FXML
    private TableColumn<purchaseInvoiceDetail, String> purchaseDetails_col_size;
    @FXML
    private TableColumn<purchaseInvoiceDetail, String> purchaseDetails_col_color;
    @FXML
    private TableColumn<purchaseInvoiceDetail, String> purchaseDetails_col_model;
    @FXML
    private TableColumn<purchaseInvoiceDetail, Integer> purchaseDetails_col_invoicesId;
///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @FXML
    private TableView<productData> returnTableView;

    @FXML
    private TableColumn<productData, Double> return_col_realPrice;

    @FXML
    private TableColumn<productData, String> return_col_invoiceId;

    @FXML
    private TableColumn<productData, String> return_col_date;

    @FXML
    private TableColumn<productData, String> return_col_wareHouse;

    @FXML
    private TableColumn<productData, String> return_col_type;

    @FXML
    private TableColumn<productData, String> return_col_unit;

    @FXML
    private TableColumn<productData, String> return_col_size;

    @FXML
    private TableColumn<productData, String> return_col_color;

    @FXML
    private TableColumn<productData, String> return_col_mNo;

    @FXML
    private TableColumn<productData, String> return_col_model;

    @FXML
    private TableColumn<?, ?> return_col_id;

    @FXML
    private TextField return_spinner_no;

    @FXML
    private TextField return_productID;

    @FXML
    private TextField return_model;

    @FXML
    private TextField return_realPrice;

    @FXML
    private TextField retrun_Search_field;

    @FXML
    private TextField return_reson;
///////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private TableView<productData> missingProductTableView;
    @FXML
    private TableColumn<productData, String> missing_col_realPrice;
    @FXML
    private TableColumn<productData, String> missing_col_wholesalePrice;
    @FXML
    private TableColumn<productData, String> missing_col_avaliableQuantity;
    @FXML
    private TableColumn<productData, String> missing_col_model;
    @FXML
    private TableColumn<productData, String> missing_col_id;
    @FXML
    private TableColumn<productData, String> missing_col_soldQuantity;
    @FXML
    private TableColumn<productData, String> missing_col_warehouse;
    @FXML
    private TableColumn<productData, String> missing_col_type;
    @FXML
    private TableColumn<productData, String> missing_col_color;
    @FXML
    private TableColumn<productData, String> missing_col_size;
    @FXML
    private TableColumn<productData, String> missing_col_unit;
    @FXML
    private TextField missingProduct_Search;
///////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private TableView<ReceiptVoucher> receiptVoucher_table;
    @FXML
    private TableColumn<ReceiptVoucher, Integer> receiptVoucher_col_id;
    @FXML
    private TableColumn<ReceiptVoucher, String> receiptVoucher_col_date;
    @FXML
    private TableColumn<ReceiptVoucher, Double> receiptVoucher_col_cash;
    @FXML
    private TableColumn<ReceiptVoucher, Double> receiptVoucher_col_vodafone;
    @FXML
    private TableColumn<ReceiptVoucher, Double> receiptVoucher_col_instaPay;
    @FXML
    private TableColumn<ReceiptVoucher, Double> receiptVoucher_col_amount;
    @FXML
    private TableColumn<ReceiptVoucher, String> receiptVoucher_col_payerName;
    @FXML
    private TableColumn<ReceiptVoucher, String> receiptVoucher_col_notes;
    @FXML
    private TextField receiptVoucher_notes_field;
    @FXML
    private TextField receiptVoucher_cash_field;
    @FXML
    private TextField receiptVoucher_vodafone_field;
    @FXML
    private TextField receiptVoucher_instaPay_field;
    @FXML
    private DatePicker toDatePickerReceiptVoucher;
    @FXML
    private DatePicker fromDatePickerReceiptVoucher;
    @FXML
    private TextField searchReceiptVoucher;
    @FXML
    private TextField payer_field;

///////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private TableColumn<Returns, String> returnProduct_col_id;

    @FXML
    private TableColumn<Returns, String> retrunProduct_col_modelID;

    @FXML
    private TableColumn<Returns, String> retrunProduct_col_invoiceID;

    @FXML
    private TableColumn<Returns, String> retrunProduct_col_quantity;

    @FXML
    private TableColumn<Returns, String> retrunProduct_col_warehouse;

    @FXML
    private TableColumn<Returns, String> retrunProduct_col_reson;

    @FXML
    private TableColumn<Returns, String> retrunProduct_col_money;

    @FXML
    private TableColumn<Returns, String> retrunProduct_col_date;
    @FXML
    private TableColumn<Returns, String> retrunProduct_col_modelSize;
    @FXML
    private TableColumn<Returns, String> retrunProduct_col_modelColor;
    @FXML
    private TableColumn<Returns, String> retrunProduct_col_modelUnit;
    @FXML
    private TableColumn<Returns, String> retrunProduct_col_modelName;

    @FXML
    private TextField returnProduct_Search;
    //////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private TableView<Invoices> invoiceTable;
    @FXML
    private TableColumn<Invoices, Integer> col_invoiceID;
    @FXML
    private TableColumn<Invoices, Integer> col_totalQty;
    @FXML
    private TableColumn<Invoices, Double> col_totalBeforeDiscount;
    @FXML
    private TableColumn<Invoices, Double> col_discount;
    @FXML
    private TableColumn<Invoices, Double> col_totalAfterDiscount;
    @FXML
    private TableColumn<Invoices, Double> col_cash;
    @FXML
    private TableColumn<Invoices, Double> col_insta;
    @FXML
    private TableColumn<Invoices, Double> col_voda;
    @FXML
    private TableColumn<Invoices, Double> col_voda1;
    @FXML
    private TableColumn<Invoices, String> col_username;
    @FXML
    private TableColumn<Invoices, String> col_date;
    @FXML
    private TableColumn<Invoices, String> col_clientPhone_salesForm;
    @FXML
    private TableColumn<Invoices, String> col_clientName_salesForm;

    @FXML
    private TextField invoice_search;

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;
    //////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private TableView<Sales> salesTable;

    @FXML
    private TableColumn<Sales, Integer> col_invoiceId;
    @FXML
    private TableColumn<Sales, Integer> col_modelId;
    @FXML
    private TableColumn<Sales, Integer> col_quantity;
    @FXML
    private TableColumn<Sales, Double> col_totalPrice;
    @FXML
    private TableColumn<Sales, String> Sales_col_date;
    @FXML
    private TableColumn<Sales, String> col_emUsername;
    @FXML
    private TableColumn<Sales, String> col_model_sales;
    @FXML
    private TableColumn<Sales, String> col_type_sales;
    @FXML
    private TableColumn<Sales, String> col_type_wareHouse;
    @FXML
    private TableColumn<Sales, String> col_size_sales;
    @FXML
    private TableColumn<Sales, String> col_color_sales;
    @FXML
    private TableColumn<Sales, String> col_unit_sales;
    @FXML
    private TextField salesModelID_search;
    //////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private AnchorPane expenses_form;
    @FXML
    private TableView<Expenses> expensesTable;
    @FXML
    private TableColumn<Expenses, Integer> col_expenseId;
    @FXML
    private TableColumn<Expenses, Double> col_amount;
    @FXML
    private TableColumn<Expenses, Double> col_cash_expenses;
    @FXML
    private TableColumn<Expenses, Double> col_vodafone;
    @FXML
    private TableColumn<Expenses, Double> col_instaPay;
    @FXML
    private TableColumn<Expenses, String> col_reason;
    @FXML
    private TableColumn<Expenses, String> col_emUsername_expenses;
    @FXML
    private TableColumn<Expenses, String> col_withdrawnBy;
    @FXML
    private TableColumn<Expenses, String> col_date_expenses;
    @FXML
    private TableColumn<Expenses, String> col_payStatues_expenses;
    @FXML
    private TextField withdrawnBy_field;
    @FXML
    private TextField reason_field;
    @FXML
    private TextField cash_field;
    @FXML
    private TextField vodafone_field;
    @FXML
    private TextField instaPay_field;
    @FXML
    private TextField searchByName;
    @FXML
    private TextField totalOutgoing_expenses;
    @FXML
    private TextField totalExpenses_expenses;
    @FXML
    private ComboBox<String> statues_list_expenses;
    @FXML
    private DatePicker fromDatePickerEx;
    @FXML
    private DatePicker toDatePickerEx;

    //////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private TextField deferredInvoice_search;
    @FXML
    private TextField deferred_cashPay;
    @FXML
    private TextField deferred_VodafonePay;
    @FXML
    private TextField deferred_instaPay;
    @FXML
    private TextField deferredInvoicesId;
    @FXML
    private TextField deferredClientName;
    @FXML
    private TextField totalDeferred;
    @FXML
    private DatePicker toDatePicker_deferredForm;
    @FXML
    private DatePicker fromDatePicker_deferredForm;
    @FXML
    private TableView<Invoices> deferredInvoiceTable;
    @FXML
    private TableColumn<Invoices, Integer> deferred_col_invoiceID;
    @FXML
    private TableColumn<Invoices, Double> deferred_col_deferred;
    @FXML
    private TableColumn<Invoices, Double> deferred_col_totalBeforeDiscount;
    @FXML
    private TableColumn<Invoices, Double> deferred_col_discount;
    @FXML
    private TableColumn<Invoices, Double> deferred_col_totalAfterDiscount;
    @FXML
    private TableColumn<Invoices, Double> deferred_col_voda;
    @FXML
    private TableColumn<Invoices, Double> deferred_col_instapay;
    @FXML
    private TableColumn<Invoices, Double> deferred_col_cash;
    @FXML
    private TableColumn<Invoices, Double> deferred_col_totalQty;
    @FXML
    private TableColumn<Invoices, String> deferred_col_clientPhone;
    @FXML
    private TableColumn<Invoices, String> deferred_col_clientName;
    @FXML
    private TableColumn<Invoices, String> deferred_col_username;
    @FXML
    private TableColumn<Invoices, String> deferred_col_date;
    //////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private AnchorPane capital_form;
    @FXML
    private TextField total_sales_wholesale_price;
    @FXML
    private TextField total_sales_Real_price;
    @FXML
    private TextField profit_without_deducting_expenses;
    @FXML
    private TextField tota_expenses_field;
    @FXML
    private TextField total_vodafoneCash_field;
    @FXML
    private TextField total_Cash_field;
    @FXML
    private TextField total_instaPay_field;
    @FXML
    private TextField NetProfit_field;
    @FXML
    private TextField totalOutgoing_capital;
    @FXML
    private TextField NetProfit_field_withOutgoing;
    @FXML
    private TextField totalAvailableWholeprice_field;
    @FXML
    private TextField totalAvailableRealprice_field;
    @FXML
    private Label Money_safe;
    @FXML
    private Label UnavailbleProductCount_label;
    @FXML
    private Label AvailbleProductCount_label;
    @FXML
    private DatePicker fromDatePickerCapital;
    @FXML
    private DatePicker toDatePickerCapital;
    @FXML
    private Label totalPriceCurrentMonth_field;
    //////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private Label Safe_main;
    @FXML
    private Label today_salaries;
    @FXML
    private Label unavailable_main;
    @FXML
    private Label available_main;
    @FXML
    private AreaChart<String, Number> dailySalesChart;

    //////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private TableView<ProfitReport> profitReportTable;
    @FXML
    private TableColumn<ProfitReport, Double> gain_col;
    @FXML
    private TableColumn<ProfitReport, Double> price_col;
    @FXML
    private TableColumn<ProfitReport, Double> wholesales_col;
    @FXML
    private TableColumn<ProfitReport, Integer> quantity_col;
    @FXML
    private TableColumn<ProfitReport, String> type_col;

    //////////////////////////////////////////////////////////////////////////////////////////////
    private final String[] RMethod = {"اجل", "نقدي", "فودافون كاش", "انستا باي"};
    private final String[] RMethodUnit = {"جرام", "كيلو", "طن", "قطعة", "كرتونة"};
    private final String[] RMethodSize = {"M", "L", "XL", "XXL", "XXXL", "XXXXL"};
    private final String[] RMethod_SuppliersReturn = {"نقدي", "اجل"};
    private final String[] EStatues = {"المصروفات", "الخوارج"};
    private String wareHouses;
    private ObservableList<ReceiptVoucher> voucherList = FXCollections.observableArrayList();

    List<customer> customersL = new ArrayList<>();
    List<wareHouse> warehouseL = new ArrayList<>();
    alert al = new alert();
    purchesServices services = new purchesServices();
    supplierServices subServices = new supplierServices();
    warehouses WH = new warehouses();
    functions func = new functions();
    printReportsValues totalPrint = new printReportsValues();
    voucherServices vouServices = new voucherServices(this);

    public mainFormController() {
    }

    public boolean checkTextField() {
        return (warehouseList_purchaseDetails.getItems().isEmpty())
                || (QuantityList_purchaseDetails.getItems().isEmpty())
                || (sizeList_purchaseDetails.getItems().isEmpty())
                || (itemTypeList_purchaseDetails.getItems().isEmpty())
                || (purchaseDetails_model.getText().isEmpty())
                || (color_purchaseDetails.getText().isEmpty())
                || (func.isntNumeric(purchaseDetails_Quantity.getText()))
                || (func.isntNumeric(purchaseDetails_wholesalePrice.getText()))
                || (func.isntNumeric(purchaseDetails_realPrice.getText()));
    }

    public void inventoryAddbtn() {
        purchaseInvoiceDetail prodDataDetails = new purchaseInvoiceDetail();

        if (selectedPurchaseInvoiceHead == null) {
            al.E_Alert("يجب تحديد الفاتورة اولا ", AlertType.ERROR);
            return;
        } else if (checkTextField()) {
            al.E_Alert("ادخل كل البيانات بشكل صحيح ", AlertType.ERROR);
            return;
        }

        prodDataDetails.setPurchaseInvoiceId(selectedPurchaseInvoiceHead.getId());
        prodDataDetails.setSize(sizeList_purchaseDetails.getSelectionModel().getSelectedItem());
        prodDataDetails.setColor(color_purchaseDetails.getText());
        prodDataDetails.setWarehouse(warehouseList_purchaseDetails.getSelectionModel().getSelectedItem());
        prodDataDetails.setUnit(QuantityList_purchaseDetails.getSelectionModel().getSelectedItem());
        prodDataDetails.setModel(itemTypeList_purchaseDetails.getSelectionModel().getSelectedItem());
        prodDataDetails.setItemCode(purchaseDetails_productID.getText());
        prodDataDetails.setItemName(purchaseDetails_model.getText());
        prodDataDetails.setQuantity(Double.parseDouble(purchaseDetails_Quantity.getText()));
        prodDataDetails.setPurchasePrice(Double.parseDouble(purchaseDetails_wholesalePrice.getText()));
        prodDataDetails.setSellingPrice(Double.parseDouble(purchaseDetails_realPrice.getText()));
        al.Confirmation_AlertOptions("هل انت متأكد من هذه البيانات ؟");
        Optional<ButtonType> option = al.getAlert().showAndWait();

        if (option.isPresent() && option.get().equals(ButtonType.OK)) {
            subServices.addPurchaseInvoiceDetail(prodDataDetails);
            inventoryClearBtn();
        } else {
            al.E_Alert("تم االغاء التعديل", AlertType.ERROR);
        }
    }

    public void inventoryUpdateBtn() {
        purchaseInvoiceDetail prodDataDetails = new purchaseInvoiceDetail();
        purchaseInvoiceDetail prodDataDetails2 = purchaseDetailsTableView.getSelectionModel().getSelectedItem();

        if (prodDataDetails == null) {
            al.E_Alert("يجب تحديد التفاصيل اولا ", AlertType.ERROR);
            return;
        } else if (checkTextField()) {
            al.E_Alert("ادخل كل البيانات بشكل صحيح ", AlertType.ERROR);
            return;
        }

        prodDataDetails.setPurchaseInvoiceId(selectedPurchaseInvoiceHead.getId());
        prodDataDetails.setSize(sizeList_purchaseDetails.getSelectionModel().getSelectedItem());
        prodDataDetails.setColor(color_purchaseDetails.getText());
        prodDataDetails.setSupplierName(prodDataDetails2.getSupplierName());
        prodDataDetails.setId(prodDataDetails2.getId());
        prodDataDetails.setWarehouse(warehouseList_purchaseDetails.getSelectionModel().getSelectedItem());
        prodDataDetails.setUnit(QuantityList_purchaseDetails.getSelectionModel().getSelectedItem());
        prodDataDetails.setModel(itemTypeList_purchaseDetails.getSelectionModel().getSelectedItem());
        prodDataDetails.setItemCode(purchaseDetails_productID.getText());
        prodDataDetails.setItemName(purchaseDetails_model.getText());
        prodDataDetails.setQuantity(Double.parseDouble(purchaseDetails_Quantity.getText()));
        prodDataDetails.setPurchasePrice(Double.parseDouble(purchaseDetails_wholesalePrice.getText()));
        prodDataDetails.setSellingPrice(Double.parseDouble(purchaseDetails_realPrice.getText()));

        al.Confirmation_AlertOptions("هل انت متأكد من هذه التعديلات ؟");
        Optional<ButtonType> option = al.getAlert().showAndWait();

        if (option.isPresent() && option.get().equals(ButtonType.OK)) {
            subServices.updatePurchaseInvoiceDetail(prodDataDetails);
            inventoryClearBtn();
        } else {
            al.E_Alert("تم االغاء التعديل", AlertType.ERROR);
        }
    }

    public void inventoryDeleteBtn() {
        purchaseInvoiceDetail prodDataDetails = purchaseDetailsTableView.getSelectionModel().getSelectedItem();
        if (prodDataDetails == null) {
            al.E_Alert("يجب تحديد التفاصيل اولا ", AlertType.ERROR);
            return;
        }
        al.Confirmation_AlertOptions("هل انت متاكد من حذف المنتج ؟");
        Optional<ButtonType> option = al.getAlert().showAndWait();

        if (option.isPresent() && option.get().equals(ButtonType.OK)) {
            subServices.deletePurchaseInvoiceDetail(prodDataDetails, true);
            inventoryClearBtn();
        } else {
            al.E_Alert("تم االغاء الحذف", AlertType.ERROR);
        }
    }

    public void inventoryClearBtn() {
        warehouseList_purchaseDetails.getSelectionModel().clearSelection();
        QuantityList_purchaseDetails.getSelectionModel().clearSelection();
        sizeList_purchaseDetails.getSelectionModel().clearSelection();
        itemTypeList_purchaseDetails.getSelectionModel().clearSelection();
        purchaseDetails_productID.clear();
        purchaseDetails_model.clear();
        color_purchaseDetails.clear();
        purchaseDetails_Quantity.clear();
        purchaseDetails_wholesalePrice.clear();
        purchaseDetails_realPrice.clear();
        getPurchaseInvoiceDetailList();
    }

    // MERGE ALL DATAS
    // TO SHOW DATA ON OUR TABLE
    private ObservableList<productData> inventoryListData;

    public void inventoryShowData() {
        inventoryListData = subServices.inventoryDataList(itemTypeListSearch_addForm, itemWareHouseListSearch_addForm);
        inventory_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        inventory_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        inventory_col_mNo.setCellValueFactory(new PropertyValueFactory<>("mNumberavailable"));
        inventory_col_wholesalePrice.setCellValueFactory(new PropertyValueFactory<>("wholesalePrice"));
        inventory_col_realPrice.setCellValueFactory(new PropertyValueFactory<>("realPrice"));
        inventory_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        inventory_col_soldNo.setCellValueFactory(new PropertyValueFactory<>("soldNo"));
        inventory_col_warehouse.setCellValueFactory(new PropertyValueFactory<>("wareHouse"));
        inventory_col_size.setCellValueFactory(new PropertyValueFactory<>("size"));
        inventory_col_color.setCellValueFactory(new PropertyValueFactory<>("color"));
        inventory_col_unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        inventoryTableView.setItems(inventoryListData);
        func.setupTableSearchFilter(inventory_Search, inventoryTableView, inventoryListData,
                Arrays.asList("id", "model", "warehouse", "mNumberavailable",
                        "suppliers", "realPrice", "type", "invoicesID", "soldNo",
                        "color", "size", "unit"));
    }

    public void purchaseDetailsSelectData() {
        purchaseInvoiceDetail prodDataDetails = purchaseDetailsTableView.getSelectionModel().getSelectedItem();
        if (prodDataDetails == null) {
            return;
        }

        warehouseList_purchaseDetails.setValue(prodDataDetails.getWarehouse());
        QuantityList_purchaseDetails.setValue(prodDataDetails.getUnit());
        sizeList_purchaseDetails.setValue(prodDataDetails.getSize());
        itemTypeList_purchaseDetails.setValue(prodDataDetails.getModel());
        purchaseDetails_productID.setText(prodDataDetails.getItemCode());
        purchaseDetails_model.setText(prodDataDetails.getItemName());
        color_purchaseDetails.setText(prodDataDetails.getColor());
        purchaseDetails_Quantity.setText(String.valueOf(prodDataDetails.getQuantity()));
        purchaseDetails_wholesalePrice.setText(String.valueOf(prodDataDetails.getPurchasePrice()));
        purchaseDetails_realPrice.setText(String.valueOf(prodDataDetails.getSellingPrice()));
    }

    public void addInvoicesTab() {
        inventoryShowData();
        loadDepartmentsIntoComboBox();
        warehouseListmenueShowData();
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private ObservableList<AccountStatement> accountStatementsDataList;

    public void accountStatementShowData() {
        accountStatementsDataList = subServices.accountStatementsDataList(SuppliersListAccountStatements, fromDatePickerAccountStatement, toDatePickerAccountStatement);
        accountStatement_col_index.setCellValueFactory(cellData
                -> new ReadOnlyObjectWrapper<>(accountStatementSuppliersTable.getItems().indexOf(cellData.getValue()) + 1)
        );
        accountStatement_col_index.setSortable(false);
        accountStatement_col_invoicesID_purchase.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));
        accountStatement_col_suppliers_purchase.setCellValueFactory(new PropertyValueFactory<>("supName"));
        accountStatement_col_phone_purchase.setCellValueFactory(new PropertyValueFactory<>("phone"));
        accountStatement_col_cash_purchase.setCellValueFactory(new PropertyValueFactory<>("cash"));
        accountStatement_col_deferred_purchase.setCellValueFactory(new PropertyValueFactory<>("deferred"));
        accountStatement_col_totalPrice_purchase.setCellValueFactory(new PropertyValueFactory<>("totalInvoice"));
        accountStatement_col_return_purchase.setCellValueFactory(new PropertyValueFactory<>("totalRetrun"));
        accountStatement_col_remarks_purchase.setCellValueFactory(new PropertyValueFactory<>("remarks"));
        accountStatement_col_date_purchase.setCellValueFactory(new PropertyValueFactory<>("date"));
        accountStatementSuppliersTable.setItems(accountStatementsDataList);

        double totalPurchase = 0.0;
        double totalRuturns = 0.0;
        double totalDeferred = 0.0;
        double totalCash = 0.0;

        for (AccountStatement statement : accountStatementsDataList) {
            totalDeferred += statement.getDeferred();
            totalPurchase += statement.getTotalInvoice();
            totalCash += (statement.getCash());
            totalRuturns += statement.getTotalRetrun();
        }
        totalPrint.setTotalCashSup(totalCash);
        totalPrint.setTotalDeferredSup(totalDeferred);
        totalPrint.setTotalPurchaseSup(totalPurchase);
        totalPrint.setTotalRuturnsSup(totalRuturns);
        totalPurchase_accountStatement.setText(Double.toString(totalPurchase));
        totalRuturns_accountStatement.setText(Double.toString(totalRuturns));
        totalDeferred_accountStatement.setText(Double.toString(totalDeferred));
        totalCash_accountStatement.setText(Double.toString(totalCash));

        func.setupTableSearchFilter(search_accountStatements, accountStatementSuppliersTable, accountStatementsDataList,
                Arrays.asList("supName", "invoiceId", "phone", "cash",
                        "deferred", "totalInvoice", "totalRetrun", "remarks"));
    }

    public void accountStatementSearchBtn() {
        accountStatementShowData();
    }

    public void accountStatementClearData() {
        search_accountStatements.clear();
        totalPurchase_accountStatement.clear();
        totalRuturns_accountStatement.clear();
        totalDeferred_accountStatement.clear();
        totalCash_accountStatement.clear();
        accountStatementSuppliersTable.setItems(null);
        SuppliersListAccountStatements.getSelectionModel().clearSelection();
        fromDatePickerAccountStatement.setValue(null);
        toDatePickerAccountStatement.setValue(null);
    }

    public void printSupplierAccountStatement() {
        printReportsValues suplierAccountStatement = new printReportsValues();
        suplierAccountStatement.setClintName(SuppliersListAccountStatements.getSelectionModel().getSelectedItem());
        suplierAccountStatement.setFromDate(fromDatePickerAccountStatement.getValue());
        suplierAccountStatement.setToDate(toDatePickerAccountStatement.getValue());
        suplierAccountStatement.setTotalCashSup(totalPrint.getTotalCashSup());
        suplierAccountStatement.setTotalDeferredSup(totalPrint.getTotalDeferredSup());
        suplierAccountStatement.setTotalRuturnsSup(totalPrint.getTotalRuturnsSup());
        suplierAccountStatement.setTotalPurchaseSup(totalPrint.getTotalPurchaseSup());
        func.printSuplierByDate(suplierAccountStatement, accountStatementsDataList);
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void backBtn() {
        addreturnPurchaseInvoicesForm.setVisible(false);
        viewreturnPurchaseInvoicesForm.setVisible(true);
        itemsRetrunShowData();
    }

    public void toAddFormBtn() {
        viewreturnPurchaseInvoicesForm.setVisible(false);
        addreturnPurchaseInvoicesForm.setVisible(true);
        returnShowDataReturns();
        purchaseRetrunShowData();
        returnMethodList_SuppliersReturn();
    }

    public void returnMethodList_SuppliersReturn() {
        List<String> methodL = new ArrayList<>();
        for (String data : RMethod_SuppliersReturn) {
            methodL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(RMethod_SuppliersReturn);
        returnMethodList_SuppliersReturn.getItems().clear();
        returnMethodList_SuppliersReturn.setItems(listData);
    }

    public void purchaseRetrunShowData() {
        ObservableList<purchaseInvoiceDetail> details = subServices.getAllPurchaseInvoiceDetails(null);

        inventory_col_wholesalePrice_SuppliersReturn.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        inventory_col_quantity_SuppliersReturn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        inventory_col_type_SuppliersReturn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        inventory_col_model_SuppliersReturn.setCellValueFactory(new PropertyValueFactory<>("model"));
        inventory_col_id_SuppliersReturn.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        inventory_col_supplier_SuppliersReturn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        inventory_col_inovicesId_SuppliersReturn.setCellValueFactory(new PropertyValueFactory<>("purchaseInvoiceId"));
        inventory_col_warehouse_SuppliersReturn.setCellValueFactory(new PropertyValueFactory<>("warehouse"));
        inventory_col_color_SuppliersReturn.setCellValueFactory(new PropertyValueFactory<>("color"));
        inventory_col_size_SuppliersReturn.setCellValueFactory(new PropertyValueFactory<>("size"));
        inventory_col_unit_SuppliersReturn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        inventoryTableView_SuppliersReturn.setItems(details);
        func.setupTableSearchFilter(search_purchaseInvoices_SuppliersReturn, inventoryTableView_SuppliersReturn, details,
                Arrays.asList("itemName", "color", "size", "model",
                        "warehouse", "unit", "supplierName"));
    }

    public void retrunItemBtn() {
        purchaseInvoiceDetail prodData = inventoryTableView_SuppliersReturn.getSelectionModel().getSelectedItem();
        Double quantity = Double.parseDouble(inventory_Mno_SuppliersReturn.getText());
        if (prodData == null) {
            al.E_Alert("حدد الصنف من فضلك", Alert.AlertType.ERROR);
        } else if (returnMethodList_SuppliersReturn.getSelectionModel().getSelectedItem() == null) {
            al.E_Alert("اختر طريقة استرجاع المال", Alert.AlertType.ERROR);
        } else if (func.isntNumeric(inventory_Mno_SuppliersReturn.getText()) || quantity <= 0) {
            al.E_Alert("ادخل كمية صالحة", Alert.AlertType.ERROR);
        } else if (quantity > prodData.getQuantity()) {
            al.E_Alert("يجب ان تكون الكمية المرتجعة اصغر من المباعة", Alert.AlertType.ERROR);
        } else {

            String returnType = returnMethodList_SuppliersReturn.getSelectionModel().getSelectedItem();
            Double returnQuantity = Double.parseDouble(inventory_Mno_SuppliersReturn.getText());

            al.Confirmation_AlertOptions("هل انت متأكد من المرتجع ؟");
            Optional<ButtonType> option = al.getAlert().showAndWait();

            if (option.isPresent() && option.get().equals(ButtonType.OK)) {
                prodData.setQuantity(quantity);
                subServices.returnSinglePurchaseItem(prodData, returnType, returnQuantity);
                clearReturnBtn();
            } else {
                al.E_Alert("تم االغاء المرتجع", AlertType.ERROR);
            }
        }
    }

    public void retrunPurchesBtn() {
        purchaseInvoiceDetail prodData = inventoryTableView_SuppliersReturn.getSelectionModel().getSelectedItem();
        if (prodData == null) {
            al.E_Alert("حدد الصنف من فضلك", Alert.AlertType.ERROR);
        } else {
            al.Confirmation_AlertOptions("هل انت متأكد من المرتجع ؟");
            Optional<ButtonType> option = al.getAlert().showAndWait();

            if (option.isPresent() && option.get().equals(ButtonType.OK)) {
                subServices.returnWholePurchaseInvoice(prodData.getPurchaseInvoiceId());
                clearReturnBtn();
            } else {
                al.E_Alert("تم االغاء المرتجع", AlertType.ERROR);
            }
        }
    }

    public void insertReturnItem(productData prodData) {
        int mNumberReturn;
        try {
            mNumberReturn = Integer.parseInt(inventory_Mno_SuppliersReturn.getText());
            if (mNumberReturn <= 0) {
                al.E_Alert("ادخل العدد اكبر من الصفر", Alert.AlertType.ERROR);
                return;
            }

        } catch (NumberFormatException e) {
            al.E_Alert("ادخل العدد عبارة عن ارقام", Alert.AlertType.ERROR);
            return;
        }

        String insertData = "INSERT INTO returnItemPurchase (item_id, model, mNumberavailable, wholesaleprice, type, suppliers, invoicesID, mNumberReturn, warehouse) VALUES (?,?,?,?,?,?,?,?,?)";

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(insertData)) {

            ps.setInt(1, prodData.getId());
            ps.setString(2, prodData.getModel());
            ps.setDouble(3, prodData.getMNumberavailable() - mNumberReturn);
            ps.setDouble(4, prodData.getWholesalePrice());
            ps.setString(5, prodData.getType());
            ps.setString(6, prodData.getSuppliers());
            ps.setInt(7, prodData.getInvoicesID());
            ps.setInt(8, mNumberReturn);
            ps.setString(9, prodData.getWareHouse());
            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("فشل اضافة الصنف الى قائمة المرتجعات");
            }
        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء ارجاع الصنف في قاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void updateReturnItem(productData prodData) {
        int mNumberavailable;
        try {
            mNumberavailable = Integer.parseInt(inventory_Mno_SuppliersReturn.getText());
            if (mNumberavailable <= 0) {
                al.E_Alert("ادخل العدد اكبر من الصفر", Alert.AlertType.ERROR);
                return;
            }

        } catch (Exception e) {
            al.E_Alert("ادخل العدد عبارة عن ارقام", Alert.AlertType.ERROR);
            return;
        }

        String sql = "UPDATE product SET mNumberavailable=mNumberavailable - ? WHERE id=?";

        try ( Connection conn = database.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, mNumberavailable);
            pstmt.setInt(2, prodData.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            al.E_Alert("فشل تعديل بيانات الصنف" + e, Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void deleteReturnItem(productData prodData) {
        String deleteSql = "DELETE FROM product WHERE id = ?";

        try ( Connection conn = database.getConnection();  PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
            pstmt.setInt(1, prodData.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            al.E_Alert("فشل حذف الصنف من جدول الاصناف:\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void updateReturnInvoices(productData prodData, int num) {
        String updateSql;
        String retrunMethod = returnMethodList_SuppliersReturn.getSelectionModel().getSelectedItem();
        double cash = 0;
        double deferred = 0;
        double total = num * prodData.getWholesalePrice();
        double change = 0;

        String money = "SELECT cash, deferred FROM purchase_invoices WHERE id = " + prodData.getInvoicesID();

        try ( Connection conn = database.getConnection();  PreparedStatement invoicesPS = conn.prepareStatement(money);  ResultSet invoicesRs = invoicesPS.executeQuery();) {

            if (invoicesRs.next()) {
                cash = invoicesRs.getDouble("cash");
                deferred = invoicesRs.getDouble("deferred");
            }
        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء  تحميل الفاتورة", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        if (retrunMethod.equals("نقدي")) {
            if (total > cash) {
                change = total - cash;
                total = cash;
                updateSql = "UPDATE purchase_invoices SET deferred=deferred - " + change + ", cash=cash - ? WHERE id=?";
            } else {
                updateSql = "UPDATE purchase_invoices SET cash=cash - ? WHERE id=?";
            }
        } else if (retrunMethod.equals("اجل")) {
            if (total > deferred) {
                change = total - deferred;
                total = deferred;
                updateSql = "UPDATE purchase_invoices SET deferred=deferred - ?, cash=cash - " + change + " WHERE id=?";
            } else {
                updateSql = "UPDATE purchase_invoices SET deferred=deferred - ? WHERE id=?";
            }
        } else {
            al.E_Alert("اختر طريقة استرجاع المال", Alert.AlertType.ERROR);
            return;
        }
        try ( Connection conn = database.getConnection();  PreparedStatement pstmt = conn.prepareStatement(updateSql)) {

            pstmt.setDouble(1, total);
            pstmt.setInt(2, prodData.getInvoicesID());

            pstmt.executeUpdate();
            al.E_Alert("تمت عملية الارجاع بنجاح و المبلغ المراد استلامة: " + num * prodData.getWholesalePrice(), Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            al.E_Alert("رقم الفاتورة الخارجي موجود بالفعل", Alert.AlertType.ERROR);
//            al.E_Alert("خطأ أثناء تعديل الفاتورة:\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void selecteditemsinReturnForm() {
        purchaseInvoiceDetail prodData = inventoryTableView_SuppliersReturn.getSelectionModel().getSelectedItem();
        inventory_invoicesID_suppliersReturn.setText(prodData.getPurchaseInvoiceId() + "");
        inventory_productID_suppliersReturn.setText(prodData.getId() + "");
        inventory_model_SuppliersReturn.setText(prodData.getModel());
        inventory_Mno_SuppliersReturn.setText(prodData.getQuantity() + "");
        inventory_color_suppliersReturn.setText(prodData.getColor());
        inventory_size_SuppliersReturn.setText(prodData.getSize());
        inventoryLable_quantity_SuppliersReturn.setText("الكمية بـ" + prodData.getUnit());
    }

    public void clearReturnBtn() {
        inventory_Mno_SuppliersReturn.clear();
        inventory_model_SuppliersReturn.clear();
        inventory_productID_suppliersReturn.clear();
        inventory_invoicesID_suppliersReturn.clear();
        search_purchaseInvoices_SuppliersReturn.clear();
        returnMethodList_SuppliersReturn.getSelectionModel().clearSelection();
        inventory_color_suppliersReturn.clear();
        inventory_size_SuppliersReturn.clear();
        inventoryLable_quantity_SuppliersReturn.setText("الكمية بـ");
        purchaseRetrunShowData();
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void showPurchaseReturn() {
        backBtn();
    }

    private ObservableList<ReturnedItemsToSuppliers> itemReturnDataList;

    public void itemsRetrunShowData() {
        itemReturnDataList = subServices.itemReturnDataList();
        item_col_wholesalePrice.setCellValueFactory(new PropertyValueFactory<>("wholesalePrice"));
        item_col_mNoReturned.setCellValueFactory(new PropertyValueFactory<>("mNumberReturn"));
        item_col_mNo.setCellValueFactory(new PropertyValueFactory<>("mNumberavailable"));
        item_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        item_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        item_col_supplier.setCellValueFactory(new PropertyValueFactory<>("suppliers"));
        item_col_itemId.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        item_col_invoicesId.setCellValueFactory(new PropertyValueFactory<>("invoicesID"));
        item_col_returnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        item_col_warehouse.setCellValueFactory(new PropertyValueFactory<>("wareHouse"));
        item_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        item_col_unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        item_col_color.setCellValueFactory(new PropertyValueFactory<>("color"));
        item_col_size.setCellValueFactory(new PropertyValueFactory<>("size"));
        itemsReturnTableView.setItems(itemReturnDataList);

        func.setupTableSearchFilter(search_purchaseItems, itemsReturnTableView, itemReturnDataList,
                Arrays.asList("wholesalePrice", "mNumberReturn", "warehouse", "mNumberavailable", "suppliers",
                        "item_id", "type", "invoicesID", "id", "size", "color", "unit"));
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public ObservableList<Employee> emplyeeDataList() {
        ObservableList<Employee> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM employee ORDER BY username COLLATE NOCASE";

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password")
                );
                listData.add(employee);
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ اثناء تحميل بيانات الموظفين", AlertType.ERROR);
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<Employee> emplyeeDataList;

    public void employeddShowData() {
        emplyeeDataList = emplyeeDataList();
        col_emplyeeId.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_employeeUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_employeePassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        employeeTable.setItems(emplyeeDataList);
    }

    public void employeeSelectData() {
        Employee employee = employeeTable.getSelectionModel().getSelectedItem();
        if (employee == null) {
            return;
        }

        emplyeeName.setText(employee.getUsername());
        employeePassword.setText(employee.getPassword());
        rolesGetData(employee.getId());
    }

    public void employeeRemoveBtn() {
        emplyeeName.clear();
        employeePassword.clear();
    }

    public void employeesUpdateBtn() {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();
//        String userName = emplyeeName.getText();
        String password = employeePassword.getText();
        if (selected == null) {
            al.E_Alert("للتعديل يجب اختيار المستخدم اولا من الجدول", Alert.AlertType.ERROR);
            return;
        }

        if (password.length() < 8) {
            al.E_Alert("الرقم السري يجب الا يقل عن 8 احرف", Alert.AlertType.ERROR);
            return;
        }

//        String sql = "UPDATE employee SET username=?, password=? WHERE id=?";
        String sql = "UPDATE employee SET password=? WHERE id=?";

        try ( Connection conn = database.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, userName);
            pstmt.setString(1, password);
            pstmt.setInt(2, selected.getId());
            pstmt.executeUpdate();
            al.E_Alert("تم تعديل بيانات المستخدم بنجاح", Alert.AlertType.INFORMATION);
            employeddShowData();
            employeeRemoveBtn();
        } catch (SQLException e) {
            al.E_Alert("فشل تعديل بيانات المستخدم" + e, Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void employeeDeleteBtn() {
        Employee selected = employeeTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            al.E_Alert("للحذف يجب اختيار موظف اولا من الجدول", Alert.AlertType.ERROR);
            return;
        }

        if (selected.getUsername().equals("admin")) {
            al.E_Alert("لا يمكن حذف هذا المستخدم", Alert.AlertType.ERROR);
        } else {
            String sql = "DELETE FROM employee WHERE id=?";

            try ( Connection conn = database.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, selected.getId());
                pstmt.executeUpdate();
                al.E_Alert("تم حذف المستخدم بنجاح", Alert.AlertType.INFORMATION);
                employeddShowData();
                employeeRemoveBtn();
            } catch (SQLException e) {
                al.E_Alert("فشل حذف المستخدم" + e, Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }

    }

    public void searchEmployeeFromDB() {
        ObservableList<Employee> searchResults = FXCollections.observableArrayList();
        String searchText = searchEmployee.getText().trim();
        String sql = "SELECT * FROM employee WHERE username LIKE ? OR password LIKE ? ORDER BY username COLLATE NOCASE";

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql)) {

            ps.setString(1, "%" + searchText + "%");
            ps.setString(2, "%" + searchText + "%");

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Employee employee = new Employee(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password")
                    );
                    searchResults.add(employee);
                }
            }
            employeeTable.setItems(searchResults);

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء الاتصال بقاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ObservableList<Departments> departmentDataList() {
        ObservableList<Departments> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM department ORDER BY department COLLATE NOCASE";

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Departments departments = new Departments(
                        rs.getInt("id"),
                        rs.getString("department")
                );
                listData.add(departments);
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ اثناء تحميل البنود", AlertType.ERROR);
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<Departments> departmentDataList;

    public void departmentShowData() {
        departmentDataList = departmentDataList();
        col_departmentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_dedepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
        departmentTable.setItems(departmentDataList);
        loadDepartmentsIntoComboBox();
    }

    public void departmentSelectData() {
        Departments department = departmentTable.getSelectionModel().getSelectedItem();
        if (department == null) {
            return;
        }

        departmentTextField.setText(department.getDepartment());
    }

    public void departmentRemoveBtn() {
        departmentTextField.clear();
    }

    public void departmentDeleteBtn() {
        Departments selected = departmentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            al.E_Alert("للحذف يجب اختيار موظف اولا من الجدول", Alert.AlertType.ERROR);
            return;
        }

        String sql = "DELETE FROM department WHERE id=?";

        try ( Connection conn = database.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, selected.getId());
            pstmt.executeUpdate();
            al.E_Alert("تم حذف البند بنجاح", Alert.AlertType.INFORMATION);
            departmentShowData();
            departmentRemoveBtn();

        } catch (SQLException e) {
            al.E_Alert("فشل الحذف" + e, Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void departmentAddbtn() {
        String department = departmentTextField.getText().trim();

        if (department.isEmpty()) {
            al.E_Alert("يجب ادخال البند اولا", Alert.AlertType.ERROR);
            return;
        }

        String checkSql = "SELECT COUNT(*) FROM department WHERE department = ?";
        String insertSql = "INSERT INTO department (department) VALUES (?)";

        try ( Connection conn = database.getConnection();  PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, department);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                al.E_Alert("هذا البند موجود بالفعل!", Alert.AlertType.WARNING);
                return;
            }

            try ( PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, department);
                insertStmt.executeUpdate();
                al.E_Alert("تم اضافة البند بنجاح", Alert.AlertType.INFORMATION);
                departmentShowData(); // reload table
                departmentRemoveBtn();
            }

        } catch (SQLException e) {
            al.E_Alert("فشل اضافة بند جديد:\n" + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void loadDepartmentsIntoComboBox() {
        String sql = "SELECT department FROM department";
        try ( Connection conn = database.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql);  ResultSet rs = pstmt.executeQuery()) {
            itemTypeListSearch_addForm.getItems().clear();
            itemTypeList_menuForm.getItems().clear();
            itemTypeList_salesForm.getItems().clear();

            itemTypeListSearch_currentStockBalance.getItems().clear();
            itemTypeListSearch_transferForm.getItems().clear();
            itemTypeList_itemsMovementForm.getItems().clear();
            itemTypeListSearch_boundsForm.getItems().clear();
            itemTypeListSearch_stockTakingForm.getItems().clear();
            itemTypeList_purchaseDetails.getItems().clear();
            boolean check = false;
            while (rs.next()) {
                check = true;
                itemTypeListSearch_addForm.getItems().add(rs.getString("department"));
                itemTypeList_menuForm.getItems().add(rs.getString("department"));
                itemTypeList_salesForm.getItems().add(rs.getString("department"));
                itemTypeList_purchaseDetails.getItems().add(rs.getString("department"));

                itemTypeListSearch_currentStockBalance.getItems().add(rs.getString("department"));
                itemTypeListSearch_transferForm.getItems().add(rs.getString("department"));
                itemTypeList_itemsMovementForm.getItems().add(rs.getString("department"));
                itemTypeListSearch_boundsForm.getItems().add(rs.getString("department"));
                itemTypeListSearch_stockTakingForm.getItems().add(rs.getString("department"));
            }
            if (!check) {
                al.E_Alert("لا يوجد بنود مسجلة", AlertType.NONE);
                return;
            }
        } catch (SQLException e) {
            al.E_Alert("فشل تحميل البنود:\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // TO SHOW DATA ON OUR TABLE
    private ObservableList<productData> menueListData;

    public void menueShowData() {
        String type = itemTypeList_menuForm.getSelectionModel().getSelectedItem();
        String wareHouse = warehouseList_menuForm.getSelectionModel().getSelectedItem();

        if (wareHouse == null && type == null) {
            return;
        }

        menueListData = services.menueDataList(type, wareHouse);
        menu_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        menu_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        menu_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        menu_col_mNo.setCellValueFactory(new PropertyValueFactory<>("mNumberavailable"));
        menu_col_realPrice.setCellValueFactory(new PropertyValueFactory<>("realPrice"));
        menu_col_color.setCellValueFactory(new PropertyValueFactory<>("color"));
        menu_col_size.setCellValueFactory(new PropertyValueFactory<>("size"));
        menu_col_unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        menuTableView.setItems(menueListData);

        func.setupTableSearchFilter(menu_Search, menuTableView, menueListData,
                Arrays.asList("id", "model", "type", "mNumberavailable",
                        "realPrice", "color", "size", "unit"));

    }

    private ObservableList<Invoices> deferredInvoiceL;

    public void deferredInvoiceShowData() {
        deferredInvoiceL = services.getInvoicesData(1, null, null, null);
        deferred_col_invoiceID.setCellValueFactory(new PropertyValueFactory<>("invoice_id"));//
        deferred_col_totalQty.setCellValueFactory(new PropertyValueFactory<>("total_quantity"));//
        deferred_col_totalBeforeDiscount.setCellValueFactory(new PropertyValueFactory<>("total_price_before_discount"));//
        deferred_col_discount.setCellValueFactory(new PropertyValueFactory<>("discount_percentage"));//
        deferred_col_totalAfterDiscount.setCellValueFactory(new PropertyValueFactory<>("total_price_after_discount"));//
        deferred_col_cash.setCellValueFactory(new PropertyValueFactory<>("cashPay"));//
        deferred_col_instapay.setCellValueFactory(new PropertyValueFactory<>("instaPay"));//
        deferred_col_voda.setCellValueFactory(new PropertyValueFactory<>("vodafonePay"));//
        deferred_col_deferred.setCellValueFactory(new PropertyValueFactory<>("deffered"));//
        deferred_col_username.setCellValueFactory(new PropertyValueFactory<>("username"));//
        deferred_col_date.setCellValueFactory(new PropertyValueFactory<>("invoice_date"));//
        deferred_col_clientName.setCellValueFactory(new PropertyValueFactory<>("clientName"));//
        deferred_col_clientPhone.setCellValueFactory(new PropertyValueFactory<>("clientPhone"));//

        deferredInvoiceTable.setItems(deferredInvoiceL);
        func.setupTableSearchFilter(deferredInvoice_search, deferredInvoiceTable, deferredInvoiceL,
                Arrays.asList("invoice_id", "total_quantity", "total_price_before_discount", "discount_percentage", "total_price_after_discount",
                        "cashPay", "deffered", "username", "invoice_date", "clientName", "clientPhone"));
    }
    private ObservableList<Returns> returnItemL;

    public void returnsShowData() {
        returnItemL = services.getAllReturns();

        retrunProduct_col_date.setCellValueFactory(new PropertyValueFactory<>("return_date"));
        retrunProduct_col_money.setCellValueFactory(new PropertyValueFactory<>("price_of_return"));
        retrunProduct_col_reson.setCellValueFactory(new PropertyValueFactory<>("return_reason"));
        retrunProduct_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity_returned"));
        retrunProduct_col_warehouse.setCellValueFactory(new PropertyValueFactory<>("warehouse"));
        retrunProduct_col_modelName.setCellValueFactory(new PropertyValueFactory<>("model_name"));
        retrunProduct_col_modelID.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        retrunProduct_col_invoiceID.setCellValueFactory(new PropertyValueFactory<>("invoice_id"));
        returnProduct_col_id.setCellValueFactory(new PropertyValueFactory<>("return_id"));
        retrunProduct_col_modelSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        retrunProduct_col_modelColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        retrunProduct_col_modelUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));

        returnProductTableView.setItems(returnItemL);

        // تفعيل البحث
        func.setupTableSearchFilter(returnProduct_Search, returnProductTableView, returnItemL,
                Arrays.asList("return_reason", "model_name", "product_id", "invoice_id",
                        "return_id", "color", "size", "unit"));

    }

    private SpinnerValueFactory<Double> spin;

    public void setQuantity() {
        menu_spinner_no.setText("0.0");
    }

    public void menueSelectData() {
        productData prodData = menuTableView.getSelectionModel().getSelectedItem();
        if (prodData == null) {
            return;
        }

//        menu_productID.setText(Integer.toString(prodData.getId()));
        menu_model.setText(prodData.getModel());
        menu_productColor.setText(prodData.getColor());
        menu_productSize.setText(prodData.getSize());
        unit_label.setText(prodData.getUnit());
//        menu_spinner_no.setText(prodData.getMNumberavailable().toString());
        menu_realPrice.setText(Double.toString(prodData.getRealPrice()));

//        data.price = prodData.getRealPrice();
    }

    // TO SHOW DATA ON OUR TABLE
    private ObservableList<InvoiceItem> SalesList;

    public void salesShowData() {
        SalesList = salesDataList();
        receipt_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        receipt_col_No.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        receipt_col_price.setCellValueFactory(new PropertyValueFactory<>("totalP"));
        receipt_tableView.setItems(SalesList);
    }
    ObservableList<InvoiceItem> listData = FXCollections.observableArrayList();

    public ObservableList<InvoiceItem> salesDataList() {
        productData prodData = menuTableView.getSelectionModel().getSelectedItem();

        if (prodData == null) {
            al.E_Alert("يرجى اختيار منتج أولاً", AlertType.WARNING);
            return listData;
        }

        try {
            data.price = Double.parseDouble(menu_realPrice.getText());
        } catch (NumberFormatException e) {
            al.E_Alert("ادخل سعر المنتج عبارة عن أرقام فقط", AlertType.ERROR);
            return listData;
        }

        int id = prodData.getId();
        String modelID = String.valueOf(id);

        double qty = Double.valueOf(menu_spinner_no.getText());
        double price = data.price;
        double pricePeforDis = prodData.getRealPrice();

        double availableQty = prodData.getMNumberavailable();
        Double existingQtyInTable = 0.0;

        for (InvoiceItem item : listData) {
            if (item.getModelID().equals(modelID)) {
                existingQtyInTable = item.getQuantity();
                break;
            }
        }

        double totalRequestedQty = existingQtyInTable + qty;
        if (totalRequestedQty > availableQty) {
            al.E_Alert("الكمية المطلوبة (" + totalRequestedQty + ") أكبر من الكمية المتاحة في المخزن (" + availableQty + ")", AlertType.WARNING);
            return listData;
        }

        double totalPerItem = qty * price;
        double totalPerItemPeforDis = qty * pricePeforDis;

        // Check if item already exists in the list
        for (InvoiceItem item : listData) {
            if (item.getModelID().equals(modelID)) {
                Double newQty = item.getQuantity() + qty;
                double newTotal = newQty * price;
                double newTotalBeforeDiscount = newQty * pricePeforDis;

                item.setQuantity(newQty);
                item.setTotalP(newTotal);
                item.setTotalPriceBeforeDis(newTotalBeforeDiscount);

                data.Total_invoice_price += totalPerItem;
                data.Total_invoice_price_peforDis += totalPerItemPeforDis;

                receipt_tableView.refresh();
                return listData;
            }
        }

        // Add new item if not found
        InvoiceItem invoiceItem = new InvoiceItem(
                id,
                menu_model.getText(),
                qty,
                totalPerItem,
                pricePeforDis,
                pricePeforDis,
                totalPerItemPeforDis,
                warehouseList_menuForm.getSelectionModel().getSelectedItem(),
                menu_productColor.getText(),
                menu_productSize.getText()
        );

        listData.add(invoiceItem);

        data.Total_invoice_price += totalPerItem;
        data.Total_invoice_price_peforDis += totalPerItemPeforDis;
        DecimalFormat df = new DecimalFormat("#,###.#");
        df.format(data.Total_invoice_price);

        return listData;
    }

    public void menuRemoveBtn() {
        disqaunt_textField.setText("0");
        menu_total.setText("0.0");
        menu_cashPay.setText("0");
        menu_VodafonePay.setText("0");
        menu_instaPay.setText("0");
        menu_deferred.setText("0");
        receipt_tableView.getItems().clear();
        menu_Search.clear();
        menu_model.clear();
        menu_productSize.clear();
        menu_productColor.clear();
//        menu_productID.clear();
        menu_realPrice.clear();
        menu_clientPhone.clear();
        menu_clientName.getItems().clear();
        setQuantity();
        data.Total_invoice_price = 0;
        data.price = 0;
        data.invoiceId = 0;
        data.Total_invoice_price_peforDis = 0;
        data.pricePeforDisForItem = 0;
        itemTypeList_menuForm.getSelectionModel().clearSelection();
        warehouseList_menuForm.getSelectionModel().clearSelection();
        menuTableView.getSelectionModel().clearSelection();
        returnClintList();
        menuTableView.setItems(
                FXCollections.observableArrayList(menuTableView.getItems())
        );
        menuTableView.getItems().clear();
    }

    public void returnInvoicesBtn() {
        if (return_reciptID.getText() == null || return_reciptID.getText().isEmpty()) {
            al.E_Alert("يجب اختيار رقم فاتورة اولا", AlertType.ERROR);
        } else if (!return_reciptID.getText().matches("\\d+")) {
            al.E_Alert("يجب ان يكون رقم الفاتورة عباره عن رقم ", AlertType.ERROR);
        } else if (returnMethodList.getSelectionModel().getSelectedItem() == null) {
            al.E_Alert("يجب اختيار طريقه الاسترجاع", AlertType.ERROR);
        } else {
            services.returnProductsToStockBeforeInvoiceDeletion(Integer.valueOf(return_reciptID.getText()));
            services.deleteSalesById(Integer.valueOf(return_reciptID.getText()), returnMethodList.getSelectionModel().getSelectedItem());
            clearReturnsForm();
            returnShowDataReturns();
//            al.information_Alert("تم حذف الفاتورة وتسجيل المرتجعات بنجاح");
        }
    }

    public void returnShowDataReturns() {
        List<productData> salesList = services.getAllSales(null, null, null);
        return_col_id.setCellValueFactory(new PropertyValueFactory<>("modelID"));
        return_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        return_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        return_col_mNo.setCellValueFactory(new PropertyValueFactory<>("mNumberavailable"));
        return_col_realPrice.setCellValueFactory(new PropertyValueFactory<>("total"));
        return_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        return_col_wareHouse.setCellValueFactory(new PropertyValueFactory<>("wareHouse"));
        return_col_invoiceId.setCellValueFactory(new PropertyValueFactory<>("invoicesID"));
        return_col_unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        return_col_size.setCellValueFactory(new PropertyValueFactory<>("size"));
        return_col_color.setCellValueFactory(new PropertyValueFactory<>("color"));

        ObservableList<productData> observableSales = FXCollections.observableArrayList(salesList);
        returnTableView.setItems(observableSales);
        func.setupTableSearchFilter(retrun_Search_field, returnTableView, observableSales,
                Arrays.asList("modelID", "model", "type", "mNumberavailable", "total",
                        "date", "warehouse", "invoicesID", "color", "size", "unit"));
    }

    public void returnRemoveBtn() {
        receipt_tableView.getItems().clear();
        retrun_Search_field.clear();
        return_model.clear();
        return_productID.clear();
        return_realPrice.clear();
        setQuantityr();
        return_reciptID.clear();
        return_reson.clear();
    }

    public void setQuantityr() {
        return_spinner_no.setText("0.0");
    }

    public void returnSelectData() {
        productData prodData = returnTableView.getSelectionModel().getSelectedItem();
        if (prodData == null) {
            return;
        }

        wareHouses = prodData.getWareHouse();
        return_productID.setText(Integer.toString(prodData.getModelID()));
        return_model.setText(prodData.getModel() + " " + prodData.getColor() + " " + prodData.getSize());
        return_realPrice.setText(Double.toString(prodData.getRealPrice()));
        return_reciptID.setText(Integer.toString(prodData.getInvoicesID()));
        unit_returnForm.setText(prodData.getUnit());
        data.price = prodData.getRealPrice();
    }

    public void returnMethodList() {
        List<String> methodL = new ArrayList<>();
        for (String data : RMethod) {
            if (!"اجل".equals(data)) {
                methodL.add(data);
            }
        }

        ObservableList<String> listData = FXCollections.observableArrayList(methodL);
        returnMethodList.getItems().clear();
        returnMethodList.setItems(listData);
    }

    public void returnClintList() {
        String custName;
        List<String> customer = new ArrayList<>();
        for (customer data : customersL) {
            custName = data.getName();
            customer.add(custName);
        }
        ObservableList listData = FXCollections.observableArrayList(customer);
        menu_clientName.getItems().clear();
        menu_clientName.setItems(listData);
        clientsListAccountStatementsDetails.getItems().clear();
        clientsListAccountStatementsDetails.setItems(listData);
        clientsListAccountStatements.getItems().clear();
        clientsListAccountStatements.setItems(listData);
    }

    public void returnClintTable() {
        customersL = services.getAllCustomers();
        addClients_col_remarks_sales.setCellValueFactory(new PropertyValueFactory<>("remark"));
        addClients_col_clientAddress_sales.setCellValueFactory(new PropertyValueFactory<>("adress"));
        addClients_col_clientPhone_sales.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addClients_col_clientName_sales.setCellValueFactory(new PropertyValueFactory<>("name"));
        addClients_col_clientID_sales.setCellValueFactory(new PropertyValueFactory<>("id"));

        ObservableList listData = FXCollections.observableArrayList(customersL);
        addClientsTable.setItems(listData);

        // تفعيل البحث
        func.setupTableSearchFilter(search_clientSales, addClientsTable, listData,
                Arrays.asList("name", "phone", "adress", "remark"));
    }

    public void updatePhoneFromClint() {
        String clintName = menu_clientName.getSelectionModel().getSelectedItem();
        for (customer data : customersL) {
            if (data.getName() == clintName) {
                menu_clientPhone.setText(data.getPhone());
            }
        }
    }

    public void processReturn() {
        productData proData = returnTableView.getSelectionModel().getSelectedItem();
        int invoiceId = 0;
        int productId = 0;
        String model = "";
        Double returnQuantity = Double.valueOf(return_spinner_no.getText());
        double p_price = 0;
        double discount_percentage = 0;
        double totalRefund = 0;

        String returnMethod = (String) returnMethodList.getSelectionModel().getSelectedItem();
        String payMethod = null;
        switch (returnMethod) {
            case "نقدي":
                payMethod = "cashPay";
                break;
            case "انستا باي":
                payMethod = "instaPay";
                break;
            case "فودافون كاش":
                payMethod = "vodafonePay";
                break;
            default:
                al.E_Alert("ادخل طريقة الاسترجاع من فضلك", AlertType.ERROR);
                return;
        }

        if (return_reson.getText().isEmpty()) {
            al.E_Alert("ادخل السبب من فضلك", AlertType.ERROR);
            return;
        }

        if (returnQuantity <= 0) {
            al.E_Alert("كمية الإرجاع يجب أن تكون أكبر من صفر", AlertType.ERROR);
            return;
        }

        try {
            invoiceId = Integer.parseInt(return_reciptID.getText());
            productId = Integer.parseInt(return_productID.getText());
            model = return_model.getText();
//            p_price = Double.parseDouble(return_realPrice.getText());
        } catch (NumberFormatException e) {
            al.E_Alert("ادخل البيانات بشكل صحيح", AlertType.ERROR);
            return;
        }

        try ( Connection connect = database.getConnection()) {
            connect.setAutoCommit(false);

            // Get invoice discount
            double discount;
            try ( PreparedStatement stmt = connect.prepareStatement("SELECT discount_percentage FROM invoices WHERE invoice_id = ?")) {
                stmt.setInt(1, invoiceId);
                try ( ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        al.E_Alert("رقم الفاتورة غير موجود", AlertType.ERROR);
                        return;
                    }
                    discount = rs.getDouble("discount_percentage");
                }
            }

            Double remainingToReturn = returnQuantity;
            try ( PreparedStatement salesStmt = connect.prepareStatement(
                    "SELECT sales_id, quantity, totalWholesalesPrice, realprice, total_price FROM sales WHERE invoice_id = ? AND model_id = ? ORDER BY sales_id ASC")) {

                salesStmt.setInt(1, invoiceId);
                salesStmt.setInt(2, productId);

                try ( ResultSet salesRs = salesStmt.executeQuery()) {
                    while (salesRs.next() && remainingToReturn > 0) {
                        int saleId = salesRs.getInt("sales_id");
                        double saleQty = salesRs.getDouble("quantity");
                        double totalWholesale = salesRs.getDouble("totalWholesalesPrice");
                        double totalRealsale = salesRs.getDouble("total_price");
                        double totalRealPrice = salesRs.getDouble("realprice");

                        if (saleQty == 0) {
                            continue;
                        }

                        Double qtyToReturn = Math.min(remainingToReturn, saleQty);
                        double wholesalePerUnit = totalWholesale / saleQty;
                        double realsalePerUnit = totalRealsale / saleQty;
                        double realPricePerUnit = totalRealPrice / saleQty;

                        double returnedWholesale = wholesalePerUnit * qtyToReturn;
                        p_price = realsalePerUnit;

                        double discountByMony = realPricePerUnit - realsalePerUnit;
//                        double priceAfterDiscount = p_price * (1 - discount);
                        double refundAmount = p_price * qtyToReturn;
                        totalRefund += refundAmount;
//                            totalRefund = refundAmount * ;
                        String typeFromProduct = "غير معروف";
                        String colorFromProduct = "غير معروف";
                        String sizeFromProduct = "غير معروف";
                        String unitFromProduct = "غير معروف";
                        String typeQuery = "SELECT type, color, size, unit FROM product WHERE id = ?";
                        try ( PreparedStatement typeStmt = connect.prepareStatement(typeQuery)) {
                            typeStmt.setInt(1, productId);
                            ResultSet typeRs = typeStmt.executeQuery();

                            if (typeRs.next()) {
                                typeFromProduct = typeRs.getString("type");
                                colorFromProduct = typeRs.getString("color");
                                sizeFromProduct = typeRs.getString("size");
                                unitFromProduct = typeRs.getString("unit");
                            }
                        }
                        try ( PreparedStatement updateProduct = connect.prepareStatement("UPDATE product SET mNumberavailable = mNumberavailable + ?, soldNo = soldNo - ? WHERE id = ?");  PreparedStatement updateInvoice = connect.prepareStatement("UPDATE invoices SET total_quantity = total_quantity - ?, total_price_after_discount = total_price_after_discount - ?, total_price_before_discount = total_price_before_discount - ?, " + payMethod + " = " + payMethod + " - ?, discount_percentage = discount_percentage - ? WHERE invoice_id = ?");  PreparedStatement updateSales = connect.prepareStatement("UPDATE sales SET quantity = quantity - ?, total_price = total_price - ?, totalWholesalesPrice = totalWholesalesPrice - ?, realprice = realprice - ? WHERE sales_id = ?");  PreparedStatement insertReturn = connect.prepareStatement("INSERT INTO returns (invoice_id, product_id, quantity_returned, price_of_return, return_reason, model_name,type,color,size,unit,warehouse) VALUES (?,?,?,?, ?, ?, ?, ?, ?,?,?)")) {

                            updateProduct.setDouble(1, qtyToReturn);
                            updateProduct.setDouble(2, qtyToReturn);
                            updateProduct.setInt(3, productId);
                            updateProduct.executeUpdate();

                            updateInvoice.setDouble(1, qtyToReturn);
                            updateInvoice.setDouble(2, refundAmount);
                            updateInvoice.setDouble(3, realPricePerUnit * qtyToReturn);
                            updateInvoice.setDouble(4, refundAmount);
                            updateInvoice.setDouble(5, discountByMony * qtyToReturn);
                            updateInvoice.setInt(6, invoiceId);
                            updateInvoice.executeUpdate();

                            updateSales.setDouble(1, qtyToReturn);
                            updateSales.setDouble(2, refundAmount);
                            updateSales.setDouble(3, returnedWholesale);
                            updateSales.setDouble(4, realPricePerUnit * qtyToReturn);
                            updateSales.setInt(5, saleId);
                            updateSales.executeUpdate();

                            insertReturn.setInt(1, invoiceId);
                            insertReturn.setInt(2, productId);
                            insertReturn.setDouble(3, qtyToReturn);
                            insertReturn.setDouble(4, refundAmount);
                            insertReturn.setString(5, return_reson.getText());
                            insertReturn.setString(6, model);
                            insertReturn.setString(7, typeFromProduct);
                            insertReturn.setString(8, colorFromProduct);
                            insertReturn.setString(9, sizeFromProduct);
                            insertReturn.setString(10, unitFromProduct);
                            insertReturn.setString(11, wareHouses);
                            insertReturn.executeUpdate();
                        }

                        remainingToReturn -= qtyToReturn;
                    }
                }
            }

            if (remainingToReturn > 0) {
                connect.rollback();
                al.E_Alert("الكمية المراد إرجاعها أكبر من الموجودة في الفاتورة.", AlertType.ERROR);
                return;
            }

            connect.commit();
            al.E_Alert("تمت العملية بنجاح. المبلغ المرتجع: " + totalRefund, AlertType.INFORMATION);
            returnRemoveBtn();
            returnShowDataReturns();
            recordItemMovement.recordItemMovement("دخول مرتجع", returnQuantity, invoiceId, "استرداد الي: " + wareHouses, productId, model, wareHouses);

        } catch (Exception e) {
            al.E_Alert("حدث خطأ أثناء الإرجاع: " + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void returnActionBtn() {
        if ((returnTableView.getSelectionModel().getSelectedItem() == null)) {
            al.E_Alert("اختر المنتج من فضلك", AlertType.ERROR);
        } else if (return_reciptID.getText().isEmpty() || return_reson.getText().isEmpty() || returnMethodList.getSelectionModel().getSelectedItem() == null) {
            al.E_Alert("ادخل رقم الفاتورة و السبب و طريقة استرجاع المال", AlertType.ERROR);
        } else if (func.isntNumeric(return_reciptID.getText()) || func.isntNumeric(return_spinner_no.getText())
                || Double.parseDouble(return_reciptID.getText().trim()) <= 0.0
                || Double.parseDouble(return_spinner_no.getText().trim()) <= 0.0) {
            al.E_Alert("الرجاء ادخال ارقام صالحه", AlertType.ERROR);
        } else {
            processReturn();
            returnShowDataReturns();
        }
//        returnRemoveBtn();
    }

    public void payMethod() {
        try {
            if (SalesList == null || SalesList.size() == 0) {
                al.E_Alert("اختر المنتجات المراد بيعها من فضلك", AlertType.ERROR);
                return;
            }

            double inputCash = Double.parseDouble(menu_cashPay.getText());
            double inputVodafone = Double.parseDouble(menu_VodafonePay.getText());
            double inputInsta = Double.parseDouble(menu_instaPay.getText());
            double inputDefferred = Double.parseDouble(menu_deferred.getText());
            String clientName = menu_clientName.getSelectionModel().getSelectedItem();
            String clientPhone = menu_clientPhone.getText();

            if (clientName == null || clientName.isEmpty()) {
                al.E_Alert("الرجاء ادخال اسم العميل ورقم الهاتف", AlertType.ERROR);
                return;
            }
            data.invoiceId = 0;

            double remaining = data.Total_invoice_price;

            // Vodafone → Insta → Cash → deffered 
            data.deffered = Math.min(inputDefferred, remaining);
            remaining -= data.deffered;

            data.vodafonePay = Math.min(inputVodafone, remaining);
            remaining -= data.vodafonePay;

            data.instaPay = Math.min(inputInsta, remaining);
            remaining -= data.instaPay;

            data.cashPay = Math.min(inputCash, remaining);
            remaining -= data.cashPay;

            data.totalPayed = data.vodafonePay + data.instaPay + data.cashPay + data.deffered;
            double totalInput = inputCash + inputVodafone + inputInsta + inputDefferred;
            data.change = totalInput - data.totalPayed;

            data.clientName = clientName;
            data.clientphone = clientPhone;

            if (data.totalPayed >= data.Total_invoice_price) {
                // save invoice
                data.invoiceId = services.insertInvoice(
                        data.totalQty,
                        data.Total_invoice_price_peforDis,
                        data.discount,
                        data.Total_invoice_price,
                        data.username,
                        data.cashPay,
                        data.instaPay,
                        data.vodafonePay,
                        data.deffered,
                        data.clientName,
                        data.clientphone
                );

                if (data.invoiceId != 0) {
                    services.updateProductQuantitiesAfterSale(SalesList);
                    func.finalizeInvoice(SalesList, disqaunt_textField, menu_total);
                    services.addCustomerIfNotExists(clientName, clientPhone);

                    al.Confirmation_AlertOptions("الباقي: (" + data.change + ")" + "\n هل تريد طباعة فاتورة؟");
                    Optional<ButtonType> option = al.getAlert().showAndWait();

                    if (option.get().equals(ButtonType.OK)) {
                        func.printReceipt(listData);
                    } else {
                        al.E_Alert("تم إلغاء الطباعة", AlertType.ERROR);
                    }
                    al.E_Alert("تمت عملية البيع بنجاح", AlertType.INFORMATION);
                    menuRemoveBtn();
                } else {
                    al.E_Alert("حدث خطأ اثناء حفظ الفاتورة, الرجاء اعادة عملية البيع", AlertType.ERROR);
                    return;
                }
            } else {
                al.E_Alert("المبلغ غير كافي لتغطية الفاتورة", AlertType.WARNING);
            }

        } catch (NumberFormatException e) {
            al.E_Alert("الرجاء إدخال مبالغ صحيحة!", Alert.AlertType.ERROR);
        }
    }

    public void addToReceipt() {
        productData prodData = menuTableView.getSelectionModel().getSelectedItem();
        if (prodData != null) {
            if (menu_spinner_no == null
                    || menu_spinner_no.getText().trim().isEmpty()
                    || Double.parseDouble(menu_spinner_no.getText().trim()) <= 0.0) {
                al.E_Alert("الرجاء ادخال الكميه", AlertType.ERROR);
            } else if (func.isntNumeric(menu_spinner_no.getText())
                    || func.isntNumeric(menu_realPrice.getText())) {
                al.E_Alert("الرجاء ادخال ارقام صالحه", AlertType.ERROR);
            } else {
                salesShowData();
                func.finalizeInvoice(SalesList, disqaunt_textField, menu_total);
            }
        } else {
            al.E_Alert("حدد المنتج اولا من فضلك", AlertType.ERROR);
        }
    }

    public void payBtn() {
        payMethod();
        deferredInvoiceShowData();
        returnsShowData();
        returnClintList();
    }

    // TO SHOW DATA ON OUR TABLE
    private ObservableList<productData> missingProductListData;

    public void missingProductShowData() {
        missingProductListData = subServices.missingProductDataList();
        missing_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        missing_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        missing_col_avaliableQuantity.setCellValueFactory(new PropertyValueFactory<>("mNumberavailable"));
        missing_col_wholesalePrice.setCellValueFactory(new PropertyValueFactory<>("wholesalePrice"));
        missing_col_realPrice.setCellValueFactory(new PropertyValueFactory<>("realPrice"));
        missing_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        missing_col_soldQuantity.setCellValueFactory(new PropertyValueFactory<>("soldNo"));
        missing_col_warehouse.setCellValueFactory(new PropertyValueFactory<>("wareHouse"));
        missing_col_unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        missing_col_color.setCellValueFactory(new PropertyValueFactory<>("color"));
        missing_col_size.setCellValueFactory(new PropertyValueFactory<>("size"));
        missingProductTableView.setItems(missingProductListData);
        func.setupTableSearchFilter(missingProduct_Search, missingProductTableView, missingProductListData,
                Arrays.asList("id", "model", "mNumberavailable", "wholesalePrice", "realPrice",
                        "type", "soldNo", "wareHouse", "color", "size", "unit"));

    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private ObservableList<Invoices> invoiceList;

    public void showInvoicesData() {
        invoiceList = services.getInvoicesData(0, null, null, null);

        col_invoiceID.setCellValueFactory(new PropertyValueFactory<>("invoice_id"));
        col_totalQty.setCellValueFactory(new PropertyValueFactory<>("total_quantity"));
        col_totalBeforeDiscount.setCellValueFactory(new PropertyValueFactory<>("total_price_before_discount"));
        col_discount.setCellValueFactory(new PropertyValueFactory<>("discount_percentage"));
        col_totalAfterDiscount.setCellValueFactory(new PropertyValueFactory<>("total_price_after_discount"));
        col_cash.setCellValueFactory(new PropertyValueFactory<>("cashPay"));
        col_insta.setCellValueFactory(new PropertyValueFactory<>("instaPay"));
        col_voda.setCellValueFactory(new PropertyValueFactory<>("vodafonePay"));
        col_voda1.setCellValueFactory(new PropertyValueFactory<>("deffered"));
        col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("invoice_date"));
        col_clientName_salesForm.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        col_clientPhone_salesForm.setCellValueFactory(new PropertyValueFactory<>("clientPhone"));

        invoiceTable.setItems(invoiceList);
        func.setupTableSearchFilter(invoice_search, invoiceTable, invoiceList,
                Arrays.asList("invoice_id", "total_quantity", "total_price_before_discount", "discount_percentage", "total_price_after_discount",
                        "cashPay", "deffered", "username", "invoice_date", "clientName", "clientPhone"));

    }

    public void searchByRangeInvoicesAndSales() {
        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = toDatePicker.getValue();

        String searchInvoices = invoice_search.getText().trim();
        services.searchInvoicesFlexible(fromDate, toDate, searchInvoices, invoiceTable);
        String searchSales = salesModelID_search.getText().trim();
        searchSalesFlexible(fromDate, toDate, searchSales);
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ObservableList<Sales> getSalesData() {
        ObservableList<Sales> list = FXCollections.observableArrayList();
        String query;
        String type = itemTypeList_salesForm.getSelectionModel().getSelectedItem();
        if (itemTypeList_salesForm.getSelectionModel().getSelectedItem() != null) {
            query = "SELECT s.sales_id , s.em_username, s.model_id, s.quantity, s.total_price, s.totalWholesalesPrice, s.date, s.invoice_id, s.model_name, s.warehouse, pr.type \n"
                    + " , pr.color, pr.size, pr.unit \n"
                    + "FROM sales s LEFT JOIN product pr ON s.model_id = pr.id WHERE s.quantity > 0 AND pr.type LIKE '" + type + "' ORDER BY s.date DESC";
        } else {
            query = "SELECT s.sales_id , s.em_username, s.model_id, s.quantity, s.total_price, s.totalWholesalesPrice, s.date, s.invoice_id, s.model_name, s.warehouse, pr.type \n"
                    + " , pr.color, pr.size, pr.unit \n"
                    + "FROM sales s LEFT JOIN product pr ON s.model_id = pr.id WHERE s.quantity > 0 ORDER BY s.date DESC";
        }

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Sales sale = new Sales(
                        rs.getInt("sales_id"),
                        rs.getInt("invoice_id"),
                        rs.getInt("model_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("total_price"),
                        rs.getString("date"),
                        rs.getString("em_username"),
                        rs.getString("model_name"),
                        rs.getString("type"),
                        rs.getString("warehouse"),
                        rs.getString("color"),
                        rs.getString("size"),
                        rs.getString("unit")
                );
                list.add(sale);
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء جلب بيانات المبيعات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return list;
    }

    ObservableList<Sales> listofsales;

    public void showSalesDataforSales() {
        listofsales = getSalesData();

        col_invoiceId.setCellValueFactory(new PropertyValueFactory<>("invoice_id"));
        col_modelId.setCellValueFactory(new PropertyValueFactory<>("model_id"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_totalPrice.setCellValueFactory(new PropertyValueFactory<>("total_price"));
        Sales_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_emUsername.setCellValueFactory(new PropertyValueFactory<>("em_username"));
        col_model_sales.setCellValueFactory(new PropertyValueFactory<>("model_name"));
        col_type_sales.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_type_wareHouse.setCellValueFactory(new PropertyValueFactory<>("warehouse"));
        col_size_sales.setCellValueFactory(new PropertyValueFactory<>("size"));
        col_color_sales.setCellValueFactory(new PropertyValueFactory<>("color"));
        col_unit_sales.setCellValueFactory(new PropertyValueFactory<>("unit"));

        salesTable.setItems(listofsales);
        func.setupTableSearchFilter(salesModelID_search, salesTable, listofsales,
                Arrays.asList("invoice_id", "model_id", "quantity", "total_price",
                        "em_username", "model_name", "warehouse", "type",
                        "color", "size", "unit"));
    }

    public void invoicesSelectData() {
        Invoices invoices = invoiceTable.getSelectionModel().getSelectedItem();
        if (invoices == null) {
            return;
        }

        int invoiceId = invoices.getInvoice_id();

        ObservableList<Sales> searchResult = FXCollections.observableArrayList();

//        String sql = "SELECT * FROM sales WHERE invoice_id LIKE ? ORDER BY date DESC";
        String sql = "SELECT s.sales_id , s.em_username, s.model_id, s.quantity, s.total_price, s.totalWholesalesPrice, s.date, s.invoice_id, s.model_name, s.warehouse, pr.type \n"
                + " , pr.color, pr.size, pr.unit \n"
                + "FROM sales s LEFT JOIN product pr ON s.model_id = pr.id WHERE s.invoice_id LIKE ? ORDER BY s.date DESC";

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql)) {

            ps.setInt(1, invoiceId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Sales sale = new Sales(
                        rs.getInt("sales_id"),
                        rs.getInt("invoice_id"),
                        rs.getInt("model_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("total_price"),
                        rs.getString("date"),
                        rs.getString("em_username"),
                        rs.getString("model_name"),
                        rs.getString("type"),
                        rs.getString("warehouse"),
                        rs.getString("color"),
                        rs.getString("size"),
                        rs.getString("unit")
                );
                searchResult.add(sale);
            }

            salesTable.setItems(searchResult);

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء الاتصال بقاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void searchSalesFlexible(LocalDate fromDate, LocalDate toDate, String searchText) {
        ObservableList<Sales> searchResult = FXCollections.observableArrayList();
        String type = itemTypeList_salesForm.getSelectionModel().getSelectedItem();
        // لو المستخدم لم يدخل أي شيء
        if ((fromDate == null && toDate == null) && (searchText == null || searchText.trim().isEmpty())) {
            al.E_Alert("يرجى إدخال تاريخ أو بيانات بحث", AlertType.WARNING);
            return;
        }

//        StringBuilder sql = new StringBuilder("SELECT * FROM sales WHERE 1=1");
        StringBuilder sql = new StringBuilder("SELECT s.sales_id , s.em_username, s.model_id, s.quantity, s.total_price, s.totalWholesalesPrice, s.date, s.invoice_id, s.model_name, s.warehouse, pr.type \n"
                + "FROM sales s LEFT JOIN product pr ON s.model_id = pr.id WHERE 1=1");
        List<Object> parameters = new ArrayList<>();

        if (itemTypeList_salesForm.getSelectionModel().getSelectedItem() != null) {
            sql.append(" AND s.type LIKE ?");
            parameters.add(type);
        }
        if (fromDate != null && toDate != null) {
            sql.append(" AND s.date BETWEEN ? AND ?");
            parameters.add(fromDate.toString());
            parameters.add(toDate.toString());
        } else if (fromDate != null) {
            sql.append(" AND s.date >= ?");
            parameters.add(fromDate.toString());
        } else if (toDate != null) {
            sql.append(" AND s.date <= ?");
            parameters.add(toDate.toString());
        }

        if (searchText != null && !searchText.trim().isEmpty()) {
            sql.append(" AND (s.model_name LIKE ? OR CAST(s.model_id AS TEXT) LIKE ? OR CAST(s.invoice_id AS TEXT) LIKE ?)");
            String pattern = "%" + searchText.trim() + "%";
            parameters.add(pattern);
            parameters.add(pattern);
            parameters.add(pattern);
        }

        sql.append(" ORDER BY s.date DESC");

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql.toString())) {

            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Sales sale = new Sales(
                        rs.getInt("sales_id"),
                        rs.getInt("invoice_id"),
                        rs.getInt("model_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("total_price"),
                        rs.getString("date"),
                        rs.getString("em_username"),
                        rs.getString("model_name"),
                        rs.getString("type"),
                        rs.getString("warehouse")
                );
                searchResult.add(sale);
            }
            salesTable.setItems(searchResult);

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء الاتصال بقاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public ObservableList<Expenses> getExpensesData() {
        ObservableList<Expenses> list = FXCollections.observableArrayList();
        String query = "SELECT expense_id, cash, vodafone, instaPay, reason, em_username, withdrawn_by, date, payStatues, (cash + vodafone + instapay) AS amount FROM expenses ORDER BY date DESC";

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Expenses exp = new Expenses(
                        rs.getInt("expense_id"),
                        rs.getDouble("amount"),
                        rs.getDouble("cash"),
                        rs.getDouble("vodafone"),
                        rs.getDouble("instaPay"),
                        rs.getString("reason"),
                        rs.getString("em_username"),
                        rs.getString("withdrawn_by"),
                        rs.getString("date"),
                        rs.getString("payStatues")
                );
                list.add(exp);
            }

        } catch (SQLException e) {
            al.E_Alert("فشل جلب بيانات المصروفات من قاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return list;
    }

    public void statues_List() {
        ObservableList<String> listData = FXCollections.observableArrayList(EStatues);
        statues_list_expenses.setItems(listData);
    }
    ObservableList<Expenses> expenseList;

    public void showExpensesData() {
        expenseList = getExpensesData();

        col_expenseId.setCellValueFactory(new PropertyValueFactory<>("expense_id"));
        col_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        col_cash_expenses.setCellValueFactory(new PropertyValueFactory<>("cash"));
        col_vodafone.setCellValueFactory(new PropertyValueFactory<>("vodafone"));
        col_instaPay.setCellValueFactory(new PropertyValueFactory<>("instaPay"));
        col_reason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        col_emUsername_expenses.setCellValueFactory(new PropertyValueFactory<>("em_username"));
        col_withdrawnBy.setCellValueFactory(new PropertyValueFactory<>("withdrawn_by"));
        col_date_expenses.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_payStatues_expenses.setCellValueFactory(new PropertyValueFactory<>("payStatues"));

        expensesTable.setItems(expenseList);
    }

    public void clearEeForm() {
        withdrawnBy_field.clear();
        reason_field.clear();
        cash_field.setText("0");
        vodafone_field.setText("0");
        instaPay_field.setText("0");
        searchByName.clear();
        fromDatePickerEx.setValue(null);
        toDatePickerEx.setValue(null);
        statues_list_expenses.getSelectionModel().clearSelection();
        totalOutgoing_expenses.clear();
        totalExpenses_expenses.clear();
        showExpensesData();
    }

    public void insertExpense() {
        String withdrawnBy = withdrawnBy_field.getText().trim();
        String reason = reason_field.getText().trim();
        String cashText = cash_field.getText().trim();
        String vodafoneText = vodafone_field.getText().trim();
        String instaPayText = instaPay_field.getText().trim();
        String statues = statues_list_expenses.getSelectionModel().getSelectedItem();

        if (withdrawnBy.isEmpty() || reason.isEmpty() || statues == null) {
            al.E_Alert("يرجى إدخال جميع البيانات المطلوبة", AlertType.ERROR);
            return;
        }

        if (func.isntNumeric(cashText) || func.isntNumeric(vodafoneText) || func.isntNumeric(instaPayText)) {
            al.E_Alert("ادخل بيانات الدفع عبارة عن أرقام", AlertType.ERROR);
            return;
        }

        double Dcash = cashText.isEmpty() ? 0.0 : Double.parseDouble(cashText);
        double Dvodafone = vodafoneText.isEmpty() ? 0.0 : Double.parseDouble(vodafoneText);
        double DinstaPay = instaPayText.isEmpty() ? 0.0 : Double.parseDouble(instaPayText);

        if (Dcash < 0 || Dvodafone < 0 || DinstaPay < 0) {
            al.E_Alert("لا يمكن إدخال مبالغ سالبة", AlertType.ERROR);
            return;
        }

        if (Dcash > data.TotalCashPay || Dvodafone > data.TotalVodafoneCash || DinstaPay > data.TotalInstaPay) {
            al.E_Alert("المبلغ المسحوب أكبر من الرصيد المتوفر", AlertType.ERROR);
            return;
        }

        Expenses expense = new Expenses(Dcash, Dvodafone, DinstaPay, reason, data.username, withdrawnBy, statues);

        String sql = "INSERT INTO expenses (cash, vodafone, instapay, reason, em_username, withdrawn_by, payStatues) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql)) {

            ps.setDouble(1, expense.getCash());
            ps.setDouble(2, expense.getVodafone());
            ps.setDouble(3, expense.getInstaPay());
            ps.setString(4, expense.getReason());
            ps.setString(5, expense.getEm_username());
            ps.setString(6, expense.getWithdrawn_by());
            ps.setString(7, expense.getPayStatues());

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                al.E_Alert("تمت إضافة المصروف بنجاح", AlertType.INFORMATION);
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء إضافة المصروف", AlertType.ERROR);
            e.printStackTrace();
        }

        clearEeForm();
        showExpensesData();
    }

    public void getExpensesSelection() {
        expensesTable.setOnMouseClicked(event -> {
            Expenses selectedExpense = expensesTable.getSelectionModel().getSelectedItem();
            if (selectedExpense != null) {
                withdrawnBy_field.setText(selectedExpense.getWithdrawn_by());
                reason_field.setText(selectedExpense.getReason());
                cash_field.setText(String.valueOf(selectedExpense.getCash()));
                vodafone_field.setText(String.valueOf(selectedExpense.getVodafone()));
                instaPay_field.setText(String.valueOf(selectedExpense.getInstaPay()));
                statues_list_expenses.getSelectionModel().select(selectedExpense.getPayStatues());
            }
        });
    }

    public void payExpense() {
        Expenses selectedExpense = expensesTable.getSelectionModel().getSelectedItem();
        int id = selectedExpense.getExpense_id();

        if (selectedExpense == null) {
            al.E_Alert("اختر الصف الذي تريد سداد المصروف له", AlertType.WARNING);
            return;
        }

        String withdrawnBy = withdrawnBy_field.getText();
        String reason = reason_field.getText();
        String cashText = cash_field.getText().trim();
        String vodafoneText = vodafone_field.getText().trim();
        String instaPayText = instaPay_field.getText().trim();
        String statues = statues_list_expenses.getSelectionModel().getSelectedItem();

        if (func.isntNumeric(cashText) || func.isntNumeric(vodafoneText) || func.isntNumeric(instaPayText)) {
            al.E_Alert("ادخل بيانات الدفع عبارة عن أرقام", AlertType.ERROR);
            return;
        }

        double Dcash = Double.parseDouble(cashText);
        double Dvodafone = Double.parseDouble(vodafoneText);
        double DinstaPay = Double.parseDouble(instaPayText);
        double Dtotal = Dcash + Dvodafone + DinstaPay;

        if (withdrawnBy == null || withdrawnBy.isEmpty() || reason.isEmpty() || statues == null) {
            al.E_Alert("ادخل كل البيانات من فضلك", AlertType.ERROR);
            return;
        }

        if (Dcash < 0 || Dvodafone < 0 || DinstaPay < 0) {
            al.E_Alert("ادخل المبالغ اكبر من الصفر", AlertType.ERROR);
            return;
        }

        String sql = "UPDATE expenses SET cash = ?, vodafone = ?, instapay = ?, "
                + "reason = ?, payStatues = ?, withdrawn_by = ? "
                + "WHERE expense_id = ?";

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql)) {

            ps.setDouble(1, Dcash);
            ps.setDouble(2, Dvodafone);
            ps.setDouble(3, DinstaPay);
            ps.setString(4, reason);
            ps.setString(5, statues);
            ps.setString(6, withdrawnBy);
            ps.setInt(7, id);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                al.E_Alert("تم تعديل المصروف بنجاح.", AlertType.INFORMATION);
            } else {
                al.E_Alert("لم يتم العثور على المصروف بالتحديد المطلوب!", AlertType.WARNING);
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء تعديل المصروف", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        clearEeForm();
        showExpensesData();
    }
    ObservableList<Expenses> searchExpenseResult;

    public void searchExpenses() {
        searchExpenseResult = FXCollections.observableArrayList();
        LocalDate fromDate = fromDatePickerEx.getValue();
        LocalDate toDate = toDatePickerEx.getValue();
        String searchText = searchByName.getText().trim();

        StringBuilder sql = new StringBuilder("SELECT expense_id, cash, vodafone, instaPay, reason, em_username, withdrawn_by, date, payStatues, (cash + vodafone + instapay) AS amount FROM expenses WHERE 1=1");
        StringBuilder sumSql = new StringBuilder("SELECT payStatues, SUM(cash + vodafone + instaPay) AS totalAmount FROM expenses WHERE 1=1");
        List<Object> parameters = new ArrayList<>();

        if (!searchText.isEmpty()) {
            sql.append(" AND withdrawn_by LIKE ? OR expense_id LIKE ? OR payStatues LIKE ? OR reason LIKE ? OR em_username LIKE ?");
            sumSql.append(" AND withdrawn_by LIKE ? OR expense_id LIKE ? OR payStatues LIKE ? OR reason LIKE ? OR em_username LIKE ?");
            parameters.add("%" + searchText + "%");
            parameters.add("%" + searchText + "%");
            parameters.add("%" + searchText + "%");
            parameters.add("%" + searchText + "%");
            parameters.add("%" + searchText + "%");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (fromDate != null && toDate != null) {
            sql.append(" AND date BETWEEN ? AND ?");
            sumSql.append(" AND date BETWEEN ? AND ?");
            parameters.add(fromDate.format(formatter));
            parameters.add(toDate.format(formatter));
        } else if (fromDate != null) {
            sql.append(" AND date >= ?");
            sumSql.append(" AND date >= ?");
            parameters.add(fromDate.format(formatter));
        } else if (toDate != null) {
            sql.append(" AND date <= ?");
            sumSql.append(" AND date <= ?");
            parameters.add(toDate.format(formatter));
        }

        sql.append(" ORDER BY date DESC");
        sumSql.append(" GROUP BY payStatues");

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql.toString());  PreparedStatement sumPs = connect.prepareStatement(sumSql.toString())) {

            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
                sumPs.setObject(i + 1, parameters.get(i));
            }

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Expenses expense = new Expenses(
                            rs.getInt("expense_id"),
                            rs.getDouble("amount"),
                            rs.getDouble("cash"),
                            rs.getDouble("vodafone"),
                            rs.getDouble("instaPay"),
                            rs.getString("reason"),
                            rs.getString("em_username"),
                            rs.getString("withdrawn_by"),
                            rs.getString("date"),
                            rs.getString("payStatues")
                    );
                    searchExpenseResult.add(expense);
                }
            }
            expensesTable.setItems(searchExpenseResult);

            // حساب الإجماليات
            try ( ResultSet sumRs = sumPs.executeQuery()) {
                double totalExpenses = 0.0;
                double totalOutflows = 0.0;

                while (sumRs.next()) {
                    String status = sumRs.getString("payStatues");
                    double sum = sumRs.getDouble("totalAmount");

                    if ("المصروفات".equals(status)) {
                        totalExpenses = sum;
                        data.TotalExpenseforPrint = sum;
                    } else if ("الخوارج".equals(status)) {
                        totalOutflows = sum;
                        data.TotalOutgoingforPrint = sum;
                    }
                }

                totalExpenses_expenses.setText(String.format("%.2f", totalExpenses));
                totalOutgoing_expenses.setText(String.format("%.2f", totalOutflows));
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء البحث أو حساب المجموع", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void suppliersTab() {
//        loadSuppliers();
//        clearSupplierFields();
    }

    public void suppliersAddbtn() {
        String name = supplierName_purchase.getText().trim();
        String phone = phone_purchase.getText().trim();
        String address = supplierAddress_purchase.getText().trim();
        String remarks = remark_purchase.getText().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            al.E_Alert("يجب ادخال اسم وهاتف المورد", Alert.AlertType.ERROR);
            return;
        }

        String checkSql = "SELECT COUNT(*) FROM suppliers WHERE name = ?";
        String insertSql = "INSERT INTO suppliers (name, phone, address, remarks) VALUES (?, ?, ?, ?)";

        try ( Connection conn = database.getConnection();  PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, name);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                al.E_Alert("هذا المورد موجود بالفعل!", Alert.AlertType.WARNING);
                return;
            }

            try ( PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, name);
                insertStmt.setString(2, phone);
                insertStmt.setString(3, address);
                insertStmt.setString(4, remarks);
                insertStmt.executeUpdate();
                al.E_Alert("تم اضافة المورد بنجاح", Alert.AlertType.INFORMATION);
                loadSuppliers(); // reload table
                clearSupplierFields();
            }

        } catch (SQLException e) {
            al.E_Alert("فشل اضافة مورد جديد:\n" + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void suppliersSelected() {
        Supplier selected = suppliersTable.getSelectionModel().getSelectedItem();
        supplierName_purchase.setText(selected.getName());
        phone_purchase.setText(selected.getPhone());
        supplierAddress_purchase.setText(selected.getAddress());
        remark_purchase.setText(selected.getRemarks());
    }

    public void suppliersUpdateBtn() {
        Supplier selected = suppliersTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            al.E_Alert("للتعديل يجب اختيار مورد اولا من الجدول", Alert.AlertType.ERROR);
            return;
        }

        String sql = "UPDATE suppliers SET name=?, phone=?, address=?, remarks=? WHERE id=?";

        try ( Connection conn = database.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, supplierName_purchase.getText());
            pstmt.setString(2, phone_purchase.getText());
            pstmt.setString(3, supplierAddress_purchase.getText());
            pstmt.setString(4, remark_purchase.getText());
            pstmt.setInt(5, selected.getId());
            pstmt.executeUpdate();
            al.E_Alert("تم تعديل بيانات المورد بنجاح", Alert.AlertType.INFORMATION);
            loadSuppliers();
            clearSupplierFields();
        } catch (SQLException e) {
            al.E_Alert("فشل تعديل بيانات المورد" + e, Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void suppliersDeleteBtn() {
        Supplier selected = suppliersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            al.E_Alert("للحذف يجب اختيار مورد اولا من الجدول", Alert.AlertType.ERROR);
            return;
        }

        String sql = "DELETE FROM suppliers WHERE id=?";

        try ( Connection conn = database.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, selected.getId());
            pstmt.executeUpdate();
            al.E_Alert("تم حذف المورد بنجاح", Alert.AlertType.INFORMATION);
            loadSuppliers();
            clearSupplierFields();
        } catch (SQLException e) {
            al.E_Alert("فشل حذف المورد" + e, Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void suppliersClearBtn() {
        clearSupplierFields();
    }

    private void clearSupplierFields() {
        supplierName_purchase.clear();
        phone_purchase.clear();
        supplierAddress_purchase.clear();
        remark_purchase.clear();
        suppliersTable.getSelectionModel().clearSelection();
        search_purchase.clear();
        loadSuppliers();
    }

    private ObservableList<Supplier> supplierListData;

    public void loadSuppliers() {
        supplierListData = subServices.loadSuppliersList();

        suppliers_col_remarks_purchase.setCellValueFactory(new PropertyValueFactory<>("remarks"));
        suppliers_col_suppliersAddress_purchase.setCellValueFactory(new PropertyValueFactory<>("address"));
        suppliers_col_suppliersPhone_purchase.setCellValueFactory(new PropertyValueFactory<>("phone"));
        suppliers_col_suppliersName_purchase.setCellValueFactory(new PropertyValueFactory<>("name"));
        suppliers_col_suppliersID_purchase.setCellValueFactory(new PropertyValueFactory<>("id"));
        suppliersTable.setItems(supplierListData);
        func.setupTableSearchFilter(search_purchase, suppliersTable, supplierListData,
                Arrays.asList("id", "remarks", "phone", "address", "name"));

    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void loadPurchaseInvoiceTab() {
        loadSuppliersIntoComboBox();
        loadPurchaseInvoices();
        clearInvoiceFields();
        backBtn_toPurchaseForm();
    }

    public void loadSuppliersIntoComboBox() {
        String sql = "SELECT name FROM suppliers";
        try ( Connection conn = database.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql);  ResultSet rs = pstmt.executeQuery()) {
            SuppliersListInvoices.getItems().clear();
            SuppliersListAccountStatements.getItems().clear();
            while (rs.next()) {
                SuppliersListInvoices.getItems().add(rs.getString("name"));
                SuppliersListAccountStatements.getItems().add(rs.getString("name"));
            }
        } catch (SQLException e) {
            al.E_Alert("فشل تحميل الموردين:\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void addPurchaseInvoice() {
        String supplier = SuppliersListInvoices.getValue();
        String invoiceOutID = InvoicesOutID.getText().trim();
        String invoiceDate = String.valueOf(fromDatePickerInvoicesInsert.getValue());
        String remarks = remark_invoices.getText();

        if (supplier == null || invoiceOutID.isEmpty() || fromDatePickerInvoicesInsert.getValue() == null) {
            al.E_Alert("يرجى إدخال اسم المورد ورقم الفاتورة الخارجي و تاريخ الفاتورة", Alert.AlertType.ERROR);
            return;
        }

        if (func.isntNumeric(InvoicesOutID.getText()) || InvoicesOutID.getText().trim().isEmpty()
                || Double.parseDouble(InvoicesOutID.getText().trim()) <= 0.0) {
            al.E_Alert("الرجاء ادخال ارقام صالحه فى الرقم الخارجى للفاتورة", Alert.AlertType.ERROR);
            return;
        }
        double total, cash, deferred;

        try {
            total = Double.parseDouble(totalPrice_purchase.getText());
            cash = Double.parseDouble(cash_purchase.getText());
            deferred = Double.parseDouble(deferredInvoices_purchase.getText());
        } catch (NumberFormatException e) {
            al.E_Alert("يرجى إدخال طرق الدفع والإجمالي كأرقام", Alert.AlertType.ERROR);
            return;
        }

        if ((cash + deferred) != total || (cash < 0 || deferred < 0 || total <= 0)) {
            al.E_Alert("المجموع الكلي لطرق الدفع لا يساوي إجمالي الفاتورة", Alert.AlertType.ERROR);
            return;
        }

        String checkSql = "SELECT COUNT(*) FROM purchase_invoices WHERE invoice_out_id = ?";
        String insertSql = "INSERT INTO purchase_invoices (supplier_name, invoice_out_id, invoice_date, cash, deferred, remarks) VALUES (?, ?, ?, ?, ?, ?)";

        try ( Connection conn = database.getConnection();  PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, invoiceOutID);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                al.E_Alert("رقم الفاتورة الخارجي موجود بالفعل", Alert.AlertType.WARNING);
                return;
            }

            try ( PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, supplier);
                insertStmt.setString(2, invoiceOutID);
                insertStmt.setString(3, invoiceDate);;
                insertStmt.setDouble(4, cash);
                insertStmt.setDouble(5, deferred);
                insertStmt.setString(6, remarks);
                insertStmt.executeUpdate();

                al.E_Alert("تم إضافة الفاتورة بنجاح", Alert.AlertType.INFORMATION);
                clearInvoiceFields();
                loadPurchaseInvoices();
            }

        } catch (SQLException e) {
            al.E_Alert("فشل في إضافة الفاتورة:\n" + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void updatePurchaseInvoice() {
        InvoiceModel selected = invoicesSuppliersTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            al.E_Alert("للتعديل اخترفاتورة من الجدول اولا", Alert.AlertType.WARNING);
            return;
        }

        if (func.isntNumeric(InvoicesOutID.getText()) || InvoicesOutID.getText().trim().isEmpty()
                || Double.parseDouble(InvoicesOutID.getText().trim()) <= 0.0) {
            al.E_Alert("الرجاء ادخال ارقام صالحه فى الرقم الخارجى للفاتورة", Alert.AlertType.ERROR);
            return;
        }
        String supplier = SuppliersListInvoices.getValue();
        String invoiceOutID = InvoicesOutID.getText().trim();
        String invoiceDate = String.valueOf(fromDatePickerInvoicesInsert.getValue());
        String remarks = remark_invoices.getText();

        double total, cash, deferred;

        try {
            total = Double.parseDouble(totalPrice_purchase.getText());
            cash = Double.parseDouble(cash_purchase.getText());
            deferred = Double.parseDouble(deferredInvoices_purchase.getText());
        } catch (NumberFormatException e) {
            al.E_Alert("يرجى إدخال طرق الدفع والإجمالي كأرقام", Alert.AlertType.ERROR);
            return;
        }

        if ((cash + deferred) != total || (cash < 0 || deferred < 0 || total <= 0)) {
            al.E_Alert("المجموع الكلي لطرق الدفع لا يساوي إجمالي الفاتورة", Alert.AlertType.ERROR);
            return;
        }

        String updateSql = "UPDATE purchase_invoices SET supplier_name=?, invoice_out_id=?, invoice_date=?, cash=?, deferred=?, remarks=? WHERE id=?";

        try ( Connection conn = database.getConnection();  PreparedStatement pstmt = conn.prepareStatement(updateSql)) {

            pstmt.setString(1, supplier);
            pstmt.setString(2, invoiceOutID);
            pstmt.setString(3, invoiceDate);
            pstmt.setDouble(4, cash);
            pstmt.setDouble(5, deferred);
            pstmt.setString(6, remarks);
            pstmt.setInt(7, selected.getId()); // من الصف المحدد
            pstmt.executeUpdate();
            al.E_Alert("تم تعديل الفاتورة بنجاح", Alert.AlertType.INFORMATION);
            clearInvoiceFields();
            loadPurchaseInvoices();

        } catch (SQLException e) {
            al.E_Alert("خطأ أثناء تعديل الفاتورة:\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void deletePurchaseInvoice() {
        InvoiceModel selected = invoicesSuppliersTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            al.E_Alert("للتعديل اختر فاتورة من الجدول اولا", Alert.AlertType.WARNING);
            return;
        }

        al.Confirmation_AlertOptions("هل انت متأكد من الحذف ؟");
        Optional<ButtonType> option = al.getAlert().showAndWait();

        if (option.isPresent() && option.get().equals(ButtonType.OK)) {
            subServices.deletePurchaseInvoice(selected.getId());
            inventoryClearBtn();
        } else {
            al.E_Alert("تم االغاء الحذف", AlertType.ERROR);
        }
    }

    public void clearInvoiceFields() {
        SuppliersListInvoices.setValue(null);
        InvoicesOutID.clear();
        fromDatePickerInvoicesInsert.setValue(null);
        totalPrice_purchase.setText("0");
        cash_purchase.setText("0");
        deferredInvoices_purchase.setText("0");
        remark_invoices.clear();
        toDatePickerInvoices.setValue(null);
        fromDatePickerInvoices.setValue(null);
        search_purchaseInvoices.clear();
        loadSuppliersIntoComboBox();
        loadPurchaseInvoices();
    }

    public void searchInvoicesSuppliersByDateRange() {
        ObservableList<InvoiceModel> invoiceModels = subServices.searchPurchBYDate(fromDatePickerInvoices, toDatePickerInvoices);
        invoices_col_invoicesID_purchase.setCellValueFactory(new PropertyValueFactory<>("id"));
        invoices_col_suppliers_purchase.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        invoices_col_invoicesOutID_purchase.setCellValueFactory(new PropertyValueFactory<>("invoiceOutId"));
        invoices_col_date_purchase.setCellValueFactory(new PropertyValueFactory<>("invoiceDate"));
        invoices_col_totalPrice_purchase.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        invoices_col_cash_purchase.setCellValueFactory(new PropertyValueFactory<>("cash"));
        invoices_col_deferred_purchase.setCellValueFactory(new PropertyValueFactory<>("deferred"));
        invoices_col_remarks_purchase.setCellValueFactory(new PropertyValueFactory<>("remarks"));

        invoicesSuppliersTable.setItems(invoiceModels);
        func.setupTableSearchFilter(search_purchaseInvoices, invoicesSuppliersTable, invoiceModels,
                Arrays.asList("id", "supplierName", "invoiceOutId", "invoiceDate",
                        "totalPrice", "cash", "deferred", "remarks"));
    }

    public void handleInvoiceTableClick() {
        InvoiceModel selected = invoicesSuppliersTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            SuppliersListInvoices.setValue(selected.getSupplierName());
            InvoicesOutID.setText(selected.getInvoiceOutId());
            fromDatePickerInvoicesInsert.setValue(LocalDate.parse(selected.getInvoiceDate()));
            remark_invoices.setText(selected.getRemarks());
            totalPrice_purchase.setText(String.valueOf(selected.getTotalPrice()));
            cash_purchase.setText(String.valueOf(selected.getCash()));
            deferredInvoices_purchase.setText(String.valueOf(selected.getDeferred()));
        }
    }

    public void loadPurchaseInvoices() {
        ObservableList<InvoiceModel> invoicesList = subServices.loadPurchaseInvoicesList();
        invoices_col_invoicesID_purchase.setCellValueFactory(new PropertyValueFactory<>("id"));
        invoices_col_suppliers_purchase.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        invoices_col_invoicesOutID_purchase.setCellValueFactory(new PropertyValueFactory<>("invoiceOutId"));
        invoices_col_date_purchase.setCellValueFactory(new PropertyValueFactory<>("invoiceDate"));
        invoices_col_totalPrice_purchase.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        invoices_col_cash_purchase.setCellValueFactory(new PropertyValueFactory<>("cash"));
        invoices_col_deferred_purchase.setCellValueFactory(new PropertyValueFactory<>("deferred"));
        invoices_col_remarks_purchase.setCellValueFactory(new PropertyValueFactory<>("remarks"));

        invoicesSuppliersTable.setItems(invoicesList);
        func.setupTableSearchFilter(search_purchaseInvoices, invoicesSuppliersTable, invoicesList,
                Arrays.asList("id", "supplierName", "invoiceOutId",
                        "totalPrice", "cash", "deferred", "remarks"));
    }

    public void loadProfitByTypeReport() {
        ObservableList<ProfitReport> reportList = FXCollections.observableArrayList();

        LocalDate fromDate = fromDatePickerCapital.getValue();
        LocalDate toDate = toDatePickerCapital.getValue();

        StringBuilder sql = new StringBuilder("SELECT pr.type, "
                + "SUM(s.quantity) AS totalQuantity, "
                + "SUM(s.totalWholesalesPrice) AS totalWholesalesPrice, "
                + "SUM(s.total_price) AS totalPrice, "
                + "(SUM(s.total_price) - SUM(s.totalWholesalesPrice)) AS gain "
                + "FROM sales s "
                + "LEFT JOIN product pr ON s.model_id = pr.id "
                + "WHERE 1=1 ");

        List<Object> params = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (fromDate != null) {
            sql.append("AND s.date >= ? ");
            params.add(fromDate.format(formatter));
        }
        if (toDate != null) {
            sql.append("AND s.date <= ? ");
            params.add(toDate.format(formatter));
        }

        sql.append("GROUP BY pr.type ORDER BY gain DESC");

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setString(i + 1, params.get(i).toString());
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProfitReport report = new ProfitReport(
                        rs.getString("type"),
                        rs.getInt("totalQuantity"),
                        rs.getDouble("totalWholesalesPrice"),
                        rs.getDouble("totalPrice"),
                        rs.getDouble("gain")
                );
                reportList.add(report);
            }

            // ربط البيانات بالجدول في واجهة المستخدم
            type_col.setCellValueFactory(new PropertyValueFactory<>("type"));
            quantity_col.setCellValueFactory(new PropertyValueFactory<>("totalQuantity"));
            wholesales_col.setCellValueFactory(new PropertyValueFactory<>("totalWholesalesPrice"));
            price_col.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
            gain_col.setCellValueFactory(new PropertyValueFactory<>("gain"));

            profitReportTable.setItems(reportList);

        } catch (SQLException e) {
            e.printStackTrace();
            al.E_Alert("حدث خطأ أثناء تحميل تقرير الأرباح", Alert.AlertType.ERROR);
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void unavailableProduct() {
        String query = "SELECT COUNT(*) AS unAvailable_model FROM product WHERE mNumberavailable = 0";

        try ( Connection conn = database.getConnection();  PreparedStatement ps = conn.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                data.UnavailbleProductCount = rs.getInt("unAvailable_model");
                UnavailbleProductCount_label.setText(String.valueOf(data.UnavailbleProductCount));
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء جلب عدد المنتجات غير المتوفرة", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void AvailableProducts() {
        String query = "SELECT SUM(mNumberavailable) AS all_AvailableProduct FROM product WHERE mNumberavailable > 0";

        try ( Connection conn = database.getConnection();  PreparedStatement ps = conn.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                data.AvailbleProductCount = rs.getInt("all_AvailableProduct");
                AvailbleProductCount_label.setText(String.valueOf(data.AvailbleProductCount));
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء جلب عدد المنتجات المتوفرة", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

// Calculates total expenses with payStatues = 'المصروفات'
    public void totalExpenses() {
        String query = "SELECT SUM(cash + vodafone + instaPay) AS total_expenses FROM expenses WHERE payStatues = 'المصروفات'";

        try (
                 Connection connect = database.getConnection();  PreparedStatement stmt = connect.prepareStatement(query);  ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                data.TotalExpense = rs.getDouble("total_expenses");

                tota_expenses_field.setText(String.format("%.2f $", data.TotalExpense));
            }
        } catch (Exception e) {
            al.E_Alert("حدث خطأ أثناء حساب المصروفات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void totalWholePrice() {
        String sql = "SELECT COALESCE(SUM(totalWholesalesPrice), 0) AS total_wholesale FROM sales WHERE quantity > 0";

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                data.TotalWholepriceinCapital = rs.getDouble("total_wholesale");
                total_sales_wholesale_price.setText(String.format("%.2f $", data.TotalWholepriceinCapital));
            }
        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء حساب إجمالي سعر الجملة", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void totalRealPrice() {
        String sql = "SELECT COALESCE(SUM(total_price_after_discount), 0) AS total_realPrice FROM invoices";

        try (
                 Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                data.TotalRealPriceinCapital = rs.getDouble("total_realPrice");
                total_sales_Real_price.setText(String.format("%.2f $", data.TotalRealPriceinCapital));
            }
        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء حساب إجمالي السعر الفعلي", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void totalAvailableProduct_wholePrice() {
        String sql = "SELECT COALESCE(SUM(wholesaleprice * mNumberavailable), 0) AS total_wholePrice FROM product WHERE mNumberavailable > 0";

        try (
                 Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                data.TotalAvailabelWholeprice = rs.getDouble("total_wholePrice");
                totalAvailableWholeprice_field.setText(String.format("%.2f $", data.TotalAvailabelWholeprice));
            }
        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء حساب إجمالي سعر الجملة للمنتجات المتوفرة", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void totalAvailableProduct_realPrice() {
        String sql = "SELECT COALESCE(SUM(realprice * mNumberavailable), 0) AS total_realPrice FROM product WHERE mNumberavailable > 0";

        try (
                 Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                data.TotalAvailabelRealprice = rs.getDouble("total_realPrice");
                totalAvailableRealprice_field.setText(String.format("%.2f $", data.TotalAvailabelRealprice));
            }
        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء حساب إجمالي السعر الحقيقي للمنتجات المتوفرة", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void totalReturnMoney() {
        String sql = "SELECT COALESCE(SUM(price_of_return), 0) AS total_returnPrice FROM returns";

        try (
                 Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                data.TotalRetrunPrice = rs.getDouble("total_returnPrice");
            }
        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء حساب إجمالي قيمة المرتجعات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void totalReturnMoney(LocalDate fromDate, LocalDate toDate) {
        if (fromDate == null || toDate == null) {
            al.E_Alert("يرجى تحديد كل من تاريخ البداية والنهاية", Alert.AlertType.ERROR);
            return;
        }

        String sql = "SELECT COALESCE(SUM(price_of_return), 0) AS total_returnPrice FROM returns WHERE return_date BETWEEN '" + fromDate + "' AND '" + toDate + "'";

        try (
                 Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql)) {
//            ps.setDate(1, java.sql.Date.valueOf(fromDate));
//            ps.setDate(2, java.sql.Date.valueOf(toDate));

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data.TotalRetrunPrice = rs.getDouble("total_returnPrice");
                }
            }
        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء حساب المرتجعات خلال الفترة المحددة", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void totalExpenses(LocalDate fromDate, LocalDate toDate) {
        if (fromDate == null || toDate == null) {
            al.E_Alert("يرجى تحديد كل من تاريخ البداية والنهاية", Alert.AlertType.ERROR);
            return;
        }

        String totalExpensesQuery = "SELECT SUM(cash + vodafone + instaPay) AS total_expenses FROM expenses WHERE date BETWEEN '" + fromDate + "' AND '" + toDate + "' AND payStatues = 'المصروفات'";

        try ( Connection connect = database.getConnection();  PreparedStatement expensePs = connect.prepareStatement(totalExpensesQuery)) {

            try ( ResultSet expenseRs = expensePs.executeQuery()) {
                if (expenseRs.next()) {
                    data.TotalExpense = expenseRs.getDouble("total_expenses");
                    tota_expenses_field.setText(String.format("%.2f $", data.TotalExpense));
                }
            }
        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء حساب المصروفات للفترة المحددة", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void totalOutgoing(LocalDate fromDate, LocalDate toDate) {
        String total_outgoing_Select = "SELECT SUM(cash + vodafone + instaPay) AS total_expenses FROM expenses WHERE date BETWEEN '" + fromDate + "' AND '" + toDate + "' AND payStatues = 'الخوارج'";
        try ( Connection connect = database.getConnection();  PreparedStatement outgoingPs = connect.prepareStatement(total_outgoing_Select)) {

            ResultSet outgoingRs = outgoingPs.executeQuery();
            if (outgoingRs.next()) {
                data.TotalOutgoing = outgoingRs.getDouble("total_expenses");
                totalOutgoing_capital.setText(String.format("%.2f $", data.TotalOutgoing));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void totalWholePrice(LocalDate fromDate, LocalDate toDate) {
        if (fromDate == null || toDate == null) {
            al.E_Alert("يرجى تحديد كل من تاريخ البداية والنهاية", Alert.AlertType.ERROR);
            return;
        }

        String sql = "SELECT COALESCE(SUM(totalWholesalesPrice), 0) AS total_wholesale FROM sales WHERE quantity > 0 AND date BETWEEN '" + fromDate + "' AND '" + toDate + "'";

        try ( Connection connect = database.getConnection();  PreparedStatement wholePricePs = connect.prepareStatement(sql)) {

            try ( ResultSet wholePriceRs = wholePricePs.executeQuery()) {
                if (wholePriceRs.next()) {
                    data.TotalWholepriceinCapital = wholePriceRs.getDouble("total_wholesale");
                    total_sales_wholesale_price.setText(String.format("%.2f $", data.TotalWholepriceinCapital));
                }
            }
        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء حساب إجمالي سعر الجملة للمبيعات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void totalRealPrice(LocalDate fromDate, LocalDate toDate) {
        if (fromDate == null || toDate == null) {
            al.E_Alert("يرجى تحديد كل من تاريخ البداية والنهاية", Alert.AlertType.ERROR);
            return;
        }

        String sql = "SELECT COALESCE(SUM(total_price_after_discount), 0) AS total_realPrice "
                + "FROM invoices WHERE invoice_date BETWEEN '" + fromDate + "' AND '" + toDate + "'";

        try ( Connection connect = database.getConnection();  PreparedStatement realPricePs = connect.prepareStatement(sql)) {

            try ( ResultSet realPriceRs = realPricePs.executeQuery()) {
                if (realPriceRs.next()) {
                    data.TotalRealPriceinCapital = realPriceRs.getDouble("total_realPrice");
                    total_sales_Real_price.setText(String.format("%.2f $", data.TotalRealPriceinCapital));
                }
            }
        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء حساب إجمالي سعر البيع بعد الخصم", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void totalRealPriceCurrentMonth() {
        LocalDate fromDate = LocalDate.now().withDayOfMonth(1);
        LocalDate toDate = fromDate.withDayOfMonth(fromDate.lengthOfMonth());

        String sql = "SELECT COALESCE(SUM(total_price_after_discount), 0) AS total_realPrice "
                + "FROM invoices WHERE invoice_date BETWEEN '" + fromDate + "' AND '" + toDate + "'";

        try ( Connection connect = database.getConnection();  PreparedStatement realPricePs = connect.prepareStatement(sql)) {

            try ( ResultSet realPriceRs = realPricePs.executeQuery()) {
                if (realPriceRs.next()) {
                    data.TotalRealPriceinCapital = realPriceRs.getDouble("total_realPrice");
                    totalPriceCurrentMonth_field.setText(String.format("%.2f $", data.TotalRealPriceinCapital));
                }
            }
        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء حساب إجمالي مبيعات الشهر الحالي", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void totalRealPriceToday() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        String sql = "SELECT COALESCE(SUM(total_price_after_discount), 0) AS total_realPrice "
                + "FROM invoices WHERE invoice_date >= '" + today + "' AND invoice_date < '" + tomorrow + "'";

        try ( Connection connect = database.getConnection();  PreparedStatement realPricePs = connect.prepareStatement(sql)) {

            try ( ResultSet realPriceRs = realPricePs.executeQuery()) {
                if (realPriceRs.next()) {
                    data.totalRealPriceToday = realPriceRs.getDouble("total_realPrice");
                    today_salaries.setText(String.format("%.2f $", data.totalRealPriceToday));
                }
            }
        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء حساب مبيعات اليوم", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    // Calculates total outgoing with payStatues = 'الخوارج'
    public void totalOutgoing() {
        String query = "SELECT SUM(cash + vodafone + instaPay) AS total_expenses FROM expenses WHERE payStatues = 'الخوارج'";

        try (
                 Connection connect = database.getConnection();  PreparedStatement stmt = connect.prepareStatement(query);  ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                data.TotalOutgoing = rs.getDouble("total_expenses");
                totalOutgoing_capital.setText(String.format("%.2f $", data.TotalOutgoing));
            }
        } catch (Exception e) {
            al.E_Alert("حدث خطأ أثناء حساب الخوارج", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void showCapital() {
        double cash = 0;

        totalWholePrice();
        totalRealPrice();
        totalReturnMoney();

        data.NetProfit_withoutDiscount = data.TotalRealPriceinCapital - data.TotalWholepriceinCapital;
        profit_without_deducting_expenses.setText(String.format("%.2f $", data.NetProfit_withoutDiscount));

        totalExpenses();
        totalOutgoing();

        func.totalCash(total_Cash_field);
        func.totalVodafoneCash(total_vodafoneCash_field);
        func.totalInstaPay(total_instaPay_field);

        data.NetProfit = data.NetProfit_withoutDiscount - data.TotalExpense;
        NetProfit_field.setText(String.format("%.2f $", data.NetProfit));

        data.NetProfit_withOutgoing = data.NetProfit - data.TotalOutgoing;
        NetProfit_field_withOutgoing.setText(String.format("%.2f $", data.NetProfit_withOutgoing));

        totalAvailableProduct_wholePrice();
        totalAvailableProduct_realPrice();

        data.MoneySafe = data.TotalCashPay + data.TotalVodafoneCash + data.TotalInstaPay;

        Money_safe.setText(String.format("%.2f $", data.MoneySafe));

        unavailableProduct();
        AvailableProducts();

        totalRealPriceCurrentMonth();
        loadProfitByTypeReport();

        fromDatePickerCapital.setValue(null);
        toDatePickerCapital.setValue(null);
    }

    public void SearchByDateRangeForCapital() {
        LocalDate fromDateC = fromDatePickerCapital.getValue();
        LocalDate toDateC = toDatePickerCapital.getValue();
        if (fromDateC == null || toDateC == null) {
            al.E_Alert("يرجى تحديد كل من تاريخ البداية والنهاية", AlertType.ERROR);
        } else {
            totalWholePrice(fromDateC, toDateC);
            totalRealPrice(fromDateC, toDateC);

            totalReturnMoney(fromDateC, toDateC);
            data.NetProfit_withoutDiscount = data.TotalRealPriceinCapital - data.TotalWholepriceinCapital;
            profit_without_deducting_expenses.setText(String.format("%.2f $", data.NetProfit_withoutDiscount));
            totalExpenses(fromDateC, toDateC);
            totalOutgoing(fromDateC, toDateC);

            func.totalCash(fromDateC, toDateC, total_Cash_field);
            func.totalVodafoneCash(fromDateC, toDateC, total_vodafoneCash_field);
            func.totalInstaPay(fromDateC, toDateC, total_instaPay_field);

            data.NetProfit = data.NetProfit_withoutDiscount - data.TotalExpense;
            NetProfit_field.setText(String.format("%.2f $", data.NetProfit));
            totalAvailableProduct_wholePrice();
            totalAvailableProduct_realPrice();
            data.MoneySafe = data.TotalCashPay + data.TotalVodafoneCash + data.TotalInstaPay;
            Money_safe.setText(String.format("%.2f $", data.MoneySafe));
            unavailableProduct();
            AvailableProducts();
            loadProfitByTypeReport();
        }

        fromDatePickerCapital.setValue(null);
        toDatePickerCapital.setValue(null);
    }

    public void mainPage() {
        totalRealPriceToday();
        Safe_main.setText(String.format("%.2f $", data.MoneySafe));
        unavailable_main.setText(data.UnavailbleProductCount + "");
        available_main.setText(data.AvailbleProductCount + "");
        loadDailySalesAreaChart();
    }

    public void loadDailySalesAreaChart() {
        dailySalesChart.getData().clear();

        String sql = "SELECT DATE(invoice_date) as day, SUM(total_price_after_discount) as total FROM invoices GROUP BY day ORDER BY day";

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("مبيعات اليوم");

            while (rs.next()) {
                String day = rs.getString("day");
                double total = rs.getDouble("total");
                series.getData().add(new XYChart.Data<>(day, total));
            }

            dailySalesChart.getData().add(series);

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء تجهيز الرسم البياني", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    public void rolesGetData(int id) {
        String sql = "SELECT * FROM user_roles WHERE employee_id = ?";

        try ( Connection connect = database.getConnection();  PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                mainPage_checkBox.setSelected(rs.getInt("mainPage") == 1);
                wareHouses_checkBox.setSelected(rs.getInt("wareHouse") == 1);
                purches_checkBox.setSelected(rs.getInt("purches") == 1);
                salesPage_checkBox.setSelected(rs.getInt("salesPage") == 1);
                expensesPage_checkBox.setSelected(rs.getInt("expensesPage") == 1);
                monyPage_checkBox.setSelected(rs.getInt("monyPage") == 1);
            }

        } catch (Exception e) {
            al.E_Alert("حدث خطأ أثناء تحميل الصلاحيات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void setRoleParameters(PreparedStatement stmt, int employeeId) throws SQLException {
        stmt.setInt(1, mainPage_checkBox.isSelected() ? 1 : 0);
        stmt.setInt(2, wareHouses_checkBox.isSelected() ? 1 : 0);
        stmt.setInt(3, purches_checkBox.isSelected() ? 1 : 0);
        stmt.setInt(4, salesPage_checkBox.isSelected() ? 1 : 0);
        stmt.setInt(5, expensesPage_checkBox.isSelected() ? 1 : 0);
        stmt.setInt(6, monyPage_checkBox.isSelected() ? 1 : 0);
        stmt.setInt(7, employeeId);
    }

    public void saveUserRoles(int employeeId) {
        String checkSql = "SELECT id FROM user_roles WHERE employee_id = ?";

        try ( Connection connect = database.getConnection();  PreparedStatement checkStmt = connect.prepareStatement(checkSql)) {

            checkStmt.setInt(1, employeeId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // موجود: نعمل UPDATE
                String updateSql = "UPDATE user_roles SET "
                        + "mainPage = ?, wareHouse = ?, purches = ?, salesPage = ?, "
                        + "expensesPage = ?, monyPage = ? "
                        + "WHERE employee_id = ?";

                try ( PreparedStatement updateStmt = connect.prepareStatement(updateSql)) {
                    setRoleParameters(updateStmt, employeeId);
                    updateStmt.executeUpdate();
                }

            } else {
                // غير موجود: نعمل INSERT
                String insertSql = "INSERT INTO user_roles (employee_id, mainPage, wareHouse, purches, "
                        + "salesPage, expensesPage, monyPage) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)";

                try ( PreparedStatement insertStmt = connect.prepareStatement(insertSql)) {
                    insertStmt.setInt(1, employeeId);
                    insertStmt.setInt(2, mainPage_checkBox.isSelected() ? 1 : 0);
                    insertStmt.setInt(3, wareHouses_checkBox.isSelected() ? 1 : 0);
                    insertStmt.setInt(4, purches_checkBox.isSelected() ? 1 : 0);
                    insertStmt.setInt(5, salesPage_checkBox.isSelected() ? 1 : 0);
                    insertStmt.setInt(6, expensesPage_checkBox.isSelected() ? 1 : 0);
                    insertStmt.setInt(7, monyPage_checkBox.isSelected() ? 1 : 0);
                    insertStmt.executeUpdate();
                }
            }

            al.E_Alert("تم حفظ الصلاحيات بنجاح", Alert.AlertType.INFORMATION);

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء حفظ الصلاحيات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void saveRolesBtn() {
        Employee employee = employeeTable.getSelectionModel().getSelectedItem();
        if (employee == null) {
            al.E_Alert("الرجاء تحديد الموظف من الجدول", AlertType.ERROR);
            return;
        }
        int selectedEmployeeId = employee.getId();
        saveUserRoles(selectedEmployeeId);
    }

    public UserRoles loadRolesForUser(int employeeId) {
        UserRoles roles = new UserRoles();
        String sql = "SELECT * FROM user_roles WHERE employee_id = ?";

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                roles.mainPage = rs.getBoolean("mainPage");
                roles.wareHouses = rs.getBoolean("wareHouse");
                roles.purches = rs.getBoolean("purches");
                roles.salesPage = rs.getBoolean("salesPage");
                roles.expensesPage = rs.getBoolean("expensesPage");
                roles.monyPage = rs.getBoolean("monyPage");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return roles;
    }

    public void initUserPermissions(int employeeId, String username) {
        data.employeeId = employeeId;
        data.username = username;

        if (!username.equalsIgnoreCase("admin")) {
            UserRoles userRoles = loadRolesForUser(employeeId);

            mainPage_btn.setVisible(userRoles.mainPage);
            warehouses_Btn.setVisible(userRoles.wareHouses);
            purches_btn.setVisible(userRoles.purches);
            sales_btn.setVisible(userRoles.salesPage);
            expenses_btn.setVisible(userRoles.expensesPage);
            capital_btn.setVisible(userRoles.monyPage);
        }
    }

    public void clientsSelected() {
        customer cust = addClientsTable.getSelectionModel().getSelectedItem();
        if (cust == null) {
            return;
        }
        clientName_sales.setText(cust.getName());
        clientPhone_sales.setText(cust.getPhone());
        clientAddress_sales.setText(cust.getAdress());
        remark_sales.setText(cust.getRemark());
    }

    public void add_clientsBtn() {
        if (clientName_sales.getText() == null || clientName_sales.getText().isEmpty()) {
            al.E_Alert("يجب ادخال اسم العميل على الاقل", Alert.AlertType.ERROR);
            return;
        } else {
            services.insertCustomer(clientName_sales.getText(), clientPhone_sales.getText(), clientAddress_sales.getText(), remark_sales.getText());
            returnClintTable();
            clearClientsBtn();
        }
    }

    public void updateClientsBtn() {
        if (addClientsTable.getSelectionModel().getSelectedItem() == null) {
            al.E_Alert("يجب اختيار عميل اولا", Alert.AlertType.ERROR);
            return;
        } else if (clientName_sales.getText() == null || clientName_sales.getText().isEmpty()) {
            al.E_Alert("يجب ادخال اسم العميل على الاقل", Alert.AlertType.ERROR);
            return;
        } else {
            customer cust = addClientsTable.getSelectionModel().getSelectedItem();
            services.updateCustomer(cust.getId(), clientName_sales.getText(), clientPhone_sales.getText(), clientAddress_sales.getText(), remark_sales.getText());
            returnClintTable();
            clearClientsBtn();
        }
    }

    public void deleteClientsBtn() {
        if (addClientsTable.getSelectionModel().getSelectedItem() == null) {
            al.E_Alert("يجب اختيار عميل اولا", Alert.AlertType.ERROR);
        } else {
            customer cust = addClientsTable.getSelectionModel().getSelectedItem();
            services.deleteCustomer(cust.getId());
            returnClintTable();
            clearClientsBtn();
        }
    }

    public void clearClientsBtn() {
        addClientsTable.getSelectionModel().clearSelection();
        clientName_sales.clear();
        clientPhone_sales.clear();
        clientAddress_sales.clear();
        remark_sales.clear();
    }

    public void clearReturnsForm() {
        returnMethodList.getSelectionModel().clearSelection();
        return_productID.clear();
        return_model.clear();
        return_realPrice.clear();
        return_reciptID.clear();
        return_reson.clear();
    }

    public void toAddReturnItems() {
        viewreturnPurchaseInvoicesForm1.setVisible(false);
        addreturnPurchaseInvoicesForm1.setVisible(true);
        returnShowDataReturns();
        ReturnItemsToDatePicker.setValue(null);
        ReturnItemsFromDatePicker.setValue(null);
    }

    public void searchDeferredSalesByDateRange() {
        if (fromDatePicker_deferredForm.getValue() == null) {
            al.E_Alert("يرجى اختيار تاريخ البداية والنهاية", Alert.AlertType.WARNING);
        } else {
            LocalDate fromDate = fromDatePicker_deferredForm.getValue();
            LocalDate toDate = toDatePicker_deferredForm.getValue();

            String searchInvoices = deferredInvoice_search.getText().trim();
            services.searchInvoicesFlexible(fromDate, toDate, searchInvoices, deferredInvoiceTable);
        }
    }

    public void deferredInvoicesSelectData() {
        Invoices invoices = deferredInvoiceTable.getSelectionModel().getSelectedItem();
        if (invoices == null) {
            return;
        }
        totalDeferred.setText(Double.toString(invoices.getDeffered()));
        deferredClientName.setText(invoices.getClientName());
        deferredInvoicesId.setText(Integer.toString(invoices.getInvoice_id()));
    }

    public void paymentDeferredInvoicesBtn() {
        Invoices invoices = deferredInvoiceTable.getSelectionModel().getSelectedItem();
        try {

            if ((deferred_cashPay.getText().isEmpty() || deferred_cashPay.getText() == null)
                    && (deferred_instaPay.getText().isEmpty() || deferred_instaPay.getText() == null)
                    && (deferred_VodafonePay.getText().isEmpty() || deferred_VodafonePay.getText() == null)) {
                al.E_Alert("يجب اختيار مبلغ للدفع اولا", Alert.AlertType.ERROR);
            } else if (Double.valueOf(deferred_cashPay.getText()) < 0 || Double.valueOf(deferred_instaPay.getText()) < 0
                    || Double.valueOf(deferred_VodafonePay.getText()) < 0) {
                al.E_Alert("يجب ان تكون قيم الدفع موجبه", Alert.AlertType.ERROR);
            } else {
                if ((Double.valueOf(deferred_cashPay.getText()) + Double.valueOf(deferred_instaPay.getText())
                        + Double.valueOf(deferred_VodafonePay.getText())) > Double.valueOf(totalDeferred.getText())) {
                    al.E_Alert("يجب ان يكون المبلغ المدفوع اقل من او يساوى الاجل", Alert.AlertType.ERROR);
                } else {
                    services.updateInvoiceByIdAndPay(invoices.getInvoice_id(), deferred_cashPay.getText(),
                            deferred_instaPay.getText(), deferred_VodafonePay.getText());
                    clearDeferredInvoicesBtn();
                    deferredInvoiceShowData();
                }
            }
        } catch (Exception e) {
            al.E_Alert("ادخل قيم الدفع بطريقة صحيحة", Alert.AlertType.ERROR);
        }

    }

    public void clearDeferredInvoicesBtn() {
        deferredInvoice_search.clear();
        deferred_cashPay.setText("0");
        deferred_VodafonePay.setText("0");
        deferred_instaPay.setText("0");
        deferredInvoicesId.clear();
        deferredClientName.clear();
        totalDeferred.clear();
        toDatePicker_deferredForm.setValue(null);
        fromDatePicker_deferredForm.setValue(null);
        returnClintList();
        deferredInvoiceShowData();
    }

    public void printSales() {
        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = toDatePicker.getValue();
        func.printSalesByDate(fromDate, toDate, listofsales);
    }

    public void printSalesReturns() {
        LocalDate fromDate = fromDatePicker_deferredForm.getValue();
        LocalDate toDate = toDatePicker_deferredForm.getValue();
        func.printInvoicesByDate(fromDate, toDate, deferredInvoiceL, "تقرير الآواجل: ");
    }

    public void printInvoices() {
        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = toDatePicker.getValue();
        func.printInvoicesByDate(fromDate, toDate, invoiceList, "تقرير المبيعات: ");
    }

    public void printReturn() {
        LocalDate fromDateC = ReturnItemsFromDatePicker.getValue();
        LocalDate toDateC = ReturnItemsToDatePicker.getValue();
        func.printReturnsByDate(fromDateC, toDateC, returnItemL);
    }

    public void printExpenses() {
        LocalDate fromDateC = fromDatePickerEx.getValue();
        LocalDate toDateC = toDatePickerEx.getValue();
        searchExpenses();
        func.printExpenses(fromDateC, toDateC, searchExpenseResult);
    }

    public void printReceipt() {
        LocalDate fromDateC = fromDatePickerReceiptVoucher.getValue();
        LocalDate toDateC = toDatePickerReceiptVoucher.getValue();
        func.printReceiptReport(fromDateC, toDateC, voucherList);
    }

    public void printCurrentStockReport() {
        String warehouse = searchWarehouseList_currentStockBalance.getSelectionModel().getSelectedItem();
        String type = itemTypeListSearch_currentStockBalance.getSelectionModel().getSelectedItem();
        func.printCurrentStockReport(warehouse, type, currentItems);
    }

    public void printItemsMovementReport() {
        String warehouse = warehouseList_itemsMovementForm.getSelectionModel().getSelectedItem();
        LocalDate fromDate = fromDatePicker_itemsMovementForm.getValue();
        LocalDate toDate = toDatePicker_itemsMovementForm.getValue();
        searchItemsMovementByDateRange();
        func.printItemsMovementReport(warehouse, fromDate, toDate, movements);
    }

    public void printSettlementBondsReport() {
        LocalDate fromDate = fromDatePicker_boundsForm.getValue();
        LocalDate toDate = toDatePicker_boundsForm.getValue();
        String warehouse = searchWarehouseList_boundsForm.getSelectionModel().getSelectedItem();
        func.printSettlementBondsReport(warehouse, fromDate, toDate, settlementList);
    }

    public void searchByRangeReturnItems() {
        LocalDate fromDate = ReturnItemsFromDatePicker.getValue();
        LocalDate toDate = ReturnItemsToDatePicker.getValue();

        if (fromDate == null || toDate == null) {
            al.E_Alert("يرجى اختيار تاريخ البداية والنهاية", Alert.AlertType.WARNING);
            return;
        }

        if (toDate.isBefore(fromDate)) {
            al.E_Alert("تاريخ النهاية يجب أن يكون بعد تاريخ البداية", Alert.AlertType.WARNING);
            return;
        }

        ObservableList<Returns> filteredList = services.getReturnsByDateRange(fromDate, toDate);
        returnProductTableView.setItems(filteredList);
    }

    private ObservableList<Invoices> ClientsAndPurchasesList;
    private List<productData> salesListDetails;

    public void searchClientsAndPurchasesDetails() {
        String clint = clientsListAccountStatementsDetails.getSelectionModel().getSelectedItem();
        LocalDate fromDate = fromDatePickerClientDetails.getValue();
        LocalDate toDate = toDatePickerClient1Details.getValue();
        salesListDetails = services.getAllSales(clint, fromDate, toDate);
        clientSalesDetails_col_modelId.setCellValueFactory(new PropertyValueFactory<>("modelID"));
        clientSalesDetails_col_model_sales.setCellValueFactory(new PropertyValueFactory<>("model"));
        clientSalesDetails_col_type_sales.setCellValueFactory(new PropertyValueFactory<>("type"));
        clientSalesDetails_col_quantity.setCellValueFactory(new PropertyValueFactory<>("mNumberavailable"));
        clientSalesDetails_col_totalPrice.setCellValueFactory(new PropertyValueFactory<>("total"));
        clientSalesDetails_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        clientSalesDetails_col_warehouse_sales.setCellValueFactory(new PropertyValueFactory<>("wareHouse"));
        clientSalesDetails_col_invoiceId.setCellValueFactory(new PropertyValueFactory<>("invoicesID"));
        clientSalesDetails_col_unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        clientSalesDetails_col_size.setCellValueFactory(new PropertyValueFactory<>("size"));
        clientSalesDetails_col_color.setCellValueFactory(new PropertyValueFactory<>("color"));

        ObservableList<productData> observableSales = FXCollections.observableArrayList(salesListDetails);
        clientSalesDetailsTable1.setItems(observableSales);
        func.setupTableSearchFilter(clientDetailSearch, clientSalesDetailsTable1, observableSales,
                Arrays.asList("modelID", "model", "type", "mNumberavailable", "total", "wareHouse", "invoicesID",
                        "color", "size", "unit"));

        double total = 0.0;
        for (productData data : salesListDetails) {
            total += data.getTotal();
        }
        totalPrint.setTotalDetails(total);
        totalPurchase_accountStatementDetails.setText(Double.toString(total));
    }

    ObservableList<purchaseInvoiceDetail> purchaseInvoiceDetailList;

    public void getPurchaseInvoiceDetailList() {
        purchaseInvoiceDetailList = subServices.getAllPurchaseInvoiceDetails(selectedPurchaseInvoiceHead == null ? null : selectedPurchaseInvoiceHead.getId());

        purchaseDetails_col_invoicesId.setCellValueFactory(new PropertyValueFactory<>("purchaseInvoiceId"));
        purchaseDetails_col_model.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        purchaseDetails_col_color.setCellValueFactory(new PropertyValueFactory<>("color"));
        purchaseDetails_col_size.setCellValueFactory(new PropertyValueFactory<>("size"));
        purchaseDetails_col_type.setCellValueFactory(new PropertyValueFactory<>("model"));
        purchaseDetails_col_warehouse.setCellValueFactory(new PropertyValueFactory<>("warehouse"));
        purchaseDetails_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        purchaseDetails_col_unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        purchaseDetails_col_wholesalePrice.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        purchaseDetails_col_realPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));

        List<purchaseInvoiceDetail> normalList = new ArrayList<>(purchaseInvoiceDetailList);

        purchaseDetailsTableView.setItems(purchaseInvoiceDetailList);
        func.setupTableSearchFilter(purchaseDetails_Search, purchaseDetailsTableView, purchaseInvoiceDetailList,
                Arrays.asList("itemName", "color", "size", "model",
                        "warehouse", "unit"));
    }
    InvoiceModel selectedPurchaseInvoiceHead;

    public void toAddProductForm() {
        selectedPurchaseInvoiceHead = invoicesSuppliersTable.getSelectionModel().getSelectedItem();
        if (selectedPurchaseInvoiceHead == null) {
            al.E_Alert("يجب تحديد الفاتورة اولا ", AlertType.ERROR);
            return;
        }
        addProductForm.setVisible(true);
        addPurchaseForm.setVisible(false);
        String warehouseName;
        List<String> warehouse = new ArrayList<>();
        for (wareHouse data : warehouseL) {
            warehouseName = data.getName();
            warehouse.add(warehouseName);
        }
        ObservableList listData = FXCollections.observableArrayList(warehouse);
        warehouseList_purchaseDetails.getItems().clear();
        warehouseList_purchaseDetails.setItems(listData);
        getPurchaseInvoiceDetailList();
        ObservableList listDataUnit = FXCollections.observableArrayList(RMethodUnit);
        ObservableList listDataSize = FXCollections.observableArrayList(RMethodSize);
        sizeList_purchaseDetails.getItems().clear();
        sizeList_purchaseDetails.setItems(listDataSize);
        QuantityList_purchaseDetails.getItems().clear();
        QuantityList_purchaseDetails.setItems(listDataUnit);
    }

    public void backBtn_toPurchaseForm() {
        addProductForm.setVisible(false);
        addPurchaseForm.setVisible(true);
        invoicesSuppliersTable.getSelectionModel().clearSelection();
    }

    public void searchClientsAndPurchases() {
        String clint = clientsListAccountStatements.getSelectionModel().getSelectedItem();
        LocalDate fromDate = fromDatePickerClient.getValue();
        LocalDate toDate = toDatePickerClient.getValue();
        ClientsAndPurchasesList = services.getInvoicesData(0, clint, fromDate, toDate);
        accountStatement_col_index.setCellValueFactory(cellData
                -> new ReadOnlyObjectWrapper<>(accountStatementSuppliersTable.getItems().indexOf(cellData.getValue()) + 1)
        );
        accountStatement_col_index.setSortable(false);
        accountStatementClients_col_invoicesID.setCellValueFactory(new PropertyValueFactory<>("invoice_id"));
        accountStatementClients_col_totalAfterDiscount.setCellValueFactory(new PropertyValueFactory<>("total_price_after_discount"));
        accountStatementClients_col_return.setCellValueFactory(new PropertyValueFactory<>("totalReturns"));
        accountStatementClients_col_totalPrice.setCellValueFactory(cellData -> {
            Invoices invoice = cellData.getValue();
            double total = invoice.getCashPay() + invoice.getInstaPay() + invoice.getVodafonePay();
            return new ReadOnlyObjectWrapper<Double>(total);
        });
        accountStatementClients_col_deferred.setCellValueFactory(new PropertyValueFactory<>("deffered"));
        accountStatementClients_col_date.setCellValueFactory(new PropertyValueFactory<>("invoice_date"));
        accountStatementClients_col_clientName.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        accountStatementClients_col_clientPhone.setCellValueFactory(new PropertyValueFactory<>("clientPhone"));

        accountStatementClientsTable.setItems(ClientsAndPurchasesList);
        func.setupTableSearchFilter(clientSearch, accountStatementClientsTable, ClientsAndPurchasesList,
                Arrays.asList("invoice_id", "total_price_after_discount",
                        "total_returns", "deffered", "clientName", "clientPhone"));

        double totalPurchase = 0.0;
        double totalRuturns = 0.0;
        double totalDeferred = 0.0;
        double totalCash = 0.0;
        for (Invoices invoices : ClientsAndPurchasesList) {
            totalDeferred += invoices.getDeffered();
            totalPurchase += invoices.getTotal_price_after_discount();
            totalCash += (invoices.getCashPay() + invoices.getInstaPay() + invoices.getVodafonePay());
            totalRuturns += invoices.getTotalReturns();
        }
        totalPrint.setTotalCash(totalCash);
        totalPrint.setTotalDeferred(totalDeferred);
        totalPrint.setTotalPurchase(totalPurchase);
        totalPrint.setTotalRuturns(totalRuturns);
        totalPurchase_accountStatementClients.setText(Double.toString(totalPurchase));
        totalRuturns_accountStatementClients.setText(Double.toString(totalRuturns));
        totalDeferred_accountStatementClients.setText(Double.toString(totalDeferred));
        totalCash_accountStatementClients.setText(Double.toString(totalCash));
    }

    public void printClientsAccountStatementDetails() {
        printReportsValues clientsAccountStatementDetails = new printReportsValues();
        clientsAccountStatementDetails.setClintName(clientsListAccountStatementsDetails.getSelectionModel().getSelectedItem());
        clientsAccountStatementDetails.setFromDate(fromDatePickerClientDetails.getValue());
        clientsAccountStatementDetails.setToDate(toDatePickerClient1Details.getValue());
        clientsAccountStatementDetails.setTotalDetails(totalPrint.getTotalDetails());
        func.printSalesDetailsByDate(clientsAccountStatementDetails, salesListDetails);
    }

    public void accountStatementDetailsClearData() {
        clientsListAccountStatementsDetails.getItems().clear();
        totalPurchase_accountStatementDetails.clear();
        clientDetailSearch.clear();
        fromDatePickerClientDetails.setValue(null);
        toDatePickerClient1Details.setValue(null);
        clientSalesDetailsTable1.setItems(FXCollections.observableArrayList());
        returnClintList();
    }

    public void printClientsAccountStatement() {
        printReportsValues clientsAccountStatement = new printReportsValues();
        clientsAccountStatement.setClintName(clientsListAccountStatements.getSelectionModel().getSelectedItem());
        clientsAccountStatement.setFromDate(fromDatePickerClient.getValue());
        clientsAccountStatement.setToDate(toDatePickerClient.getValue());
        clientsAccountStatement.setTotalCash(totalPrint.getTotalCash());
        clientsAccountStatement.setTotalDeferred(totalPrint.getTotalDeferred());
        clientsAccountStatement.setTotalRuturns(totalPrint.getTotalRuturns());
        clientsAccountStatement.setTotalPurchase(totalPrint.getTotalPurchase());
        func.printSalesTotalByDate(clientsAccountStatement, ClientsAndPurchasesList);
    }

    public void clientsAccountStatementClearData() {
        clientsListAccountStatements.getItems().clear();
        totalPurchase_accountStatementClients.clear();
        totalRuturns_accountStatementClients.clear();
        totalDeferred_accountStatementClients.clear();
        totalCash_accountStatementClients.clear();
        clientSearch.clear();
        fromDatePickerClient.setValue(null);
        toDatePickerClient.setValue(null);
        accountStatementClientsTable.setItems(FXCollections.observableArrayList());
        returnClintList();
    }

    public void backBtn_clients() {
        viewreturnPurchaseInvoicesForm1.setVisible(true);
        addreturnPurchaseInvoicesForm1.setVisible(false);
        returnsShowData();
    }

    public void warehouseListmenueShowData() {
        warehouseL = services.getAllWareHouse();
        String warehouseName;
        List<String> warehouse = new ArrayList<>();
        for (wareHouse data : warehouseL) {
            warehouseName = data.getName();
            warehouse.add(warehouseName);
        }
        ObservableList listData = FXCollections.observableArrayList(warehouse);
        warehouseList_menuForm.getItems().clear();
        warehouseList_menuForm.setItems(listData);

        itemWareHouseListSearch_addForm.getItems().clear();
        itemWareHouseListSearch_addForm.setItems(listData);

        searchWarehouseList_currentStockBalance.getItems().clear();
        searchWarehouseList_currentStockBalance.setItems(listData);

        searchWarehouseList_transferForm.getItems().clear();
        searchWarehouseList_transferForm.setItems(listData);

        toWarehouseList_transferForm.getItems().clear();
        toWarehouseList_transferForm.setItems(listData);

        warehouseList_itemsMovementForm.getItems().clear();
        warehouseList_itemsMovementForm.setItems(listData);

        searchWarehouseList_boundsForm.getItems().clear();
        searchWarehouseList_boundsForm.setItems(listData);

        searchWarehouseList_stockTakingForm.getItems().clear();
        searchWarehouseList_stockTakingForm.setItems(listData);

    }
//////////المخازن//////////////////////////////////////////////////////////////////////////////////////////////////////////////

//////////المخازن//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////سندصرف///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Load data into Table
    public void loadReceiptVouchers() {
        voucherList.clear();
        voucherList = vouServices.loadReceiptVouchersList(voucherList);

        receiptVoucher_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        receiptVoucher_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        receiptVoucher_col_cash.setCellValueFactory(new PropertyValueFactory<>("cash"));
        receiptVoucher_col_vodafone.setCellValueFactory(new PropertyValueFactory<>("vodafone"));
        receiptVoucher_col_instaPay.setCellValueFactory(new PropertyValueFactory<>("instaPay"));
        receiptVoucher_col_payerName.setCellValueFactory(new PropertyValueFactory<>("payer"));
        receiptVoucher_col_notes.setCellValueFactory(new PropertyValueFactory<>("notes"));
        receiptVoucher_col_amount.setCellValueFactory(new PropertyValueFactory<>("total"));

        receiptVoucher_table.setItems(voucherList);
        func.setupTableSearchFilter(searchReceiptVoucher, receiptVoucher_table, voucherList,
                Arrays.asList("id", "cash", "vodafone",
                        "instaPay", "payer", "notes", "total"));
    }

    // Delete Receipt Voucher
    public void deleteReceiptVoucher() {
        ReceiptVoucher receiptVoucher = receiptVoucher_table.getSelectionModel().getSelectedItem();

        if (receiptVoucher == null) {
            al.showAlert("خطأ", "حدد الصف المراد تعديلة", Alert.AlertType.ERROR);
            return;
        }

        int id = receiptVoucher.getId();
        String sql = "DELETE FROM receipt_voucher WHERE id=?";

        try ( Connection conn = database.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();

            al.showAlert("نجاح", "تم حذف سند القبض بنجاح", Alert.AlertType.INFORMATION);
            loadReceiptVouchers();
            clearReceiptVoucherFields();
        } catch (SQLException e) {
            al.showAlert("خطأ", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void updateReceiptVoucherBtn() {
        ReceiptVoucher receiptVoucher = receiptVoucher_table.getSelectionModel().getSelectedItem();

        if (receiptVoucher == null) {
            al.showAlert("خطأ", "حدد الصف المراد تعديلة", Alert.AlertType.ERROR);
            return;
        }

        String payer = payer_field.getText();

        String notes = receiptVoucher_notes_field.getText();
        String cashText = receiptVoucher_cash_field.getText().trim();
        String vodafoneText = receiptVoucher_vodafone_field.getText().trim();
        String instaPayText = receiptVoucher_instaPay_field.getText().trim();

        if (func.isntNumeric(cashText) || func.isntNumeric(vodafoneText) || func.isntNumeric(instaPayText) || payer.isEmpty()) {
            al.E_Alert("ادخل بيانات الدفع عبارة عن أرقام", AlertType.ERROR);
            return;
        }

        double Dcash = Double.parseDouble(cashText);
        double Dvodafone = Double.parseDouble(vodafoneText);
        double DinstaPay = Double.parseDouble(instaPayText);

        vouServices.updateReceiptVoucher(receiptVoucher.getId(), payer, Dcash, Dvodafone, DinstaPay, notes);
    }

    public void addReceiptVoucherBtn() {
        String payer = payer_field.getText();

        String notes = receiptVoucher_notes_field.getText();
        String cashText = receiptVoucher_cash_field.getText().trim();
        String vodafoneText = receiptVoucher_vodafone_field.getText().trim();
        String instaPayText = receiptVoucher_instaPay_field.getText().trim();

        if (func.isntNumeric(cashText) || func.isntNumeric(vodafoneText) || func.isntNumeric(instaPayText) || payer.isEmpty()) {
            al.E_Alert("ادخل بيانات الدفع عبارة عن أرقام", AlertType.ERROR);
            return;
        }

        double Dcash = Double.parseDouble(cashText);
        double Dvodafone = Double.parseDouble(vodafoneText);
        double DinstaPay = Double.parseDouble(instaPayText);

        vouServices.addReceiptVoucher(payer, Dcash, Dvodafone, DinstaPay, notes);
    }

    public void selectDatafromTable_receiptVoucher() {
        ReceiptVoucher receiptVoucher = receiptVoucher_table.getSelectionModel().getSelectedItem();

        payer_field.setText(receiptVoucher.getPayer());
        receiptVoucher_notes_field.setText(receiptVoucher.getNotes());
        receiptVoucher_cash_field.setText(receiptVoucher.getCash() + "");
        receiptVoucher_vodafone_field.setText(receiptVoucher.getVodafone() + "");
        receiptVoucher_instaPay_field.setText(receiptVoucher.getInstaPay() + "");
    }

    public void clearReceiptVoucherFields() {
        // ComboBox
        payer_field.clear();

        // TextFields
        receiptVoucher_notes_field.clear();
        receiptVoucher_cash_field.setText("0");
        receiptVoucher_vodafone_field.setText("0");
        receiptVoucher_instaPay_field.setText("0");
        searchReceiptVoucher.clear();

        // Table Selection
        receiptVoucher_table.getSelectionModel().clearSelection();

        // Date Picker
        fromDatePickerReceiptVoucher.setValue(null);
        toDatePickerReceiptVoucher.setValue(null);
        loadReceiptVouchers();
    }

    public void searchReceiptVouchersDate() {
        LocalDate fromDate = fromDatePickerReceiptVoucher.getValue();
        LocalDate toDate = toDatePickerReceiptVoucher.getValue();
        String searchText = searchReceiptVoucher.getText();

        if (fromDate == null && toDate == null) {
            al.showAlert("خطأ في البحث", "حدد التورايخ", Alert.AlertType.ERROR);
            return;
        }
        voucherList = vouServices.searchReceiptVouchers(fromDate, toDate, voucherList);
        // Refresh table
        receiptVoucher_table.setItems(voucherList);

        receiptVoucher_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        receiptVoucher_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        receiptVoucher_col_cash.setCellValueFactory(new PropertyValueFactory<>("cash"));
        receiptVoucher_col_vodafone.setCellValueFactory(new PropertyValueFactory<>("vodafone"));
        receiptVoucher_col_instaPay.setCellValueFactory(new PropertyValueFactory<>("instaPay"));
        receiptVoucher_col_payerName.setCellValueFactory(new PropertyValueFactory<>("payer"));
        receiptVoucher_col_notes.setCellValueFactory(new PropertyValueFactory<>("notes"));
        receiptVoucher_col_amount.setCellValueFactory(new PropertyValueFactory<>("total"));

        func.setupTableSearchFilter(searchReceiptVoucher, receiptVoucher_table, voucherList,
                Arrays.asList("id", "cash", "vodafone",
                        "instaPay", "payer", "notes", "total"));

    }

//////////سندصرف///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private TableView<productData> currentStockBalanceTable;
    @FXML
    private TableColumn<productData, String> currentStockBalance_col_realPrice;
    @FXML
    private TableColumn<productData, String> currentStockBalance_col_wholesalePrice;
    @FXML
    private TableColumn<productData, String> currentStockBalance_col_mNo;
    @FXML
    private TableColumn<productData, String> currentStockBalance_col_warehouse;
    @FXML
    private TableColumn<productData, String> currentStockBalance_col_type;
    @FXML
    private TableColumn<productData, String> currentStockBalance_col_model;
    @FXML
    private TableColumn<productData, String> currentStockBalance_col_id;
    @FXML
    private TableColumn<productData, String> currentStockBalance_col_invoicesId;
    @FXML
    private TableColumn<productData, String> currentStockBalance_col_supplier;
    @FXML
    private TableColumn<productData, String> currentStockBalance_col_total;
    @FXML
    private ComboBox<String> itemTypeListSearch_currentStockBalance;
    @FXML
    private ComboBox<String> searchWarehouseList_currentStockBalance;
    @FXML
    private TextField currentStockBalance_Search;

    @FXML
    private TableView<productData> itemsTable_itemsMovementForm;
    @FXML
    private TableColumn<productData, String> items_col_id;
    @FXML
    private TableColumn<productData, String> items_col_model;
    @FXML
    private TableColumn<productData, String> items_col_mNo;
    @FXML
    private TableColumn<productData, String> items_col_wholesalePrice;
    @FXML
    private TableColumn<productData, String> items_col_realPrice;
    @FXML
    private TableColumn<productData, String> items_col_type;
    @FXML
    private TableColumn<productData, String> items_col_warehouse;
    @FXML
    private TableColumn<productData, String> items_col_invoicesId;
    @FXML
    private TableColumn<productData, String> items_col_supplier;
    @FXML
    private TableColumn<productData, String> items_col_total;
    @FXML
    private ComboBox<String> warehouseList_itemsMovementForm;
    @FXML
    private ComboBox<String> itemTypeList_itemsMovementForm;
    @FXML
    private DatePicker fromDatePicker_itemsMovementForm;
    @FXML
    private DatePicker toDatePicker_itemsMovementForm;
    @FXML
    private TextField SearchMovementItems_itemsMovementForm;
    @FXML
    private TextField SearchItems_itemsMovementForm;

    @FXML
    private TableView<itemMovementData> itemsMovementTable_itemsMovementForm;
    @FXML
    private TableColumn<itemMovementData, String> itemsMovement_col_movementId;
    @FXML
    private TableColumn<itemMovementData, String> itemsMovement_col_movementType;
    @FXML
    private TableColumn<itemMovementData, String> itemsMovement_col_Quantity;
    @FXML
    private TableColumn<itemMovementData, String> itemsMovement_col_invoiceId;
    @FXML
    private TableColumn<itemMovementData, String> itemsMovement_col_date;
    @FXML
    private TableColumn<itemMovementData, String> itemsMovement_col_remark;
    @FXML
    private TableColumn<itemMovementData, String> itemsMovement_col_itemName;
    @FXML
    private TableColumn<itemMovementData, String> itemsMovement_col_itemId;
    @FXML
    private TableColumn<itemMovementData, String> itemsMovement_col_warehouse;

    ////////////////////////////////////////////////////////////////////// رصيد المخزن الحالي/////////////////////////////////////////////////////
    public ObservableList<productData> currentItemsDataList() {
        ObservableList<productData> listData = FXCollections.observableArrayList();

        String type = itemTypeListSearch_currentStockBalance.getSelectionModel().getSelectedItem();
        String warehouse = searchWarehouseList_currentStockBalance.getSelectionModel().getSelectedItem();

        StringBuilder sql = new StringBuilder("SELECT * FROM product WHERE 1=1");
        List<String> params = new ArrayList<>();

        if (warehouse != null && !warehouse.isEmpty()) {
            sql.append(" AND warehouse LIKE ?");
            params.add("%" + warehouse + "%");
        }
        if (type != null && !type.isEmpty()) {
            sql.append(" AND type LIKE ?");
            params.add("%" + type + "%");
        }
        sql.append(" ORDER BY model COLLATE NOCASE");

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql.toString())) {

            // Bind parameters
            for (int i = 0; i < params.size(); i++) {
                ps.setString(i + 1, params.get(i));
            }

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productData prodData = new productData(
                            rs.getInt("id"),
                            rs.getString("model"),
                            rs.getDouble("mNumberavailable"),
                            rs.getDouble("wholesaleprice"),
                            rs.getDouble("realprice"),
                            rs.getString("type"),
                            rs.getInt("soldNo")
                    );
                    prodData.setWareHouse(rs.getString("warehouse"));
                    listData.add(prodData);
                }
            }

        } catch (SQLException e) {
            al.showAlert("خطأ", "حدث خطأ أثناء تحميل الاصناف", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return listData;
    }

    private ObservableList<productData> currentItems;

    public void currentItemsShowData() {
        currentItems = currentItemsDataList();

        currentStockBalance_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        currentStockBalance_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        currentStockBalance_col_mNo.setCellValueFactory(new PropertyValueFactory<>("mNumberavailable"));
        currentStockBalance_col_wholesalePrice.setCellValueFactory(new PropertyValueFactory<>("wholesalePrice"));
        currentStockBalance_col_realPrice.setCellValueFactory(new PropertyValueFactory<>("realPrice"));
        currentStockBalance_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        currentStockBalance_col_total.setCellValueFactory(new PropertyValueFactory<>("total2"));
        currentStockBalance_col_invoicesId.setCellValueFactory(new PropertyValueFactory<>("invoicesID"));
        currentStockBalance_col_supplier.setCellValueFactory(new PropertyValueFactory<>("suppliers"));
        currentStockBalance_col_warehouse.setCellValueFactory(new PropertyValueFactory<>("wareHouse"));

        currentStockBalanceTable.setItems(currentItems);

        func.setupTableSearchFilter(currentStockBalance_Search, currentStockBalanceTable, currentItems,
                Arrays.asList("id", "model", "type", "suppliers",
                        "invoicesID", "wareHouse"));
    }

    public void currentItemsTab() {
        itemTypeListSearch_currentStockBalance.getSelectionModel().clearSelection();
        searchWarehouseList_currentStockBalance.getSelectionModel().clearSelection();
        currentStockBalance_Search.clear();
        currentItemsShowData();
    }

    ////////////////////////////////////////////////حركة الاصناف ////////////////////////////////////////////////////////////////////
    public ObservableList<productData> itemsDataList() {
        ObservableList<productData> listData = FXCollections.observableArrayList();

        String type = itemTypeList_itemsMovementForm.getSelectionModel().getSelectedItem();
        String warehouse = warehouseList_itemsMovementForm.getSelectionModel().getSelectedItem();

        StringBuilder sql = new StringBuilder("SELECT * FROM product WHERE 1=1");
        List<String> params = new ArrayList<>();

        if (warehouse != null && !warehouse.isEmpty()) {
            sql.append(" AND warehouse LIKE ?");
            params.add("%" + warehouse + "%");
        }
        if (type != null && !type.isEmpty()) {
            sql.append(" AND type LIKE ?");
            params.add("%" + type + "%");
        }

        sql.append(" ORDER BY model COLLATE NOCASE");

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setString(i + 1, params.get(i));
            }

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productData prodData = new productData(
                            rs.getInt("id"),
                            rs.getString("model"),
                            rs.getDouble("mNumberavailable"),
                            rs.getDouble("wholesaleprice"),
                            rs.getDouble("realprice"),
                            rs.getString("type"),
                            rs.getInt("soldNo")
                    );
                    prodData.setWareHouse(rs.getString("warehouse"));
                    listData.add(prodData);
                }
            }

        } catch (SQLException e) {
            al.showAlert("خطأ", "حدث خطأ أثناء تحميل الأصناف", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return listData;
    }

    public void itemsShowData() {
        ObservableList<productData> currentItems = itemsDataList();

        items_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        items_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        items_col_mNo.setCellValueFactory(new PropertyValueFactory<>("mNumberavailable"));
        items_col_wholesalePrice.setCellValueFactory(new PropertyValueFactory<>("wholesalePrice"));
        items_col_realPrice.setCellValueFactory(new PropertyValueFactory<>("realPrice"));
        items_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        items_col_warehouse.setCellValueFactory(new PropertyValueFactory<>("wareHouse"));
        items_col_invoicesId.setCellValueFactory(new PropertyValueFactory<>("invoicesID"));
        items_col_supplier.setCellValueFactory(new PropertyValueFactory<>("suppliers"));
        items_col_total.setCellValueFactory(new PropertyValueFactory<>("total2"));

        itemsTable_itemsMovementForm.setItems(currentItems);
    }

    public void clearItemsTab() {
        itemTypeList_itemsMovementForm.getSelectionModel().clearSelection();
        warehouseList_itemsMovementForm.getSelectionModel().clearSelection();
        SearchItems_itemsMovementForm.clear();
        SearchMovementItems_itemsMovementForm.clear();
        fromDatePicker_itemsMovementForm.setValue(null);
        toDatePicker_itemsMovementForm.setValue(null);
        itemsShowData();
    }

    private ObservableList<itemMovementData> movements;

    public void searchItemsMovementByDateRange() {
        LocalDate fromDate = fromDatePicker_itemsMovementForm.getValue();
        LocalDate toDate = toDatePicker_itemsMovementForm.getValue();
        String searchText = SearchMovementItems_itemsMovementForm.getText() != null
                ? SearchMovementItems_itemsMovementForm.getText().trim()
                : "";

        movements = FXCollections.observableArrayList();

        StringBuilder sql = new StringBuilder(
                "SELECT * FROM item_movements WHERE 1=1"
        );
        List<Object> params = new ArrayList<>();

        // Filter by date range
        if (fromDate != null) {
            sql.append(" AND date(`date`) >= date(?)");
            params.add(fromDate.toString()); // yyyy-MM-dd
        }
        if (toDate != null) {
            sql.append(" AND date(`date`) <= date(?)");
            params.add(toDate.toString()); // yyyy-MM-dd
        }

        // Filter by search text
        if (!searchText.isEmpty()) {
            sql.append(" AND (CAST(movementId AS TEXT) LIKE ? OR CAST(invoiceId AS TEXT) LIKE ? OR movementType LIKE ? OR remark LIKE ? OR productId LIKE ? OR productName LIKE ? OR warehouse LIKE ?)");
            for (int i = 0; i < 7; i++) {
                params.add("%" + searchText + "%");
            }
        }

        sql.append(" ORDER BY date DESC");

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    movements.add(new itemMovementData(
                            rs.getInt("movementId"),
                            rs.getString("movementType"),
                            rs.getDouble("quantity"),
                            rs.getInt("invoiceId"),
                            rs.getString("date"),
                            rs.getString("remark"),
                            rs.getInt("productId"),
                            rs.getString("warehouse"),
                            rs.getString("productName")
                    ));
                }
            }

            // Bind table columns
            itemsMovement_col_movementId.setCellValueFactory(new PropertyValueFactory<>("movementId"));
            itemsMovement_col_movementType.setCellValueFactory(new PropertyValueFactory<>("movementType"));
            itemsMovement_col_Quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            itemsMovement_col_invoiceId.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));
            itemsMovement_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
            itemsMovement_col_remark.setCellValueFactory(new PropertyValueFactory<>("remark"));
            itemsMovement_col_warehouse.setCellValueFactory(new PropertyValueFactory<>("warehouse"));
            itemsMovement_col_itemId.setCellValueFactory(new PropertyValueFactory<>("productId"));
            itemsMovement_col_itemName.setCellValueFactory(new PropertyValueFactory<>("productName"));

            itemsMovementTable_itemsMovementForm.setItems(movements);

        } catch (SQLException e) {
            al.showAlert("خطأ", "حدث خطأ أثناء تحميل حركة الأصناف", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void selectItemMovementsByProductId() {
        ObservableList<itemMovementData> movements = FXCollections.observableArrayList();

        productData prodData = itemsTable_itemsMovementForm.getSelectionModel().getSelectedItem();

        String productName = prodData.getModel();

        String sql = "SELECT * FROM item_movements WHERE productName = ? ORDER BY date DESC";

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql)) {

            ps.setString(1, productName);

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    movements.add(new itemMovementData(
                            rs.getInt("movementId"),
                            rs.getString("movementType"),
                            rs.getDouble("quantity"),
                            rs.getInt("invoiceId"),
                            rs.getString("date"),
                            rs.getString("remark"),
                            rs.getInt("productId"),
                            rs.getString("warehouse"),
                            rs.getString("productName")
                    ));
                }
            }

            // Bind table columns (لو الجدول بتاعك أصلاً متربط، ممكن تشيل السطور دي)
            itemsMovement_col_movementId.setCellValueFactory(new PropertyValueFactory<>("movementId"));
            itemsMovement_col_movementType.setCellValueFactory(new PropertyValueFactory<>("movementType"));
            itemsMovement_col_Quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            itemsMovement_col_invoiceId.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));
            itemsMovement_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
            itemsMovement_col_remark.setCellValueFactory(new PropertyValueFactory<>("remark"));
            itemsMovement_col_warehouse.setCellValueFactory(new PropertyValueFactory<>("warehouse"));
            itemsMovement_col_itemId.setCellValueFactory(new PropertyValueFactory<>("productId"));
            itemsMovement_col_itemName.setCellValueFactory(new PropertyValueFactory<>("productName"));

            itemsMovementTable_itemsMovementForm.setItems(movements);

        } catch (SQLException e) {
            al.showAlert("خطأ", "حدث خطأ أثناء تحميل حركة الصنف", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /*
    CREATE TABLE IF NOT EXISTS item_movements (
    movementId INTEGER PRIMARY KEY AUTOINCREMENT,
    movementType TEXT NOT NULL,         -- IN / OUT / ADJUST / TRANSFER
    quantity INTEGER NOT NULL,
    invoiceId INTEGER,                  -- الفاتورة المرتبطة بالحركة
    date DATE NOT NULL,
    remark TEXT,
    productId INTEGER,                  -- لو هتربطه بجدول المنتجات
    productName TEXT,
    warehouse TEXT,                     -- لو عايز تحدد المخزن
    FOREIGN KEY (productId) REFERENCES product(id) ON DELETE CASCADE
);
     */
    /////////////////////////////////////////////////////////سند تسوية محزون /////////////////////////////////////////////////
    /*
    CREATE TABLE settlement_bonds (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    bond_number TEXT NOT NULL,                -- رقم السند
    date TEXT NOT NULL,                       -- تاريخ السند بصيغة ISO (مثل 2025-08-07)

    item_id INTEGER NOT NULL,                 -- كود الصنف (يفترض أنه مرتبط بجدول الأصناف)
    item_name TEXT NOT NULL,                  -- اسم الصنف (للعرض فقط)
    type TEXT,                           -- نوع الصنف (اختياري)

    warehouse TEXT NOT NULL,             -- اسم المخزن
    book_balance REAL NOT NULL,               -- الرصيد الدفتري
    actual_balance REAL NOT NULL,             -- الرصيد الفعلي
    difference REAL NOT NULL,                 -- فرق التسوية (actual - book)
    total_settlement_amount REAL NOT NULL,    -- مبلغ التسوية = فرق * سعر الشراء مثلاً

    note TEXT                                  -- ملاحظات (اختياري)
    );
     */
    @FXML
    private TableView<SettlementBondData> settlementBondsTable;
    @FXML
    private TableColumn<SettlementBondData, String> settlementBonds_col_date;
    @FXML
    private TableColumn<SettlementBondData, Double> settlementBonds_col_totalSettlement;
    @FXML
    private TableColumn<SettlementBondData, Double> settlementBonds_col_differenceSettlement;
    @FXML
    private TableColumn<SettlementBondData, Double> settlementBonds_col_actualBalance;
    @FXML
    private TableColumn<SettlementBondData, Double> settlementBonds_col_bookBalance;
    @FXML
    private TableColumn<SettlementBondData, String> settlementBonds_col_warehouse;
    @FXML
    private TableColumn<SettlementBondData, String> settlementBonds_col_type;
    @FXML
    private TableColumn<SettlementBondData, String> settlementBonds_col_mode;
    @FXML
    private TableColumn<SettlementBondData, Integer> settlementBonds_col_id;
    @FXML
    private TableColumn<SettlementBondData, String> settlementBonds_col_boundId;
    @FXML
    private ComboBox<String> itemTypeListSearch_boundsForm;
    @FXML
    private ComboBox<String> searchWarehouseList_boundsForm;
    @FXML
    private TextField bounds_Search;
    @FXML
    private DatePicker fromDatePicker_boundsForm;
    @FXML
    private DatePicker toDatePicker_boundsForm;
    @FXML
    private AnchorPane stock_settlement_bonds_form;
    @FXML
    private AnchorPane stockTaking_form;

    public ObservableList<SettlementBondData> loadSettlementBondsToTable() {
        ObservableList<SettlementBondData> bondList = FXCollections.observableArrayList();

        // Get filter values from UI
        String searchText = bounds_Search.getText() != null ? bounds_Search.getText().trim() : "";
        String type = itemTypeListSearch_boundsForm.getSelectionModel().getSelectedItem();
        String warehouse = searchWarehouseList_boundsForm.getSelectionModel().getSelectedItem();
        LocalDate fromDate = fromDatePicker_boundsForm.getValue();
        LocalDate toDate = toDatePicker_boundsForm.getValue();

        StringBuilder sql = new StringBuilder("SELECT * FROM settlement_bonds WHERE 1=1");
        List<Object> params = new ArrayList<>();

        // Filter by date range
        if (fromDate != null) {
            sql.append(" AND date(`date`) >= date(?)");
            params.add(fromDate.toString()); // yyyy-MM-dd
        }
        if (toDate != null) {
            sql.append(" AND date(`date`) <= date(?)");
            params.add(toDate.toString()); // yyyy-MM-dd
        }

        // Filter by search text
        if (!searchText.isEmpty()) {
            sql.append(" AND (item_name LIKE ? OR CAST(id AS TEXT) LIKE ? OR warehouse LIKE ? OR type LIKE ? OR CAST(item_id AS TEXT) LIKE ?)");
            for (int i = 0; i < 5; i++) {
                params.add("%" + searchText + "%");
            }
        }

        // Filter by type
        if (type != null && !type.isEmpty()) {
            sql.append(" AND type LIKE ?");
            params.add("%" + type + "%");
        }

        // Filter by warehouse
        if (warehouse != null && !warehouse.isEmpty()) {
            sql.append(" AND warehouse LIKE ?");
            params.add("%" + warehouse + "%");
        }

        sql.append(" ORDER BY date DESC");

        // Execute the query and fill the list
        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SettlementBondData bond = new SettlementBondData(
                            rs.getInt("id"),
                            rs.getDouble("total_settlement_amount"),
                            rs.getDouble("difference"),
                            rs.getDouble("actual_balance"),
                            rs.getDouble("book_balance"),
                            rs.getString("warehouse"),
                            rs.getString("type"),
                            rs.getString("item_name"),
                            rs.getInt("item_id")
                    );
                    bondList.add(bond);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bondList;
    }

    private ObservableList<SettlementBondData> settlementList;

    public void settlementBondsShowData() {
        settlementList = loadSettlementBondsToTable();

        settlementBonds_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        settlementBonds_col_totalSettlement.setCellValueFactory(new PropertyValueFactory<>("totalSettlementAmount"));
        settlementBonds_col_differenceSettlement.setCellValueFactory(new PropertyValueFactory<>("difference"));
        settlementBonds_col_actualBalance.setCellValueFactory(new PropertyValueFactory<>("actualBalance"));
        settlementBonds_col_bookBalance.setCellValueFactory(new PropertyValueFactory<>("bookBalance"));
        settlementBonds_col_warehouse.setCellValueFactory(new PropertyValueFactory<>("warehouseName"));
        settlementBonds_col_type.setCellValueFactory(new PropertyValueFactory<>("itemType"));
        settlementBonds_col_mode.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        settlementBonds_col_id.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        settlementBonds_col_boundId.setCellValueFactory(new PropertyValueFactory<>("id"));

        settlementBondsTable.setItems(settlementList);
    }

    public void settlementBondsTab() {

    }

    public void goToStockTakingForm() {
        stock_settlement_bonds_form.setVisible(false);
        stockTaking_form.setVisible(true);
        clearSettlementBond();
    }

    public void backFromStockTakingForm() {
        bounds_Search.clear();
        itemTypeListSearch_boundsForm.getSelectionModel().clearSelection();
        searchWarehouseList_boundsForm.getSelectionModel().clearSelection();
        fromDatePicker_boundsForm.setValue(null);
        toDatePicker_boundsForm.setValue(null);
        settlementBondsShowData();
        stock_settlement_bonds_form.setVisible(true);
        stockTaking_form.setVisible(false);
    }

    //////////////////////////////////////////// جرد الاصناف ///////////////////////////////////////////////
    @FXML
    private ComboBox<String> searchWarehouseList_stockTakingForm;
    @FXML
    private ComboBox<String> itemTypeListSearch_stockTakingForm;
    @FXML
    private TextField stockTaking_Search;
    @FXML
    private TableView<productData> stockTakingTable;
    @FXML
    private TableColumn<productData, Integer> stockTaking_col_modelId;
    @FXML
    private TableColumn<productData, String> stockTaking_col_model;
    @FXML
    private TableColumn<productData, Double> stockTaking_col_quantity;
    @FXML
    private TableColumn<productData, Double> stockTaking_col_wholesalePrice;
    @FXML
    private TableColumn<productData, Double> stockTaking_col_realPrice;
    @FXML
    private TableColumn<productData, String> stockTaking_col_type;
    @FXML
    private TableColumn<productData, String> stockTaking_col_supplier;
    @FXML
    private TableColumn<productData, String> stockTaking_col_invoicesId;
    @FXML
    private TableColumn<productData, String> stockTaking_col_warehouse;
    @FXML
    private TextField itemName_stockTakingForm;
    @FXML
    private TextField bookQuantity_stockTakingForm;
    @FXML
    private TextField actualQuantity_stockTakingForm;
    @FXML
    private TextField diff_stockTakingForm;
    @FXML
    private TextField totalSettlement_stockTakingForm;

    public ObservableList<productData> loadTransferDataList() {
        ObservableList<productData> listData = FXCollections.observableArrayList();

        String warehouse = searchWarehouseList_transferForm.getSelectionModel().getSelectedItem();
        String type = itemTypeListSearch_transferForm.getSelectionModel().getSelectedItem();
        String searchText = itemSearch_transferForm.getText() != null ? itemSearch_transferForm.getText().trim() : "";

        StringBuilder sql = new StringBuilder("SELECT * FROM product WHERE 1=1");
        List<String> params = new ArrayList<>();

        if (warehouse != null && !warehouse.isEmpty()) {
            sql.append(" AND warehouse LIKE ?");
            params.add("%" + warehouse + "%");
        }
        if (type != null && !type.isEmpty()) {
            sql.append(" AND type LIKE ?");
            params.add("%" + type + "%");
        }
        if (!searchText.isEmpty()) {
            sql.append(" AND (model LIKE ? OR CAST(id AS TEXT) LIKE ? OR suppliers LIKE ?)");
            for (int i = 0; i < 3; i++) {
                params.add("%" + searchText + "%");
            }
        }

        sql.append(" ORDER BY model COLLATE NOCASE");

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setString(i + 1, params.get(i));
            }

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productData prodData = new productData(
                            rs.getInt("id"),
                            rs.getString("model"),
                            rs.getDouble("mNumberavailable"),
                            rs.getDouble("wholesaleprice"),
                            rs.getDouble("realprice"),
                            rs.getString("type"),
                            rs.getInt("soldNo")
                    );
                    prodData.setWareHouse(rs.getString("warehouse"));
                    listData.add(prodData);
                }
            }

        } catch (SQLException e) {
            al.showAlert("خطأ", "حدث خطأ أثناء تحميل بيانات الجرد", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return listData;
    }

    public ObservableList<productData> loadStockTakingDataList() {
        ObservableList<productData> listData = FXCollections.observableArrayList();

        String warehouse = searchWarehouseList_stockTakingForm.getSelectionModel().getSelectedItem();
        String type = itemTypeListSearch_stockTakingForm.getSelectionModel().getSelectedItem();
        String searchText = stockTaking_Search.getText() != null ? stockTaking_Search.getText().trim() : "";

        StringBuilder sql = new StringBuilder("SELECT * FROM product WHERE 1=1");
        List<String> params = new ArrayList<>();

        if (warehouse != null && !warehouse.isEmpty()) {
            sql.append(" AND warehouse LIKE ?");
            params.add("%" + warehouse + "%");
        }
        if (type != null && !type.isEmpty()) {
            sql.append(" AND type LIKE ?");
            params.add("%" + type + "%");
        }
        if (!searchText.isEmpty()) {
            sql.append(" AND (model LIKE ? OR CAST(id AS TEXT) LIKE ? OR suppliers LIKE ?)");
            for (int i = 0; i < 3; i++) {
                params.add("%" + searchText + "%");
            }
        }

        sql.append(" ORDER BY model COLLATE NOCASE");

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setString(i + 1, params.get(i));
            }

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productData prodData = new productData(
                            rs.getInt("id"),
                            rs.getString("model"),
                            rs.getDouble("mNumberavailable"),
                            rs.getDouble("wholesaleprice"),
                            rs.getDouble("realprice"),
                            rs.getString("type"),
                            rs.getInt("soldNo")
                    );
                    prodData.setWareHouse(rs.getString("warehouse"));
                    listData.add(prodData);
                }
            }

        } catch (SQLException e) {
            al.showAlert("خطأ", "حدث خطأ أثناء تحميل بيانات الجرد", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return listData;
    }

    private ObservableList<productData> stockTakingItems;

    public void stockItemShowData() {
        stockTakingItems = loadStockTakingDataList();

        stockTaking_col_modelId.setCellValueFactory(new PropertyValueFactory<>("id"));
        stockTaking_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        stockTaking_col_quantity.setCellValueFactory(new PropertyValueFactory<>("mNumberavailable"));
        stockTaking_col_wholesalePrice.setCellValueFactory(new PropertyValueFactory<>("wholesalePrice"));
        stockTaking_col_realPrice.setCellValueFactory(new PropertyValueFactory<>("realPrice"));
        stockTaking_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        stockTaking_col_supplier.setCellValueFactory(new PropertyValueFactory<>("suppliers"));
        stockTaking_col_invoicesId.setCellValueFactory(new PropertyValueFactory<>("invoicesID"));
        stockTaking_col_warehouse.setCellValueFactory(new PropertyValueFactory<>("wareHouse"));

        stockTakingTable.setItems(stockTakingItems);

        func.setupTableSearchFilter(stockTaking_Search, stockTakingTable, stockTakingItems,
                Arrays.asList("id", "model", "type", "suppliers",
                        "invoicesID", "wareHouse"));
    }

    public void stockTakingSelectData() {
        productData selectedItem = stockTakingTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            itemName_stockTakingForm.setText(selectedItem.getModel());
            bookQuantity_stockTakingForm.setText(String.valueOf(selectedItem.getMNumberavailable()));
        }
    }

    public void countDiffSettlementBond() {
        try {
            // تحقق من اختيار الصنف
            productData selectedItem = stockTakingTable.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                al.showAlert("تحذير", "حدد الصنف من الجدول أولاً", Alert.AlertType.WARNING);
                return;
            }

            // تحقق من إدخال الكمية الفعلية
            String actualQuantityText = actualQuantity_stockTakingForm.getText().trim();
            if (actualQuantityText.isEmpty()) {
                al.showAlert("خطأ", "أدخل الكمية الفعلية", Alert.AlertType.ERROR);
                return;
            }

            // محاولة التحويل لرقم
            double actualQuantity;
            try {
                actualQuantity = Double.parseDouble(actualQuantityText);
            } catch (NumberFormatException e) {
                al.showAlert("خطأ", "الكمية الفعلية يجب أن تكون أرقام", Alert.AlertType.ERROR);
                return;
            }

            if (Double.parseDouble(actualQuantity_stockTakingForm.getText().trim()) < 0.0) {
                al.showAlert("خطأ", "الكمية الفعلية يجب أن تكون أرقام", Alert.AlertType.ERROR);
                return;
            }

            // الحسابات
            double bookQuantity = selectedItem.getMNumberavailable();
            double difference = actualQuantity - bookQuantity;
            double differencePrice = difference * selectedItem.getWholesalePrice();

            // عرض النتيجة
            diff_stockTakingForm.setText(String.valueOf(difference));
            totalSettlement_stockTakingForm.setText(String.valueOf(differencePrice));

        } catch (Exception e) {
            al.showAlert("خطأ", "حدث خطأ أثناء حساب الفروق", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void insertSettlementBond(SettlementBondData bondData) {
        String sql = "INSERT INTO settlement_bonds (total_settlement_amount, difference, actual_balance, book_balance, warehouse, type, item_name, item_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql)) {

            ps.setDouble(1, bondData.getTotalSettlementAmount());
            ps.setDouble(2, bondData.getDifference());
            ps.setDouble(3, bondData.getActualBalance());
            ps.setDouble(4, bondData.getBookBalance());
            ps.setString(5, bondData.getWarehouseName());
            ps.setString(6, bondData.getItemType());
            ps.setString(7, bondData.getItemName());
            ps.setInt(8, bondData.getItemId());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Settlement bond inserted successfully.");
            }

        } catch (SQLException e) {
            al.showAlert("خطأ", "حدث خطأ اثناء حفظ سند تسوية المخززن", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void inventoryUpdateBtn(int itemId, double actualQuantity) {
        String updateData = "UPDATE product SET "
                + "mNumberavailable = ?"
                + " WHERE id = ?";

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(updateData)) {

            ps.setDouble(1, actualQuantity);
            ps.setInt(2, itemId);

            ps.executeUpdate();

        } catch (SQLException e) {
            al.showAlert("خطأ", "حدث خطأ أثناء تعديل كمية المنتج الفعلية", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void clearSettlementBond() {
        stockTakingTable.getSelectionModel().clearSelection();

        searchWarehouseList_stockTakingForm.getSelectionModel().clearSelection();
        itemTypeListSearch_stockTakingForm.getSelectionModel().clearSelection();
        stockTaking_Search.clear();
        itemName_stockTakingForm.clear();
        bookQuantity_stockTakingForm.clear();
        diff_stockTakingForm.clear();
        totalSettlement_stockTakingForm.clear();
        actualQuantity_stockTakingForm.clear();
        stockItemShowData();
    }

    public void saveSettlementBond() {
        try {
            // تحقق من اختيار الصنف
            productData selectedItem = stockTakingTable.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                al.showAlert("تحذير", "حدد الصنف من الجدول أولاً", Alert.AlertType.WARNING);
                return;
            }

            // تحقق من إدخال الكمية الفعلية
            String actualQuantityText = actualQuantity_stockTakingForm.getText().trim();
            if (actualQuantityText.isEmpty()) {
                al.showAlert("خطأ", "أدخل الكمية الفعلية", Alert.AlertType.ERROR);
                return;
            }

            double actualQuantity;
            try {
                actualQuantity = Double.parseDouble(actualQuantityText);
            } catch (NumberFormatException e) {
                al.showAlert("خطأ", "الكمية الفعلية يجب أن تكون أرقام", Alert.AlertType.ERROR);
                return;
            }

            if (Double.parseDouble(actualQuantity_stockTakingForm.getText().trim()) < 0.0) {
                al.showAlert("خطأ", "الكمية الفعلية يجب أن تكون أرقام", Alert.AlertType.ERROR);
                return;
            }

            // الحسابات
            double bookQuantity = selectedItem.getMNumberavailable();
            double difference = actualQuantity - bookQuantity;
            double differencePrice = difference * selectedItem.getWholesalePrice();

            // إنشاء الكائن
            SettlementBondData bondData = new SettlementBondData(
                    differencePrice,
                    difference,
                    actualQuantity,
                    selectedItem.getMNumberavailable(),
                    selectedItem.getWareHouse(),
                    selectedItem.getType(),
                    selectedItem.getModel(),
                    selectedItem.getId()
            );

            insertSettlementBond(bondData);
            inventoryUpdateBtn(selectedItem.getId(), actualQuantity);
            clearSettlementBond();

            al.showAlert("نجاح", "تم حفظ قيد الجرد بنجاح", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            al.showAlert("خطأ", "حدث خطأ أثناء حفظ قيد الجرد", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////////////////// ترحيل مخزون /////////////////////////////////
    @FXML
    private ComboBox<String> searchWarehouseList_transferForm;
    @FXML
    private ComboBox<String> itemTypeListSearch_transferForm;
    @FXML
    private ComboBox<String> toWarehouseList_transferForm;
    @FXML
    private TextField itemSearch_transferForm;
    @FXML
    private TextField itemName__transferForm;
    @FXML
    private TextField quantity_transferForm;
    @FXML
    private TextField currentWarehouse_transferForm;
    @FXML
    private TableView<productData> itemsTable_transferForm;
    @FXML
    private TableColumn<productData, Integer> itemsTransfer_col_id;
    @FXML
    private TableColumn<productData, String> itemsTransfer_col_model;
    @FXML
    private TableColumn<productData, Double> itemsTransfer_col_quantity;
    @FXML
    private TableColumn<productData, Double> itemsTransfer_col_wholesalePrice;
    @FXML
    private TableColumn<productData, Double> itemsTransfer_col_realPrice;
    @FXML
    private TableColumn<productData, String> itemsTransfer_col_type;
    @FXML
    private TableColumn<productData, String> itemsTransfer_col_supplier;
    @FXML
    private TableColumn<productData, String> itemsTransfer_col_invoicesId;
    @FXML
    private TableColumn<productData, String> itemsTransfer_col_warehouse;

    public ObservableList<productData> itemsTransferDataList() {
        ObservableList<productData> listData = FXCollections.observableArrayList();

        String warehouse = searchWarehouseList_transferForm.getSelectionModel().getSelectedItem();
        String type = itemTypeListSearch_transferForm.getSelectionModel().getSelectedItem();
        String searchText = itemSearch_transferForm.getText() != null ? itemSearch_transferForm.getText().trim() : "";

        StringBuilder sql = new StringBuilder("SELECT * FROM product WHERE 1=1");
        List<String> params = new ArrayList<>();

        if (warehouse != null && !warehouse.isEmpty()) {
            sql.append(" AND warehouse LIKE ?");
            params.add("%" + warehouse + "%");
        }
        if (type != null && !type.isEmpty()) {
            sql.append(" AND type LIKE ?");
            params.add("%" + type + "%");
        }
        if (!searchText.isEmpty()) {
            sql.append(" AND (model LIKE ? OR CAST(id AS TEXT) LIKE ? OR suppliers LIKE ?)");
            for (int i = 0; i < 3; i++) {
                params.add("%" + searchText + "%");
            }
        }

        sql.append(" ORDER BY model COLLATE NOCASE");

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setString(i + 1, params.get(i));
            }

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productData prodData = new productData(
                            rs.getInt("id"),
                            rs.getString("model"),
                            rs.getDouble("mNumberavailable"),
                            rs.getDouble("wholesaleprice"),
                            rs.getDouble("realprice"),
                            rs.getString("type"),
                            rs.getInt("soldNo")
                    );
                    prodData.setWareHouse(rs.getString("warehouse"));
                    listData.add(prodData);
                }
            }

        } catch (SQLException e) {
            al.showAlert("خطأ", "حدث خطأ أثناء تحميل بيانات الجرد", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return listData;
    }

    private ObservableList<productData> itemsTransfer;

    public void itemsTransferShowData() {
        itemsTransfer = loadTransferDataList();

        itemsTransfer_col_realPrice.setCellValueFactory(new PropertyValueFactory<>("realPrice"));
        itemsTransfer_col_wholesalePrice.setCellValueFactory(new PropertyValueFactory<>("wholesalePrice"));
        itemsTransfer_col_quantity.setCellValueFactory(new PropertyValueFactory<>("mNumberavailable"));
        itemsTransfer_col_warehouse.setCellValueFactory(new PropertyValueFactory<>("wareHouse"));
        itemsTransfer_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        itemsTransfer_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        itemsTransfer_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        itemsTransfer_col_invoicesId.setCellValueFactory(new PropertyValueFactory<>("invoicesID"));
        itemsTransfer_col_supplier.setCellValueFactory(new PropertyValueFactory<>("suppliers"));

        itemsTable_transferForm.setItems(itemsTransfer);

        func.setupTableSearchFilter(itemSearch_transferForm, itemsTable_transferForm, itemsTransfer,
                Arrays.asList("id", "model", "type", "suppliers",
                        "invoicesID", "wareHouse"));
    }

    public void itemsTransferSelectData() {
        productData selectedItem = itemsTable_transferForm.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            itemName__transferForm.setText(selectedItem.getModel());
            quantity_transferForm.setText(String.valueOf(selectedItem.getMNumberavailable()));
            currentWarehouse_transferForm.setText(String.valueOf(selectedItem.getWareHouse()));
        }
    }

    public void itemsTransferClear() {
        itemName__transferForm.clear();
        quantity_transferForm.clear();
        currentWarehouse_transferForm.clear();
        toWarehouseList_transferForm.getSelectionModel().clearSelection();
        itemsTable_transferForm.getSelectionModel().clearSelection();
        itemSearch_transferForm.clear();
        searchWarehouseList_transferForm.getSelectionModel().clearSelection();
        itemTypeListSearch_transferForm.getSelectionModel().clearSelection();
        itemsTransferShowData();
    }

    public void transferItemBetweenWarehouses() {
        productData selectedItem = itemsTable_transferForm.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            al.showAlert("تحذير", "حدد الصنف من الجدول أولاً", Alert.AlertType.WARNING);
            return;
        }

        String targetWarehouse = toWarehouseList_transferForm.getSelectionModel().getSelectedItem();
        if (targetWarehouse == null || targetWarehouse.isEmpty()) {
            al.showAlert("تحذير", "حدد المخزن المراد النقل إليه", Alert.AlertType.WARNING);
            return;
        }

        if (targetWarehouse.equals(selectedItem.getWareHouse())) {
            al.showAlert("تحذير", "لا يمكن النقل لنفس المخزن", Alert.AlertType.WARNING);
            return;
        }

        double transferQty;
        try {
            transferQty = Double.parseDouble(quantity_transferForm.getText());
        } catch (NumberFormatException e) {
            al.showAlert("خطأ", "أدخل كمية صحيحة", Alert.AlertType.ERROR);
            return;
        }

        if (transferQty <= 0 || transferQty > selectedItem.getMNumberavailable()) {
            al.showAlert("خطأ", "الكمية غير صالحة أو أكبر من المتاح", Alert.AlertType.ERROR);
            return;
        }

        try ( Connection conn = database.getConnection()) {
            conn.setAutoCommit(false);

            // 1️⃣ خصم الكمية من المخزن الحالي
            try ( PreparedStatement ps = conn.prepareStatement(
                    "UPDATE product SET mNumberavailable = mNumberavailable - ? WHERE id = ? AND warehouse = ?")) {
                ps.setDouble(1, transferQty);
                ps.setInt(2, selectedItem.getId());
                ps.setString(3, selectedItem.getWareHouse());
                ps.executeUpdate();
            }

            // 2️⃣ إضافة الكمية للمخزن الجديد (أو إنشاء سجل جديد إذا مش موجود)
            try ( PreparedStatement check = conn.prepareStatement(
                    "SELECT id FROM product WHERE model = ? AND warehouse = ?")) {
                check.setString(1, selectedItem.getModel());
                check.setString(2, targetWarehouse);
                try ( ResultSet rs = check.executeQuery()) {
                    if (rs.next()) {
                        // الصنف موجود بالفعل → تعديل الكمية
                        try ( PreparedStatement ps = conn.prepareStatement(
                                "UPDATE product SET mNumberavailable = mNumberavailable + ? WHERE id = ?")) {
                            ps.setDouble(1, transferQty);
                            ps.setInt(2, rs.getInt("id"));
                            ps.executeUpdate();
                        }
                    } else {
                        // الصنف غير موجود → إضافة سجل جديد
                        try ( PreparedStatement ps = conn.prepareStatement(
                                "INSERT INTO product (model, mNumberavailable, wholesaleprice, realprice, type, suppliers, invoicesID, warehouse) "
                                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
                            ps.setString(1, selectedItem.getModel());
                            ps.setDouble(2, transferQty);
                            ps.setDouble(3, selectedItem.getWholesalePrice());
                            ps.setDouble(4, selectedItem.getRealPrice());
                            ps.setString(5, selectedItem.getType());
                            ps.setString(6, selectedItem.getSuppliers());
                            ps.setInt(7, selectedItem.getInvoicesID());
                            ps.setString(8, targetWarehouse);
                            ps.executeUpdate();
                        }
                    }
                }
            }

            // 3️⃣ تسجيل الحركة في جدول الحركات
            // تسجيل حركة الدخول للمخزن الجديد
            try ( PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item_movements (movementType, quantity, invoiceId, date, remark, productId, productName, warehouse) "
                    + "VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?)")) {

                ps.setString(1, "دخول");
                ps.setDouble(2, transferQty);
                ps.setNull(3, Types.INTEGER); // مفيش فاتورة مرتبطة
                ps.setString(4, "نقل من : " + selectedItem.getWareHouse());
                ps.setInt(5, selectedItem.getId());
                ps.setString(6, selectedItem.getModel());
                ps.setString(7, targetWarehouse);
                ps.executeUpdate();
            }
            // تسجيل حركة الخروج من المخزن الحالي
            try ( PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item_movements (movementType, quantity, invoiceId, date, remark, productId, productName, warehouse) "
                    + "VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?)")) {

                ps.setString(1, "خروج");
                ps.setDouble(2, transferQty);
                ps.setNull(3, Types.INTEGER); // مفيش فاتورة مرتبطة
                ps.setString(4, "نقل إلى : " + targetWarehouse);
                ps.setInt(5, selectedItem.getId());
                ps.setString(6, selectedItem.getModel());
                ps.setString(7, selectedItem.getWareHouse());
                ps.executeUpdate();
            }

            conn.commit();
            al.showAlert("تم", "تم ترحيل الكمية بنجاح", Alert.AlertType.INFORMATION);

            // تحديث الجدول بعد الترحيل
            itemsTransferShowData();

        } catch (SQLException e) {
            e.printStackTrace();
            al.showAlert("خطأ", "حدث خطأ أثناء الترحيل", Alert.AlertType.ERROR);
        }
    }

    /////////////////////////////////////////////// جدول المخزن في الاعدادات ///////////////////////
    @FXML
    private TableView<WarehouseData> warehouseTable;
    @FXML
    private TableColumn<WarehouseData, Integer> col_warehouseId;
    @FXML
    private TableColumn<WarehouseData, String> col_warehouseName;
    @FXML
    private TableColumn<WarehouseData, String> col_wareHouseRemark;
    @FXML
    private TextField warehouseNameField;
    @FXML
    private TextField warehouseRemarkTextField;

    public void loadWarehouseData() {
        ObservableList<WarehouseData> list = FXCollections.observableArrayList();

        String sql = "SELECT * FROM warehouse ORDER BY id DESC";

        try ( Connection conn = database.getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new WarehouseData(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("remark"),
                        rs.getString("date")
                ));
            }

            col_warehouseId.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_warehouseName.setCellValueFactory(new PropertyValueFactory<>("name"));
            col_wareHouseRemark.setCellValueFactory(new PropertyValueFactory<>("remark"));

            warehouseTable.setItems(list);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addWarehouse() {
        String name = warehouseNameField.getText(); // TextField لاسم المخزن
        String remark = warehouseRemarkTextField.getText(); // TextField للملاحظات

        if (name == null || name.trim().isEmpty()) {
            al.showAlert("تحذير", "من فضلك أدخل اسم المخزن", Alert.AlertType.WARNING);
            return;
        }

        try ( Connection conn = database.getConnection()) {

            // التحقق إذا كان الاسم موجود مسبقًا
            String checkSql = "SELECT COUNT(*) FROM warehouse WHERE name = ?";
            try ( PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
                checkPs.setString(1, name.trim());
                try ( ResultSet rs = checkPs.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        al.showAlert("تحذير", "هذا المخزن موجود بالفعل", Alert.AlertType.WARNING);
                        return;
                    }
                }
            }

            // إضافة المخزن الجديد
            String insertSql = "INSERT INTO warehouse (name, remark) VALUES (?, ?)";
            try ( PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setString(1, name.trim());
                ps.setString(2, remark != null ? remark.trim() : "");
                ps.executeUpdate();
            }

            al.showAlert("نجاح", "تمت إضافة المخزن بنجاح", Alert.AlertType.INFORMATION);

            loadWarehouseData(); // إعادة تحميل البيانات في الجدول

            warehouseRemarkTextField.clear();
            warehouseNameField.clear();

        } catch (SQLException e) {
            al.showAlert("خطأ", "حدث خطأ أثناء إضافة المخزن", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void warehouseSelectData() {
        WarehouseData selected = warehouseTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            warehouseNameField.setText(selected.getName());
            warehouseRemarkTextField.setText(selected.getRemark());
        }
    }

    public void deleteWarehouse() {
        WarehouseData selected = warehouseTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            al.showAlert("تحذير", "من فضلك اختر المخزن الذي تريد حذفه", Alert.AlertType.WARNING);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("تأكيد الحذف");
        confirm.setHeaderText(null);
        confirm.setContentText("هل أنت متأكد أنك تريد حذف المخزن: " + selected.getName() + " ؟");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String sql = "DELETE FROM warehouse WHERE id = ?";

            try ( Connection conn = database.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, selected.getId());
                ps.executeUpdate();

                al.showAlert("نجاح", "تم حذف المخزن بنجاح", Alert.AlertType.INFORMATION);

                loadWarehouseData(); // إعادة تحميل الجدول
                warehouseRemarkTextField.clear();
                warehouseNameField.clear();

            } catch (SQLException e) {
                al.showAlert("خطأ", "حدث خطأ أثناء حذف المخزن", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    public void purchaseDetailShowData() {

    }

    public void switchFrom(ActionEvent event) {
        if (event.getSource() == mainPage_btn) {
            main_form.setVisible(true);
            menu_form.setVisible(false);
            addProduct_form.setVisible(false);
            //returned_form.setVisible(false);
//            // product_form.setVisible(false);
//            sales_form.setVisible(false);
            expenses_form.setVisible(false);
            capital_form.setVisible(false);
//            // clients_form.setVisible(false);
            settings_form.setVisible(false);
            wareHouseForm.setVisible(false);

            mainPage();
            showCapital();
            inventoryShowData();
            departmentShowData();
        } else if (event.getSource() == sales_btn) {
            main_form.setVisible(false);
            menu_form.setVisible(true);
            addProduct_form.setVisible(false);
            //returned_form.setVisible(false);//
//            // product_form.setVisible(false);//
//            sales_form.setVisible(false);
            expenses_form.setVisible(false);
            capital_form.setVisible(false);
//            // clients_form.setVisible(false);//
            settings_form.setVisible(false);
            wareHouseForm.setVisible(false);

            returnClintTable();
            menuRemoveBtn();
            loadDepartmentsIntoComboBox();
            deferredInvoiceShowData();
            returnMethodList.getSelectionModel().clearSelection();
            returnsShowData();
            returnMethodList();
            returnClintList();
            returnRemoveBtn();
            showSalesDataforSales();
            showInvoicesData();
            fromDatePicker.setValue(null);
            toDatePicker.setValue(null);
            itemTypeList_salesForm.getSelectionModel().clearSelection();
            invoice_search.clear();
            salesModelID_search.clear();
        } else if (event.getSource() == purches_btn) {
            main_form.setVisible(false);
            menu_form.setVisible(false);
            addProduct_form.setVisible(true);
            //returned_form.setVisible(false);
//            // product_form.setVisible(false);
//            sales_form.setVisible(false);
            expenses_form.setVisible(false);
            capital_form.setVisible(false);
//            // clients_form.setVisible(false);
            settings_form.setVisible(false);
            wareHouseForm.setVisible(false);

            inventoryShowData();
            inventoryClearBtn();
            backBtn_toPurchaseForm();
            loadPurchaseInvoices();
            loadSuppliers();
            missingProductShowData();
//            loadTypesIntoComboBox();
//            loadSuppliersIntoComboBox();

//        } else if (event.getSource() == inventory_btn) {
//            main_form.setVisible(false);
//            menu_form.setVisible(false);
//            addProduct_form.setVisible(false);
//            //returned_form.setVisible(false);
//            product_form.setVisible(true);
////            sales_form.setVisible(false);
//            expenses_form.setVisible(false);
//            capital_form.setVisible(false);
//            // clients_form.setVisible(false);
//            settings_form.setVisible(false);
//
//            missingProductShowData();
//            returnsShowData();
        } else if (event.getSource() == expenses_btn) {
            main_form.setVisible(false);
            menu_form.setVisible(false);
            addProduct_form.setVisible(false);
            //returned_form.setVisible(false);
            // product_form.setVisible(false);
//            sales_form.setVisible(false);

            expenses_form.setVisible(true);
            capital_form.setVisible(false);
            // clients_form.setVisible(false);
            settings_form.setVisible(false);
            wareHouseForm.setVisible(false);

            showExpensesData();
            clearEeForm();
            clearReceiptVoucherFields();
        } else if (event.getSource() == capital_btn) {
            main_form.setVisible(false);
            menu_form.setVisible(false);
            addProduct_form.setVisible(false);
            //returned_form.setVisible(false);
            // product_form.setVisible(false);
//            sales_form.setVisible(false);
            expenses_form.setVisible(false);
            capital_form.setVisible(true);
            // clients_form.setVisible(false);
            settings_form.setVisible(false);
            wareHouseForm.setVisible(false);

            showCapital();
//        } else if (event.getSource() == clients_btn) {
//            main_form.setVisible(false);
//            menu_form.setVisible(false);
//            addProduct_form.setVisible(false);
//            //returned_form.setVisible(false);
//            // product_form.setVisible(false);
////            sales_form.setVisible(false);
//            expenses_form.setVisible(false);
//            capital_form.setVisible(false);
////            clients_form.setVisible(true);
//            settings_form.setVisible(false);
//
//            fromDatePickerClient.setValue(null);
//            toDatePickerClient.setValue(null);
////            loadClientsInvoices();
        } else if (event.getSource() == settings_btn) {
            main_form.setVisible(false);
            menu_form.setVisible(false);
            addProduct_form.setVisible(false);
            //returned_form.setVisible(false);
            // product_form.setVisible(false);
//            sales_form.setVisible(false);
            expenses_form.setVisible(false);
            capital_form.setVisible(false);
            // clients_form.setVisible(false);
            settings_form.setVisible(true);
            wareHouseForm.setVisible(false);

            employeddShowData();
            loadWarehouseData();
        } else if (event.getSource() == warehouses_Btn) {
            main_form.setVisible(false);
            menu_form.setVisible(false);
            addProduct_form.setVisible(false);
            //returned_form.setVisible(false);
            // product_form.setVisible(false);
//            sales_form.setVisible(false);
            expenses_form.setVisible(false);
            capital_form.setVisible(false);
            // clients_form.setVisible(false);
            settings_form.setVisible(false);
            wareHouseForm.setVisible(true);

        }
    }

    public void logout() {
        try {
            al.Confirmation_AlertOptions("هل انت متاكد من تسجيل الخروج ؟");
            Optional<ButtonType> option = al.getAlert().showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                // TO HIDE MAIN FORM
                logout_btn.getScene().getWindow().hide();

                // LINK YOUR LOGIN FORM AND SHOW IT
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Inventory System");
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            al.E_Alert("حدث خطأ أثناء تسجيل الخروج", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void displayUsername() {
        String user = data.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        username.setText(user);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showCapital();
        displayUsername();
        inventoryShowData();
        statues_List();
        loadSuppliersIntoComboBox();
        loadDepartmentsIntoComboBox();
        warehouseListmenueShowData();

        searchByName.textProperty().addListener((obs, oldVal, newVal) -> searchExpenses());
        disqaunt_textField.textProperty().addListener((obs, oldVal, newVal) -> func.finalizeInvoice(SalesList, disqaunt_textField, menu_total));
        searchEmployee.textProperty().addListener((obs, oldVal, newVal) -> searchEmployeeFromDB());
        bounds_Search.textProperty().addListener((obs, oldVal, newVal) -> settlementBondsShowData());
        SearchMovementItems_itemsMovementForm.textProperty().addListener((obs, oldVal, newVal) -> searchItemsMovementByDateRange());

        totalRealPriceToday();
        Safe_main.setText(String.format("%.2f $", data.MoneySafe));
        unavailable_main.setText(data.UnavailbleProductCount + "");
        available_main.setText(data.AvailbleProductCount + "");
    }

}
