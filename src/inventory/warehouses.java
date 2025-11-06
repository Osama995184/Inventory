/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import services.alert;
import services.functions;

/**
 *
 * @author Osama
 */
public class warehouses {

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

    private Alert alert;
    alert al = new alert();
    functions func = new functions();

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

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql.toString())) {

            // Bind parameters
            for (int i = 0; i < params.size(); i++) {
                ps.setString(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productData prodData = new productData(
                            rs.getInt("id"),
                            rs.getString("model"),
                            rs.getDouble("mNumberavailable"),
                            rs.getDouble("wholesaleprice"),
                            rs.getDouble("realprice"),
                            rs.getString("type"),
                            rs.getInt("soldNo"),
                            rs.getString("unit")
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

    public void printCurrentStockReport() {
        String warehouse = searchWarehouseList_currentStockBalance.getSelectionModel().getSelectedItem();

        try (Connection connect = database.getConnection()) {

            // Load the compiled Jasper report
            InputStream reportStream = getClass().getResourceAsStream("/reports/CurrentStockReport.jasper");

            if (reportStream == null) {
                al.showAlert("خطأ", "لم يتم العثور على تقرير المخزون", Alert.AlertType.ERROR);
                return;
            }

            // إعداد باراميتر المخزن
            Map<String, Object> params = new HashMap<>();
            params.put("warehouse", warehouse); // لو عايز كل المخازن ممكن تبعت null

            // Fill the report with data from DB
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, params, connect);

            // عرض التقرير
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle("تقرير رصيد المخزون الحالي");
            viewer.setVisible(true);

        } catch (Exception e) {
            al.showAlert("خطأ", "حدث خطأ أثناء طباعة التقرير", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
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

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setString(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productData prodData = new productData(
                            rs.getInt("id"),
                            rs.getString("model"),
                            rs.getDouble("mNumberavailable"),
                            rs.getDouble("wholesaleprice"),
                            rs.getDouble("realprice"),
                            rs.getString("type"),
                            rs.getInt("soldNo"),
                            rs.getString("unit")
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
        items_col_total.setCellValueFactory(new PropertyValueFactory<>("total"));

        itemsTable_itemsMovementForm.setItems(currentItems);

        func.setupTableSearchFilter(SearchItems_itemsMovementForm, itemsTable_itemsMovementForm, currentItems,
                Arrays.asList("id", "model", "type", "suppliers",
                        "invoicesID", "wareHouse"));
    }

    public void searchItemsMovementByDateRange() {
        LocalDate fromDate = fromDatePicker_itemsMovementForm.getValue();
        LocalDate toDate = toDatePicker_itemsMovementForm.getValue();
        String searchText = SearchMovementItems_itemsMovementForm.getText() != null
                ? SearchMovementItems_itemsMovementForm.getText().trim()
                : "";

        ObservableList<itemMovementData> movements = FXCollections.observableArrayList();

        StringBuilder sql = new StringBuilder(
                "SELECT * FROM item_movements WHERE 1=1"
        );
        List<Object> params = new ArrayList<>();

        // Filter by date range
        if (fromDate != null) {
            sql.append(" AND date >= ?");
            params.add(Date.valueOf(fromDate));
        }
        if (toDate != null) {
            sql.append(" AND date <= ?");
            params.add(Date.valueOf(toDate));
        }

        // Filter by search text
        if (!searchText.isEmpty()) {
            sql.append(" AND (CAST(movementId AS TEXT) LIKE ? OR CAST(invoiceId AS TEXT) LIKE ? OR movementType LIKE ? OR remark LIKE ?)");
            for (int i = 0; i < 4; i++) {
                params.add("%" + searchText + "%");
            }
        }

        sql.append(" ORDER BY date DESC");

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
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
                            rs.getString("productName"),
                            rs.getString("unit")
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

        int productId = prodData.getId();

        String sql = "SELECT * FROM item_movements WHERE productId = ? ORDER BY date DESC";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql)) {

            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
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
                            rs.getString("productName"),
                            rs.getString("unit")
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

    public void printItemsMovementReport() {
        String warehouse = warehouseList_itemsMovementForm.getSelectionModel().getSelectedItem();
        LocalDate fromDate = fromDatePicker_itemsMovementForm.getValue();
        LocalDate toDate = toDatePicker_itemsMovementForm.getValue();

        try (Connection connect = database.getConnection()) {
            InputStream reportStream = getClass().getResourceAsStream("/reports/ItemsMovementReport.jasper");

            if (reportStream == null) {
                al.showAlert("خطأ", "لم يتم العثور على تقرير حركة الأصناف", Alert.AlertType.ERROR);
                return;
            }

            Map<String, Object> params = new HashMap<>();
            params.put("warehouse", warehouse);
            params.put("fromDate", fromDate != null ? Date.valueOf(fromDate) : null);
            params.put("toDate", toDate != null ? Date.valueOf(toDate) : null);

            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, params, connect);

            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle("تقرير حركة الأصناف");
            viewer.setVisible(true);

        } catch (Exception e) {
            al.showAlert("خطأ", "حدث خطأ أثناء طباعة التقرير", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

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
            sql.append(" AND date >= ?");
            params.add(Date.valueOf(fromDate));
        }
        if (toDate != null) {
            sql.append(" AND date <= ?");
            params.add(Date.valueOf(toDate));
        }

        // Filter by search text
        if (!searchText.isEmpty()) {
            sql.append(" AND (item_name LIKE ? OR CAST(bond_number AS TEXT) LIKE ? OR warehouse LIKE ? OR type LIKE ? OR CAST(item_id AS TEXT) LIKE ?)");
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
        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SettlementBondData bond = new SettlementBondData(
                            rs.getDouble("total_settlement_amount"),
                            rs.getDouble("difference"),
                            rs.getDouble("actual_balance"),
                            rs.getDouble("book_balance"),
                            rs.getString("warehouse"),
                            rs.getString("type"),
                            rs.getString("item_name"),
                            rs.getInt("item_id"),
                            rs.getString("unit")
                    );
                    bondList.add(bond);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bondList;
    }

    public void settlementBondsShowData() {
        ObservableList<SettlementBondData> settlementList = loadSettlementBondsToTable();

        settlementBonds_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        settlementBonds_col_totalSettlement.setCellValueFactory(new PropertyValueFactory<>("totalSettlementAmount"));
        settlementBonds_col_differenceSettlement.setCellValueFactory(new PropertyValueFactory<>("difference"));
        settlementBonds_col_actualBalance.setCellValueFactory(new PropertyValueFactory<>("actualBalance"));
        settlementBonds_col_bookBalance.setCellValueFactory(new PropertyValueFactory<>("bookBalance"));
        settlementBonds_col_warehouse.setCellValueFactory(new PropertyValueFactory<>("warehouseName"));
        settlementBonds_col_type.setCellValueFactory(new PropertyValueFactory<>("itemType"));
        settlementBonds_col_mode.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        settlementBonds_col_id.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        settlementBonds_col_boundId.setCellValueFactory(new PropertyValueFactory<>("bondNumber"));

        settlementBondsTable.setItems(settlementList);
    }

    public void printSettlementBondsReport() {
        // القيم من واجهة المستخدم
        String warehouse = searchWarehouseList_boundsForm.getSelectionModel().getSelectedItem();
        String item = bounds_Search.getText() != null ? bounds_Search.getText().trim() : "";
        LocalDate fromDate = fromDatePicker_boundsForm.getValue();
        LocalDate toDate = toDatePicker_boundsForm.getValue();

        // بناء الاستعلام
        StringBuilder sql = new StringBuilder("SELECT * FROM settlement_bonds WHERE 1=1");
        List<Object> params = new ArrayList<>();

        // تصفية حسب التاريخ
        if (fromDate != null) {
            sql.append(" AND date >= ?");
            params.add(Date.valueOf(fromDate));
        }
        if (toDate != null) {
            sql.append(" AND date <= ?");
            params.add(Date.valueOf(toDate));
        }

        // تصفية حسب اسم المخزن
        if (warehouse != null && !warehouse.isEmpty()) {
            sql.append(" AND warehouse LIKE ?");
            params.add("%" + warehouse + "%");
        }

        // تصفية حسب اسم/كود البند
        if (!item.isEmpty()) {
            sql.append(" AND (item_name LIKE ? OR CAST(item_id AS TEXT) LIKE ?)");
            params.add("%" + item + "%");
            params.add("%" + item + "%");
        }

        sql.append(" ORDER BY date DESC");

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql.toString())) {

            // تمرير الباراميترات
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            // تنفيذ الاستعلام
            ResultSet rs = ps.executeQuery();

            // تعبئة مصدر البيانات لـ Jasper
            JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);

            // تحميل ملف التصميم
            InputStream reportStream = getClass().getResourceAsStream("/reports/settlement_bonds_report.jasper");
            if (reportStream == null) {
                System.out.println("Report file not found.");
                return;
            }

            // تمرير الباراميترات لـ Jasper (يمكنك إضافة اسم الشركة مثلاً)
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("warehouse_filter", warehouse != null ? warehouse : "All");
            parameters.put("item_filter", !item.isEmpty() ? item : "All");
            parameters.put("from_date", fromDate != null ? fromDate.toString() : "Not Set");
            parameters.put("to_date", toDate != null ? toDate.toString() : "Not Set");

            // عرض التقرير
            JasperPrint print = JasperFillManager.fillReport(reportStream, parameters, resultSetDataSource);
            JasperViewer.viewReport(print, false);

        } catch (SQLException | JRException e) {
            e.printStackTrace();
        }
    }

    public void goToStockTakingForm() {
        stock_settlement_bonds_form.setVisible(false);
        stockTaking_form.setVisible(true);
    }

    public void backFromStockTakingForm() {
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

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setString(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productData prodData = new productData(
                            rs.getInt("id"),
                            rs.getString("model"),
                            rs.getDouble("mNumberavailable"),
                            rs.getDouble("wholesaleprice"),
                            rs.getDouble("realprice"),
                            rs.getString("type"),
                            rs.getInt("soldNo"),
                            rs.getString("unit")
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

    public void inventoryItemShowData() {
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

            // الحسابات
            double bookQuantity = selectedItem.getMNumberavailable();
            double difference = bookQuantity - actualQuantity;
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

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql)) {

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
        }
    }

    public void inventoryUpdateBtn(int itemId, double actualQuantity) {
        String updateData = "UPDATE product SET "
                + "mNumberavailable = ?, "
                + "WHERE id = ?";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(updateData)) {

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

            // الحسابات
            double bookQuantity = selectedItem.getMNumberavailable();
            double difference = bookQuantity - actualQuantity;
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
                    selectedItem.getId(),
                    selectedItem.getUnit()
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

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setString(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productData prodData = new productData(
                            rs.getInt("id"),
                            rs.getString("model"),
                            rs.getDouble("mNumberavailable"),
                            rs.getDouble("wholesaleprice"),
                            rs.getDouble("realprice"),
                            rs.getString("type"),
                            rs.getInt("soldNo"),
                            rs.getString("unit")
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
        itemsTransfer = loadStockTakingDataList();

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
        itemsTable_transferForm.getSelectionModel().clearSelection();
        itemSearch_transferForm.clear();
        searchWarehouseList_transferForm.getSelectionModel().clearSelection();
        toWarehouseList_transferForm.getSelectionModel().clearSelection();
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

        try (Connection conn = database.getConnection()) {
            conn.setAutoCommit(false);

            // 1️⃣ خصم الكمية من المخزن الحالي
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE product SET mNumberavailable = mNumberavailable - ? WHERE id = ? AND warehouse = ?")) {
                ps.setDouble(1, transferQty);
                ps.setInt(2, selectedItem.getId());
                ps.setString(3, selectedItem.getWareHouse());
                ps.executeUpdate();
            }

            // 2️⃣ إضافة الكمية للمخزن الجديد (أو إنشاء سجل جديد إذا مش موجود)
            try (PreparedStatement check = conn.prepareStatement(
                    "SELECT id FROM product WHERE model = ? AND warehouse = ?")) {
                check.setString(1, selectedItem.getModel());
                check.setString(2, targetWarehouse);
                try (ResultSet rs = check.executeQuery()) {
                    if (rs.next()) {
                        // الصنف موجود بالفعل → تعديل الكمية
                        try (PreparedStatement ps = conn.prepareStatement(
                                "UPDATE product SET mNumberavailable = mNumberavailable + ? WHERE id = ?")) {
                            ps.setDouble(1, transferQty);
                            ps.setInt(2, rs.getInt("id"));
                            ps.executeUpdate();
                        }
                    } else {
                        // الصنف غير موجود → إضافة سجل جديد
                        try (PreparedStatement ps = conn.prepareStatement(
                                "INSERT INTO product (model, mNumberavailable, wholesaleprice, realprice, type, suppliers, invoicesID, warehouse) "
                                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
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
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item_movements (movementType, quantity, invoiceId, date, remark, productId, productName, warehouse, unit) "
                    + "VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?)")) {

                ps.setString(1, "دخول");
                ps.setDouble(2, transferQty);
                ps.setNull(3, Types.INTEGER); // مفيش فاتورة مرتبطة
                ps.setString(4, "نقل من مخزن: " + selectedItem.getWareHouse());
                ps.setInt(5, selectedItem.getId());
                ps.setString(6, selectedItem.getModel());
                ps.setString(7, targetWarehouse);
                ps.setString(8, selectedItem.getUnit());
                ps.executeUpdate();
            }
            // تسجيل حركة الخروج من المخزن الحالي
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item_movements (movementType, quantity, invoiceId, date, remark, productId, productName, warehouse) "
                    + "VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?)")) {

                ps.setString(1, "خروج");
                ps.setDouble(2, transferQty);
                ps.setNull(3, Types.INTEGER); // مفيش فاتورة مرتبطة
                ps.setString(4, "نقل إلى مخزن: " + targetWarehouse);
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

        try (Connection conn = database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

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

        try (Connection conn = database.getConnection()) {

            // التحقق إذا كان الاسم موجود مسبقًا
            String checkSql = "SELECT COUNT(*) FROM warehouse WHERE name = ?";
            try (PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
                checkPs.setString(1, name.trim());
                try (ResultSet rs = checkPs.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        al.showAlert("تحذير", "هذا المخزن موجود بالفعل", Alert.AlertType.WARNING);
                        return;
                    }
                }
            }

            // إضافة المخزن الجديد
            String insertSql = "INSERT INTO warehouse (name, remark) VALUES (?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
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

            try (Connection conn = database.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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

}
