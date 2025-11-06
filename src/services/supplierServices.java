/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import inventory.AccountStatement;
import inventory.InvoiceModel;
import inventory.ReturnedItemsToSuppliers;
import inventory.Returns;
import inventory.Supplier;
import inventory.database;
import inventory.productData;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author OSOS
 */
public class supplierServices {

    alert al = new alert();

    public ObservableList<productData> inventoryDataList(ComboBox<String> itemTypeListSearch_addForm, ComboBox<String> itemWareHouseListSearch_addForm) {
        ObservableList<productData> listData = FXCollections.observableArrayList();

        String type = itemTypeListSearch_addForm.getSelectionModel().getSelectedItem();
        String stock = itemWareHouseListSearch_addForm.getSelectionModel().getSelectedItem();
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM product WHERE mNumberavailable > 0 ");
        List<String> conditions = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        if (type != null && !type.trim().isEmpty()) {
            conditions.add("\"type\" LIKE ? ");
            params.add("%" + type + "%");
        }
        if (stock != null && !stock.trim().isEmpty()) {
            conditions.add("\"wareHouse\" LIKE ? ");
            params.add("%" + stock + "%");
        }

        if (!conditions.isEmpty()) {
            sqlBuilder.append(" AND ");
            sqlBuilder.append(String.join(" AND ", conditions));
        }

        sqlBuilder.append(" ORDER BY model COLLATE NOCASE");

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sqlBuilder.toString())) {

            // Bind parameters
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                productData prodData = new productData(
                        rs.getInt("id"),
                        rs.getString("model"),
                        rs.getDouble("mNumberavailable"),
                        rs.getDouble("wholesaleprice"),
                        rs.getDouble("realprice"),
                        rs.getString("type"),
                        rs.getInt("soldNo"),
                        rs.getString("wareHouse"),
                        rs.getString("color"),
                        rs.getString("size"),
                        rs.getString("unit")
                );
                listData.add(prodData);
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء تحميل المنتجات: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return listData;
    }

    public ObservableList<ReturnedItemsToSuppliers> itemReturnDataList() {
        ObservableList<ReturnedItemsToSuppliers> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM returnItemPurchase ORDER BY model COLLATE NOCASE";

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ReturnedItemsToSuppliers retrunItems = new ReturnedItemsToSuppliers(
                        rs.getInt("id"),
                        rs.getInt("item_id"),
                        rs.getString("model"),
                        rs.getInt("mNumberReturn"),
                        rs.getDouble("wholesaleprice"),
                        rs.getString("type"),
                        rs.getString("suppliers"),
                        rs.getString("warehouse"),
                        rs.getString("date"),
                        rs.getString("color"),
                        rs.getString("size"),
                        rs.getString("unit"),
                        rs.getInt("invoicesID")
                );
                listData.add(retrunItems);
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ اثناء تحميل الاصناف المرتجعة", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return listData;
    }

    public ObservableList<productData> missingProductDataList() {
        ObservableList<productData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM product WHERE mNumberavailable <= 0 ORDER BY model COLLATE NOCASE";

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productData prodData = new productData(
                        rs.getInt("id"),
                        rs.getString("model"),
                        rs.getDouble("mNumberavailable"),
                        rs.getDouble("wholesaleprice"),
                        rs.getDouble("realprice"),
                        rs.getString("type"),
                        rs.getInt("soldNo"),
                        rs.getString("wareHouse"),
                        rs.getString("color"),
                        rs.getString("size"),
                        rs.getString("unit")
                );
                listData.add(prodData);
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء تحميل المنتجات الناقصة", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return listData;
    }

    public ObservableList<Returns> retrunProductDataList() {
        ObservableList<Returns> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM returns ORDER BY return_date DESC";

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Returns returns = new Returns(
                        rs.getInt("return_id"),
                        rs.getInt("product_id"),
                        rs.getInt("invoice_id"),
                        rs.getInt("quantity_returned"),
                        rs.getString("return_reason"),
                        rs.getString("return_date"),
                        rs.getDouble("price_of_return"),
                        rs.getString("model_name"),
                        rs.getString("model_color"),
                        rs.getString("model_size")
                );
                listData.add(returns);
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء تحميل بيانات المرتجعات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return listData;
    }

    public ObservableList<Supplier> loadSuppliersList() {
        ObservableList<Supplier> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM suppliers";

        try ( Connection conn = database.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql);  ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                list.add(new Supplier(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("remarks")
                ));
            }

        } catch (SQLException e) {
            al.E_Alert("فشل تحميل بيانات المورد" + e, Alert.AlertType.ERROR);
            e.printStackTrace();
        }
        return list;
    }

    public ObservableList<AccountStatement> accountStatementsDataList(ComboBox<String> SuppliersListAccountStatements, DatePicker fromDatePickerAccountStatement, DatePicker toDatePickerAccountStatement) {
        ObservableList<AccountStatement> listData = FXCollections.observableArrayList();

        String suppliers = SuppliersListAccountStatements.getSelectionModel().getSelectedItem();
        LocalDate from = fromDatePickerAccountStatement.getValue();
        LocalDate to = toDatePickerAccountStatement.getValue();

        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT \n"
                + "    p.id AS Invoice_id,\n"
                + "    p.supplier_name AS sup_name,\n"
                + "    p.invoice_date AS date,\n"
                + "    p.cash,\n"
                + "    p.deferred,\n"
                + "    (p.cash + p.deferred) AS totalInvoice,\n"
                + "    p.remarks,\n"
                + "    sup.phone,\n"
                + "    COALESCE(SUM(r.wholesaleprice * r.mNumberReturn), 0) AS totalReturn,\n"
                + "    COALESCE(SUM(r.mNumberReturn), 0) AS returnItem\n"
                + "FROM \n"
                + "    purchase_invoices AS p\n"
                + "LEFT JOIN \n"
                + "    suppliers AS sup ON sup.name = p.supplier_name\n"
                + "LEFT JOIN \n"
                + "    returnItemPurchase AS r ON r.invoicesID = p.id\n"
                + "WHERE 1 = 1");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (suppliers != null && !suppliers.isEmpty()) {
            sql.append(" AND p.supplier_name = ?");
            params.add(suppliers);
        }

        if (from != null) {
            sql.append(" AND p.invoice_date >= ?");
            params.add(from.format(formatter));
        }

        if (to != null) {
            sql.append(" AND p.invoice_date <= ?");
            params.add(to.format(formatter));
        }

        sql.append(" GROUP BY p.id, p.supplier_name, p.invoice_date, p.cash, p.deferred, p.remarks, sup.phone ORDER BY date DESC");
        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AccountStatement accountStatement = new AccountStatement(
                        rs.getInt("Invoice_id"),
                        rs.getInt("returnItem"),
                        rs.getString("sup_name"),
                        rs.getString("phone"),
                        rs.getString("date"),
                        rs.getString("remarks"),
                        rs.getDouble("cash"),
                        rs.getDouble("deferred"),
                        rs.getDouble("totalReturn"),
                        rs.getDouble("totalInvoice")
                );
                listData.add(accountStatement);
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ اثناء تحميل الفواتير", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return listData;
    }

    public ObservableList<InvoiceModel> loadPurchaseInvoicesList() {
        ObservableList<InvoiceModel> invoicesList = FXCollections.observableArrayList();

        String sql = "SELECT id, supplier_name, invoice_out_id ,invoice_date,(deferred + cash) AS total_price, cash, deferred, remarks FROM purchase_invoices ORDER BY invoice_date DESC";

        try ( Connection conn = database.getConnection();  Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                InvoiceModel invoice = new InvoiceModel(
                        rs.getInt("id"),
                        rs.getString("supplier_name"),
                        rs.getString("invoice_out_id"),
                        rs.getString("invoice_date"),
                        rs.getDouble("total_price"),
                        rs.getDouble("cash"),
                        rs.getDouble("deferred"),
                        rs.getString("remarks")
                );
                invoicesList.add(invoice);
            }
        } catch (SQLException e) {
            al.E_Alert("خطأ أثناء تحميل فواتير المشتريات:\n" + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
        return invoicesList;
    }

    public ObservableList<InvoiceModel> searchPurchBYDate(DatePicker fromDatePickerInvoices, DatePicker toDatePickerInvoices) {
        LocalDate from = fromDatePickerInvoices.getValue();
        LocalDate to = toDatePickerInvoices.getValue();

        ObservableList<InvoiceModel> results = FXCollections.observableArrayList();
        StringBuilder sql = new StringBuilder("SELECT id, supplier_name, invoice_out_id ,invoice_date,(deferred + cash) AS total_price, cash, deferred, remarks FROM purchase_invoices WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (from != null) {
            sql.append(" AND DATE(invoice_date) >= ?");
            params.add(from.toString());
        }
        if (to != null) {
            sql.append(" AND DATE(invoice_date) <= ?");
            params.add(to.toString());
        }

        try ( Connection conn = database.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                InvoiceModel invoice = new InvoiceModel(
                        rs.getInt("id"),
                        rs.getString("supplier_name"),
                        rs.getString("invoice_out_id"),
                        rs.getString("invoice_date"),
                        rs.getDouble("total_price"),
                        rs.getDouble("cash"),
                        rs.getDouble("deferred"),
                        rs.getString("remarks")
                );
                results.add(invoice);
            }
        } catch (SQLException e) {
            al.E_Alert("خطأ أثناء البحث بالتاريخ:\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
        return results;
    }

    public void addPurchaseInvoiceDetail(purchaseInvoiceDetail d) {
        String sql = "INSERT INTO purchase_invoice_details "
                + "(purchase_invoice_id, item_name, color, size, unit, warehouse, model, quantity, purchase_price, selling_price) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try ( Connection connect = database.getConnection()) {
            try ( PreparedStatement ps = connect.prepareStatement(sql)) {
                ps.setInt(1, d.getPurchaseInvoiceId());
                ps.setString(2, d.getItemName());
                ps.setString(3, d.getColor());
                ps.setString(4, d.getSize());
                ps.setString(5, d.getUnit());
                ps.setString(6, d.getWarehouse());
                ps.setString(7, d.getModel());
                ps.setDouble(8, d.getQuantity());
                ps.setDouble(9, d.getPurchasePrice());
                ps.setDouble(10, d.getSellingPrice());
                ps.executeUpdate();
            } catch (SQLException e) {
                al.E_Alert("حدث خطأ اثناء اضافة هذه التفاصيل", Alert.AlertType.ERROR);
                return;
            }

            String checkSql = "SELECT id, mNumberavailable FROM product "
                    + "WHERE model=? AND warehouse=? AND unit=? AND IFNULL(color,'')=? AND IFNULL(size,'')=? AND type=?";
            try ( PreparedStatement checkPs = connect.prepareStatement(checkSql)) {
                checkPs.setString(1, d.getItemName());
                checkPs.setString(2, d.getWarehouse());
                checkPs.setString(3, d.getUnit());
                checkPs.setString(4, d.getColor() == null ? "" : d.getColor());
                checkPs.setString(5, d.getSize() == null ? "" : d.getSize());
                checkPs.setString(6, d.getModel());

                try ( ResultSet rs = checkPs.executeQuery()) {
                    if (rs.next()) {
                        int productId = rs.getInt("id");
                        double oldQty = rs.getDouble("mNumberavailable");
                        double newQty = oldQty + d.getQuantity();

                        String updateSql = "UPDATE product SET mNumberavailable=? ,"
                                + " model=? ,"
                                + " warehouse=? ,"
                                + " wholesalePrice=? ,"
                                + " realPrice=? ,"
                                + " type=? ,"
                                + " color=? ,"
                                + " size=? ,"
                                + " unit=? "
                                + " WHERE id=?";
                        try ( PreparedStatement updatePs = connect.prepareStatement(updateSql)) {
                            updatePs.setDouble(1, newQty);
                            updatePs.setString(2, d.getItemName());
                            updatePs.setString(3, d.getWarehouse());
                            updatePs.setDouble(4, d.getSellingPrice());
                            updatePs.setDouble(5, d.getPurchasePrice());
                            updatePs.setString(6, d.getModel());
                            updatePs.setString(7, d.getColor());
                            updatePs.setString(8, d.getSize());
                            updatePs.setString(9, d.getUnit());
                            updatePs.setInt(10, productId);
                            updatePs.executeUpdate();
                        } catch (SQLException e) {
                            al.E_Alert("حدث خطأ اثناء تعديل المنتج", Alert.AlertType.ERROR);
                            return;
                        }

                    } else {
                        String insertProductSql = "INSERT INTO product "
                                + "(model, warehouse, mNumberavailable, wholesalePrice, realPrice, type, color, size, unit) "
                                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        try ( PreparedStatement insertPs = connect.prepareStatement(insertProductSql)) {
                            insertPs.setString(1, d.getItemName());
                            insertPs.setString(2, d.getWarehouse());
                            insertPs.setDouble(3, d.getQuantity());
                            insertPs.setDouble(4, d.getPurchasePrice());
                            insertPs.setDouble(5, d.getSellingPrice());
                            insertPs.setString(6, d.getModel());
                            insertPs.setString(7, d.getColor());
                            insertPs.setString(8, d.getSize());
                            insertPs.setString(9, d.getUnit());
                            insertPs.executeUpdate();
                        } catch (SQLException e) {
                            al.E_Alert("حدث خطأ اثناء تعديل المنتج", Alert.AlertType.ERROR);
                            return;
                        }
                    }
                } catch (SQLException e) {
                    al.E_Alert("حدث خطأ اثناء تعديل المنتج", Alert.AlertType.ERROR);
                    return;
                }
            }

            al.information_Alert(" تمت إضافة تفاصيل الفاتورة وتحديث/إضافة الصنف بنجاح");

        } catch (SQLException e) {
            al.E_Alert(" حدث خطأ أثناء إضافة تفاصيل فاتورة الشراء", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void deletePurchaseInvoiceDetail(Connection connect, purchaseInvoiceDetail d, Boolean print) throws SQLException {
        String deleteDetailSql = "DELETE FROM purchase_invoice_details WHERE id=?";
        String selectSql = "SELECT mNumberavailable FROM product "
                + "WHERE model=? AND warehouse=? AND unit=? AND IFNULL(color,'')=? AND IFNULL(size,'')=?";
        String updateProductSql = "UPDATE product SET mNumberavailable = mNumberavailable - ? "
                + "WHERE model=? AND warehouse=? AND unit=? AND IFNULL(color,'')=? AND IFNULL(size,'')=?";
        String deleteProductSql = "DELETE FROM product "
                + "WHERE model=? AND warehouse=? AND unit=? AND IFNULL(color,'')=? AND IFNULL(size,'')=?";

        // حذف من تفاصيل الفاتورة
        try ( PreparedStatement ps = connect.prepareStatement(deleteDetailSql)) {
            ps.setInt(1, d.getId());
            ps.executeUpdate();
        }

        double currentQty = 0;

        // جلب الكمية الحالية من المنتج
        try ( PreparedStatement ps = connect.prepareStatement(selectSql)) {
            ps.setString(1, d.getItemName());
            ps.setString(2, d.getWarehouse());
            ps.setString(3, d.getUnit());
            ps.setString(4, d.getColor() == null ? "" : d.getColor());
            ps.setString(5, d.getSize() == null ? "" : d.getSize());
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    currentQty = rs.getDouble("mNumberavailable");
                }
            }
        }

        // لو الكمية تساوي الكمية المحذوفة احذف المنتج كله، غير كده اعمل تحديث
        if (currentQty == d.getQuantity()) {
            try ( PreparedStatement ps = connect.prepareStatement(deleteProductSql)) {
                ps.setString(1, d.getItemName());
                ps.setString(2, d.getWarehouse());
                ps.setString(3, d.getUnit());
                ps.setString(4, d.getColor() == null ? "" : d.getColor());
                ps.setString(5, d.getSize() == null ? "" : d.getSize());
                ps.executeUpdate();
            }
        } else if (currentQty > d.getQuantity()) {
            try ( PreparedStatement ps = connect.prepareStatement(updateProductSql)) {
                ps.setDouble(1, d.getQuantity());
                ps.setString(2, d.getItemName());
                ps.setString(3, d.getWarehouse());
                ps.setString(4, d.getUnit());
                ps.setString(5, d.getColor() == null ? "" : d.getColor());
                ps.setString(6, d.getSize() == null ? "" : d.getSize());
                ps.executeUpdate();
            }
        }

        if (print) {
            al.information_Alert("تم حذف تفاصيل الفاتورة وتحديث المخزون بنجاح");
        }
    }

    public void deletePurchaseInvoiceDetail(purchaseInvoiceDetail d, Boolean print) {
        try ( Connection connect = database.getConnection()) {
            connect.setAutoCommit(false);
            deletePurchaseInvoiceDetail(connect, d, print);
            connect.commit();
        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء الحذف", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private ObservableList<purchaseInvoiceDetail> getAllPurchaseInvoiceDetails(Connection connect, Integer invoiceId) throws SQLException {
        ObservableList<purchaseInvoiceDetail> list = FXCollections.observableArrayList();

        String sql = "SELECT d.*, p.supplier_name "
                + "FROM purchase_invoice_details d "
                + "JOIN purchase_invoices p ON d.purchase_invoice_id = p.id ";

        if (invoiceId != null && invoiceId > 0) {
            sql += "WHERE d.purchase_invoice_id = ? ";
        }

        sql += "ORDER BY d.id DESC";

        try ( PreparedStatement ps = connect.prepareStatement(sql)) {
            if (invoiceId != null && invoiceId > 0) {
                ps.setInt(1, invoiceId);
            }

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    purchaseInvoiceDetail d = new purchaseInvoiceDetail();
                    d.setId(rs.getInt("id"));
                    d.setPurchaseInvoiceId(rs.getInt("purchase_invoice_id"));
                    d.setItemName(rs.getString("item_name"));
                    d.setColor(rs.getString("color"));
                    d.setSize(rs.getString("size"));
                    d.setUnit(rs.getString("unit"));
                    d.setWarehouse(rs.getString("warehouse"));
                    d.setModel(rs.getString("model"));
                    d.setQuantity(rs.getDouble("quantity"));
                    d.setPurchasePrice(rs.getDouble("purchase_price"));
                    d.setSellingPrice(rs.getDouble("selling_price"));
                    d.setSupplierName(rs.getString("supplier_name"));

                    list.add(d);
                }
            }
        }

        return list;
    }

    public ObservableList<purchaseInvoiceDetail> getAllPurchaseInvoiceDetails(Integer invoiceId) {
        try ( Connection connect = database.getConnection()) {
            return getAllPurchaseInvoiceDetails(connect, invoiceId);
        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء جلب تفاصيل فواتير الشراء", Alert.AlertType.ERROR);
            e.printStackTrace();
            return FXCollections.observableArrayList();
        }
    }

    public void updatePurchaseInvoiceDetail(purchaseInvoiceDetail d) {
        String selectOldSql = "SELECT quantity FROM purchase_invoice_details WHERE id=?";
        String updateDetailSql = "UPDATE purchase_invoice_details SET "
                + "purchase_invoice_id=?, item_name=?, color=?, size=?, unit=?, warehouse=?, model=?, quantity=?, purchase_price=?, selling_price=? "
                + "WHERE id=?";
        String updateProductSql = "UPDATE product SET mNumberavailable = mNumberavailable + ? ,"
                + " wholesalePrice=? ,"
                + " realPrice=? "
                + "WHERE model=? AND warehouse=? AND unit=? AND IFNULL(color,'')=? AND IFNULL(size,'')=?";

        try ( Connection connect = database.getConnection()) {
            connect.setAutoCommit(false);

            double oldQty = 0;

            try ( PreparedStatement ps = connect.prepareStatement(selectOldSql)) {
                ps.setInt(1, d.getId());
                try ( ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        oldQty = rs.getDouble("quantity");
                    }
                }
            }

            try ( PreparedStatement ps = connect.prepareStatement(updateDetailSql)) {
                ps.setInt(1, d.getPurchaseInvoiceId());
                ps.setString(2, (d.getItemName()));
                ps.setString(3, (d.getColor()));
                ps.setString(4, (d.getSize()));
                ps.setString(5, (d.getUnit()));
                ps.setString(6, (d.getWarehouse()));
                ps.setString(7, (d.getModel()));
                ps.setDouble(8, d.getQuantity());
                ps.setDouble(9, d.getPurchasePrice());
                ps.setDouble(10, d.getSellingPrice());
                ps.setInt(11, d.getId());

                int rows = ps.executeUpdate();
                System.out.println("Rows updated in purchase_invoice_details = " + rows);
            }

            double diff = d.getQuantity() - oldQty;

            if (diff != 0) {
                try ( PreparedStatement ps = connect.prepareStatement(updateProductSql)) {
                    ps.setDouble(1, diff);
                    ps.setDouble(2, d.getPurchasePrice());
                    ps.setDouble(3, d.getSellingPrice());
                    ps.setString(4, (d.getItemName()));
                    ps.setString(5, (d.getWarehouse()));
                    ps.setString(6, (d.getUnit()));
                    ps.setString(7, (d.getColor()));
                    ps.setString(8, (d.getSize()));
                    ps.executeUpdate();

                } catch (SQLException e) {
                    al.E_Alert(" حدث خطأ أثناء تعديل تفاصيل فاتورة الشراء", Alert.AlertType.ERROR);
                    e.printStackTrace();
                }
            }

            connect.commit();
            al.information_Alert(" تم تعديل التفاصيل وتحديث المخزون بنجاح");

        } catch (SQLException e) {
            al.E_Alert(" حدث خطأ أثناء تعديل تفاصيل فاتورة الشراء", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void updatePurchaseInvoiceDetail2(purchaseInvoiceDetail d) {
        String updateDetailSql = "UPDATE purchase_invoice_details SET "
                + "purchase_invoice_id=?, item_name=?, color=?, size=?, unit=?, warehouse=?, model=?, quantity = quantity + ?, purchase_price=?, selling_price=? "
                + "WHERE id=?";
        String updateProductSql = "UPDATE product SET mNumberavailable = mNumberavailable + ? ,"
                + " wholesalePrice=? ,"
                + " realPrice=? "
                + "WHERE model=? AND warehouse=? AND unit=? AND IFNULL(color,'')=? AND IFNULL(size,'')=?";

        try ( Connection connect = database.getConnection()) {
            connect.setAutoCommit(false);
            try ( PreparedStatement ps = connect.prepareStatement(updateDetailSql)) {
                ps.setInt(1, d.getPurchaseInvoiceId());
                ps.setString(2, (d.getItemName()));
                ps.setString(3, (d.getColor()));
                ps.setString(4, (d.getSize()));
                ps.setString(5, (d.getUnit()));
                ps.setString(6, (d.getWarehouse()));
                ps.setString(7, (d.getModel()));
                ps.setDouble(8, d.getQuantity());
                ps.setDouble(9, d.getPurchasePrice());
                ps.setDouble(10, d.getSellingPrice());
                ps.setInt(11, d.getId());

                int rows = ps.executeUpdate();
                System.out.println("Rows updated in purchase_invoice_details = " + rows);
            }

            try ( PreparedStatement ps = connect.prepareStatement(updateProductSql)) {
                ps.setDouble(1, d.getQuantity());
                ps.setDouble(2, d.getPurchasePrice());
                ps.setDouble(3, d.getSellingPrice());
                ps.setString(4, (d.getItemName()));
                ps.setString(5, (d.getWarehouse()));
                ps.setString(6, (d.getUnit()));
                ps.setString(7, (d.getColor()));
                ps.setString(8, (d.getSize()));
                ps.executeUpdate();

            } catch (SQLException e) {
                al.E_Alert(" حدث خطأ أثناء تعديل تفاصيل فاتورة الشراء", Alert.AlertType.ERROR);
                e.printStackTrace();
            }

            connect.commit();
            al.information_Alert(" تم تعديل التفاصيل وتحديث المخزون بنجاح");

        } catch (SQLException e) {
            al.E_Alert(" حدث خطأ أثناء تعديل تفاصيل فاتورة الشراء", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void deletePurchaseInvoice(int purchaseInvoiceId) {
        String deleteInvoiceSql = "DELETE FROM purchase_invoices WHERE id=?";

        try ( Connection connect = database.getConnection()) {
            connect.setAutoCommit(false);

            ObservableList<purchaseInvoiceDetail> invoiceDetails = getAllPurchaseInvoiceDetails(connect, purchaseInvoiceId);

            for (purchaseInvoiceDetail detail : invoiceDetails) {
                deletePurchaseInvoiceDetail(connect, detail, false);
            }

            try ( PreparedStatement ps = connect.prepareStatement(deleteInvoiceSql)) {
                ps.setInt(1, purchaseInvoiceId);
                ps.executeUpdate();
            }

            connect.commit();
            al.information_Alert("تم حذف الفاتورة وكافة تفاصيلها وتحديث المخزون بنجاح");

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء حذف الفاتورة", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void returnSinglePurchaseItem(purchaseInvoiceDetail d, String ReturnType, double quantityReturn) {
        Boolean isCash;
        String insertReturnSql = "INSERT INTO returnItemPurchase "
                + "(item_id, model, warehouse, wholesaleprice, type, suppliers, invoicesID, mNumberReturn, color, size, unit) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String updateProductSql = "UPDATE product SET mNumberavailable = mNumberavailable - ? "
                + "WHERE model=? AND warehouse=? AND unit=? AND IFNULL(color,'')=? AND IFNULL(size,'')=?";

        String updateDetailSql = "UPDATE purchase_invoice_details SET quantity=? WHERE id=?";
        String deleteDetailSql = "DELETE FROM purchase_invoice_details WHERE id=?";

        String updateInvoiceCashSql = "UPDATE purchase_invoices SET cash = cash - ? WHERE id=?";
        String updateInvoiceDeferredSql = "UPDATE purchase_invoices SET deferred = deferred - ? WHERE id=?";

        try ( Connection connect = database.getConnection()) {
            connect.setAutoCommit(false);

            String supplierName = getSupplierNameByInvoiceId(d.getPurchaseInvoiceId(), connect);

            // 1) إضافة المرتجع
            try ( PreparedStatement psInsert = connect.prepareStatement(insertReturnSql)) {
                psInsert.setString(2, d.getItemName());
                psInsert.setString(3, d.getWarehouse());
                psInsert.setDouble(4, d.getPurchasePrice());
                psInsert.setString(5, d.getModel());
                psInsert.setString(6, supplierName);
                psInsert.setInt(7, d.getPurchaseInvoiceId());
                psInsert.setDouble(8, quantityReturn);
                psInsert.setString(9, d.getColor() == null ? "" : d.getColor());
                psInsert.setString(10, d.getSize() == null ? "" : d.getSize());
                psInsert.setString(11, d.getUnit());
                psInsert.executeUpdate();
            }

            try ( PreparedStatement psUpdate = connect.prepareStatement(updateProductSql)) {
                psUpdate.setDouble(1, quantityReturn);
                psUpdate.setString(2, d.getItemName());
                psUpdate.setString(3, d.getWarehouse());
                psUpdate.setString(4, d.getUnit());
                psUpdate.setString(5, d.getColor() == null ? "" : d.getColor());
                psUpdate.setString(6, d.getSize() == null ? "" : d.getSize());
                psUpdate.executeUpdate();
            }

            double remainingQty = d.getQuantity() - quantityReturn;
            if (remainingQty > 0) {
                try ( PreparedStatement psUpdateDetail = connect.prepareStatement(updateDetailSql)) {
                    psUpdateDetail.setDouble(1, remainingQty);
                    psUpdateDetail.setInt(2, d.getId());
                    psUpdateDetail.executeUpdate();
                }
            } else {
                try ( PreparedStatement psDeleteDetail = connect.prepareStatement(deleteDetailSql)) {
                    psDeleteDetail.setInt(1, d.getId());
                    psDeleteDetail.executeUpdate();
                }
            }

            double returnAmount = quantityReturn * d.getPurchasePrice();
            if ("نقدي".equals(ReturnType)) {
                isCash = Boolean.TRUE;
            } else {
                isCash = Boolean.FALSE;
            }

            if (isCash) {
                try ( PreparedStatement psUpdateInvoice = connect.prepareStatement(updateInvoiceCashSql)) {
                    psUpdateInvoice.setDouble(1, returnAmount);
                    psUpdateInvoice.setInt(2, d.getPurchaseInvoiceId());
                    psUpdateInvoice.executeUpdate();
                }
            } else {
                double deferredNow = 0.0;
                double cashNow = 0.0;
                // قراءة القيم الحالية من الفاتورة
                try ( PreparedStatement psSelect = connect.prepareStatement(
                        "SELECT deferred, cash FROM purchase_invoices WHERE id=?")) {
                    psSelect.setInt(1, d.getPurchaseInvoiceId());
                    try ( ResultSet rs = psSelect.executeQuery()) {
                        if (rs.next()) {
                            deferredNow = rs.getDouble("deferred");
                            cashNow = rs.getDouble("cash");
                        }
                    }
                }

                double remainingReturn = returnAmount;

                // خصم من الآجل أولاً
                double deferredDeduction = Math.min(remainingReturn, deferredNow);
                if (deferredDeduction > 0) {
                    try ( PreparedStatement psUpdateDeferred = connect.prepareStatement(updateInvoiceDeferredSql)) {
                        psUpdateDeferred.setDouble(1, deferredDeduction);
                        psUpdateDeferred.setInt(2, d.getPurchaseInvoiceId());
                        psUpdateDeferred.executeUpdate();
                    }
                    remainingReturn -= deferredDeduction;
                }

                // لو لسه في باقي → خصمه من الكاش
                if (remainingReturn > 0) {
                    try ( PreparedStatement psUpdateCash = connect.prepareStatement(updateInvoiceCashSql)) {
                        psUpdateCash.setDouble(1, remainingReturn);
                        psUpdateCash.setInt(2, d.getPurchaseInvoiceId());
                        psUpdateCash.executeUpdate();
                    }
                }
            }

            connect.commit();
            al.information_Alert("تم إرجاع جزء من الصنف وتحديث المخزون والفاتورة بنجاح");

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء إرجاع الصنف", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void returnWholePurchaseInvoice(int purchaseInvoiceId) {
        String insertReturnSql = "INSERT INTO returnItemPurchase "
                + "( model, warehouse, wholesaleprice, type, suppliers, invoicesID, mNumberReturn, color, size, unit) "
                + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String updateProductSql = "UPDATE product SET mNumberavailable = mNumberavailable - ? "
                + "WHERE model=? AND warehouse=? AND unit=? AND IFNULL(color,'')=? AND IFNULL(size,'')=?";
        String deleteInvoiceDetailsSql = "DELETE FROM purchase_invoice_details WHERE purchase_invoice_id=?";
        String deleteInvoiceSql = "DELETE FROM purchase_invoices WHERE id=?";

        try ( Connection connect = database.getConnection()) {
            connect.setAutoCommit(false);

            ObservableList<purchaseInvoiceDetail> invoiceDetails = getAllPurchaseInvoiceDetails(connect, purchaseInvoiceId);

            for (purchaseInvoiceDetail detail : invoiceDetails) {
                String supplierName = detail.getSupplierName();

                try ( PreparedStatement psInsert = connect.prepareStatement(insertReturnSql)) {
                    psInsert.setString(1, detail.getItemName());
                    psInsert.setString(2, detail.getWarehouse());
                    psInsert.setDouble(3, detail.getPurchasePrice());
                    psInsert.setString(4, detail.getModel());
                    psInsert.setString(5, supplierName);
                    psInsert.setInt(6, purchaseInvoiceId);
                    psInsert.setDouble(7, detail.getQuantity());
                    psInsert.setString(8, detail.getColor() == null ? "" : detail.getColor());
                    psInsert.setString(9, detail.getSize() == null ? "" : detail.getSize());
                    psInsert.setString(10, detail.getUnit());
                    psInsert.executeUpdate();
                }

                try ( PreparedStatement psUpdate = connect.prepareStatement(updateProductSql)) {
                    psUpdate.setDouble(1, detail.getQuantity());
                    psUpdate.setString(2, detail.getItemName());
                    psUpdate.setString(3, detail.getWarehouse());
                    psUpdate.setString(4, detail.getUnit());
                    psUpdate.setString(5, detail.getColor() == null ? "" : detail.getColor());
                    psUpdate.setString(6, detail.getSize() == null ? "" : detail.getSize());
                    psUpdate.executeUpdate();
                }
            }

            try ( PreparedStatement ps = connect.prepareStatement(deleteInvoiceDetailsSql)) {
                ps.setInt(1, purchaseInvoiceId);
                ps.executeUpdate();
            }

            try ( PreparedStatement ps = connect.prepareStatement(deleteInvoiceSql)) {
                ps.setInt(1, purchaseInvoiceId);
                ps.executeUpdate();
            }

            connect.commit();
            al.information_Alert("تم إرجاع الفاتورة كاملة وتحديث المخزون بنجاح");

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء إرجاع الفاتورة", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private String getSupplierNameByInvoiceId(int purchaseInvoiceId, Connection connect) throws SQLException {
        String sql = "SELECT supplier_name FROM purchase_invoices WHERE id=?";
        try ( PreparedStatement ps = connect.prepareStatement(sql)) {
            ps.setInt(1, purchaseInvoiceId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("supplier_name");
                }
            }
        }
        return "";
    }

    public ObservableList<purchaseInvoiceDetail> getAllPurchases(String supplier, LocalDate fromDate, LocalDate toDate) {
        ObservableList<purchaseInvoiceDetail> list = FXCollections.observableArrayList();
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<>();

        sql.append("SELECT ");
        sql.append("d.purchase_invoice_id, ");
        sql.append("d.item_name, ");
        sql.append("d.color, ");
        sql.append("d.size, ");
        sql.append("d.unit, ");
        sql.append("d.warehouse, ");
        sql.append("d.quantity, ");
        sql.append("d.model, ");
        sql.append("d.purchase_price, ");
        sql.append("d.selling_price, ");
        sql.append("inv.invoice_date as date, ");
        sql.append("p.id AS product_id, ");
        sql.append("inv.supplier_name ");
        sql.append("FROM purchase_invoice_details d ");
        sql.append("LEFT JOIN purchase_invoices inv ON d.purchase_invoice_id = inv.id ");
        sql.append("LEFT JOIN product p ON d.model = p.type ");
        sql.append("AND d.warehouse = p.warehouse ");
        sql.append("AND d.unit = p.unit ");
        sql.append("AND d.item_name = p.model ");
        sql.append("AND d.color = p.color ");
        sql.append("AND d.size = p.size ");
        sql.append("WHERE 1=1 ");

        if (supplier != null && !supplier.trim().isEmpty()) {
            sql.append(" AND inv.supplier_name = ?");
            params.add(supplier);
        }
        if (fromDate != null) {
            sql.append(" AND DATE(inv.invoice_date) >= ?");
            params.add(fromDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        if (toDate != null) {
            sql.append(" AND DATE(inv.invoice_date) <= ?");
            params.add(toDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        sql.append(" GROUP BY d.warehouse, d.unit, d.item_name, d.model, d.color, d.size ");
        sql.append(" ORDER BY inv.invoice_date DESC");

        try ( Connection connect = database.getConnection();  PreparedStatement ps = connect.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    purchaseInvoiceDetail d = new purchaseInvoiceDetail();

                    d.setPurchaseInvoiceId(rs.getInt("purchase_invoice_id"));
                    d.setItemName(rs.getString("item_name"));
                    d.setColor(rs.getString("color"));
                    d.setSize(rs.getString("size"));
                    d.setUnit(rs.getString("unit"));
                    d.setWarehouse(rs.getString("warehouse"));
                    d.setModel(rs.getString("model"));
                    d.setPurchasePrice(rs.getDouble("purchase_price"));
                    d.setSellingPrice(rs.getDouble("selling_price"));
                    d.setQuantity(rs.getDouble("quantity"));
                    d.setSupplierName(rs.getString("supplier_name"));
                    d.setItemCode(rs.getString("product_id"));
                    d.setDate(rs.getString("date"));

                    list.add(d);
                }
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء تحميل تقرير المشتريات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return list;
    }
    
     public void printSupplierDetailsByDate(printReportsValues reportsValues, ObservableList<purchaseInvoiceDetail> returnItemL) {
        InputStream input = null;
        try {
            input = getClass().getResourceAsStream("/reports/suplierDetailsReport.jrxml");
            if (input == null) {
                al.E_Alert("لم يتم العثور على ملف التصميم salesDetailsReport.jrxml", Alert.AlertType.ERROR);
                return;
            }

            JasperReport jr = JasperCompileManager.compileReport(input);

            if (returnItemL == null || returnItemL.isEmpty()) {
                al.E_Alert("تأكد من وجود بيانات للطباعة", Alert.AlertType.WARNING);
                return;
            }

            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("fromDate", reportsValues.getFromDate() == null ? "" : reportsValues.getFromDate().toString());
            parameters.put("toDate", reportsValues.getToDate() == null ? "" : reportsValues.getToDate().toString());
            parameters.put("clint", reportsValues.getClintName() == null ? "" : reportsValues.getClintName());
            parameters.put("totalDetails", reportsValues.getTotalDetails() == null ? "" : reportsValues.getTotalDetails());

            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(returnItemL);

            JasperPrint print = JasperFillManager.fillReport(jr, parameters, itemsJRBean);

            JasperPrintManager.printReport(print, false);

        } catch (Exception e) {
            al.E_Alert("حدث خطأ أثناء طباعة الفاتورة: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ioEx) {
                al.E_Alert("حدث خطأ أثناء طباعة الفاتورة: " + ioEx.getMessage(), Alert.AlertType.ERROR);
                ioEx.printStackTrace();
            }
        }
    }

}
